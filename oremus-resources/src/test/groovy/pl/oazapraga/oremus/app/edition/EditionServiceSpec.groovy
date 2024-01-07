package pl.oazapraga.oremus.app.edition


import pl.oazapraga.oremus.app.BaseUnitTest
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsEntity
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsService
import pl.oazapraga.oremus.app.edition.exception.EditionNotFound
import pl.oazapraga.oremus.app.edition.exception.InvalidEditionRequest
import pl.oazapraga.oremus.app.utils.DateRange

import java.time.LocalDate

import static java.time.LocalDate.parse
import static pl.oazapraga.oremus.app.ValidationIssue.MISSING_NAME
import static pl.oazapraga.oremus.app.ValidationIssue.NO_EDITION_EXISTS

class EditionServiceSpec extends BaseUnitTest {

    DailyReadingsService dailyReadingsService = GroovyMock()

    EditionRepositoryPort repository = new EditionInMemoryRepository()
    EditionService editionService = new EditionService(repository, dailyReadingsService)

    def cleanup() {
        repository.store.removeAll()
    }

    def 'should save new edition'() {
        given:
            dailyReadingsService.findEntityByDate(_ as LocalDate) >> {
                LocalDate date ->
                    new DailyReadingsEntity(date.toEpochDay().toInteger(), date, "siglum", "memorial", null)
            }

        and:
            def unsaved = given.edition.unsaved()

        when:
            def response = editionService.save(unsaved)

        then:
            with(editionService.get(response.id)) {
                name == unsaved.name
            }
    }

    def 'should return proper daysRangeDateFrom'() {
        given:
            def unsaved1 = given.edition.unsaved(
                    parse("2023-02-01"), parse("2023-02-28"))
            def unsaved2 = given.edition.unsaved(
                    parse("2023-04-01"), parse("2023-04-30"))

            editionService.save(unsaved1)
            editionService.save(unsaved2)

        expect:
            editionService.getNextDaysRangeDateFrom() == parse("2023-05-01")
    }

    def 'should throw exception when daysRangeDateFrom is not provided and there are no existing editions'() {
        when:
            editionService.getNextDaysRangeDateFrom()

        then:
            def exception = thrown(InvalidEditionRequest)
            exception.issues.contains(NO_EDITION_EXISTS)
    }

    def 'should NOT return exception when new edition is saved and no another edition exists.'() {
        expect:
            editionService.checkDatesOverlaps(List.of(), given.edition.unsaved())
    }

    def 'should throw exception when new edition daysRange would overlapped some existing one.'(
            LocalDate existingDaysRangeDateFrom, LocalDate existingDaysRangeDateTo,
            LocalDate newDaysRangeDateFrom, LocalDate newDaysRangeDateTo) {

        given:
            def editions = given.edition.mockedEditionList(List.of(
                    [
                            creatingReflectionBeginningDate: parse("2023-01-01"),
                            creatingReflectionEndDate      : parse("2023-01-31"),
                            daysRange                      : new DateRange(startDate: existingDaysRangeDateFrom, endDate: existingDaysRangeDateTo)
                    ]))

        when:
            editionService.checkDatesOverlaps(editions, given.edition.unsaved(newDaysRangeDateFrom, newDaysRangeDateTo))

        then:
            thrown(InvalidEditionRequest)

        where:
            existingDaysRangeDateFrom | existingDaysRangeDateTo | newDaysRangeDateFrom | newDaysRangeDateTo
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2023-01-10")  | parse("2023-01-20")
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2022-12-24")  | parse("2023-01-01")
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2023-01-05")  | parse("2023-01-10")
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2023-01-01")  | parse("2023-01-05")
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2023-01-05")  | parse("2023-01-20")
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2022-12-24")  | parse("2023-01-05")
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2023-01-04")  | parse("2023-01-08")
            parse("2023-01-01")       | parse("2023-01-10")     | parse("2022-12-24")  | parse("2023-01-15")
    }

    def 'should update existing edition'() {
        given:
            def saved = editionService.save(given.edition.unsaved())

        when:
            def updateRequest = given.edition.updateOf("updated name")
            def result = editionService.update(updateRequest, saved.id)

        then:
            with(result) {
                id == saved.id
                name == updateRequest.name
            }
    }

    def 'should not update existing edition when updated name is empty'() {
        given:
            def saved = editionService.save(given.edition.unsaved())

        when:
            def updateRequest = given.edition.updateOf('')
            editionService.update(updateRequest, saved.id)

        then:
            def exception = thrown(InvalidEditionRequest)
            exception.message == "Invalid Edition Request"
            exception.issues.contains(MISSING_NAME)
    }

    def 'should not update when edition does not exists'() {
        when:
            def updateRequest = given.edition.updateOf('Updated name')
            editionService.update(updateRequest, 1)

        then:
            thrown(EditionNotFound)
    }
}