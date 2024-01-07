package pl.oazapraga.oremus.app

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import pl.oazapraga.oremus.app.dto.TestEdition
import pl.oazapraga.oremus.app.dto.TestNewEdition
import pl.oazapraga.oremus.app.dto.TestUpdateEdition
import spock.lang.Ignore

import static java.time.LocalDate.parse
import static java.time.temporal.ChronoUnit.DAYS
import static org.springframework.http.HttpStatus.*

class EditionSpec extends BaseIntegrationTest {

    def 'should create new edition'() {
        given:
            def creatingReflectionBeginningDate = parse("2023-01-01")
            def creatingReflectionsEndDate = parse("2023-01-31")
            def daysRangeDateFrom = parse("2023-02-01")
            def daysRangeDateTo = parse("2023-02-28")
            given.dailyReadings.someDailyReadings(daysRangeDateFrom, daysRangeDateTo)
            TestNewEdition edition = given.edition.unsaved(daysRangeDateFrom, daysRangeDateTo, creatingReflectionBeginningDate, creatingReflectionsEndDate)

        when:
            ResponseEntity postResponse = restTemplate.postForEntity("/api/edition", edition, TestEdition.class)

        then:
            postResponse.statusCode == CREATED

        and:
            with(postResponse.body) {
                id != null
                daysRangeDateFrom == daysRangeDateFrom
                daysRangeDateTo == daysRangeDateTo
                creatingReflectionBeginningDate == creatingReflectionBeginningDate
                creatingReflectionsEndDate == creatingReflectionsEndDate
                days.size() == DAYS.between(daysRangeDateFrom, daysRangeDateTo.plusDays(1))
                days.every { it.dailyReadings != null }
            }

        and:
            ResponseEntity getResponse = restTemplate.getForEntity("/api/edition/$postResponse.body.id", TestEdition.class)
            with(getResponse.body) {
                id != null
                daysRangeDateFrom == daysRangeDateFrom
                daysRangeDateTo == daysRangeDateTo
                creatingReflectionBeginningDate == creatingReflectionBeginningDate
                creatingReflectionsEndDate == creatingReflectionsEndDate
                days.size() == DAYS.between(daysRangeDateFrom, daysRangeDateTo.plusDays(1))
                days.every { it.dailyReadings != null }
            }
    }

    def 'should create new edition with daysRangeDateFrom next day after last day of the latest edition'() {
        given:
            def daysRangeDateFrom = parse("2023-02-01")
            def daysRangeDateTo = parse("2023-02-28")
            def creatingReflectionBeginningDate = parse("2023-01-01")
            def creatingReflectionsEndDate = parse("2023-01-31")
            given.dailyReadings.someDailyReadings(daysRangeDateFrom, daysRangeDateTo)

        and:
            TestNewEdition edition = given.edition.unsaved(daysRangeDateFrom, daysRangeDateFrom.plusDays(10),
                    creatingReflectionBeginningDate, creatingReflectionsEndDate)
            restTemplate.postForEntity("/api/edition", edition, TestEdition.class)

        when:
            TestNewEdition editionWithEmptyDaysRangeDateFrom = given.edition.unsaved(null, daysRangeDateTo, creatingReflectionBeginningDate, creatingReflectionsEndDate)
            ResponseEntity response = restTemplate.postForEntity("/api/edition", editionWithEmptyDaysRangeDateFrom, TestEdition.class)

        then:
            response.statusCode == CREATED

        and:
            with(response.body) {
                days.size() == DAYS.between(daysRangeDateFrom.plusDays(11), daysRangeDateTo.plusDays(1))
                days.min { it.date }.date == daysRangeDateFrom.plusDays(11)
                days.max { it.date }.date == daysRangeDateTo
                days.every { it.dailyReadings != null }
            }
    }

    def 'should update edition'() {
        given:
            def edition = given.edition.anySavedEdition()

        when:
            def updateDto = given.edition.updateOf("Updated ${edition.name}")
            ResponseEntity updated = restTemplate.exchange("/api/edition/${edition.id}", HttpMethod.PUT, new HttpEntity<TestUpdateEdition>(updateDto), TestEdition.class)

        then:
            with(updated.body) {
                id == edition.id
                name == "Updated ${edition.name}"
            }
    }

    def 'should not update when edition does not exists'() {
        given:
            def wrongId = -1

        when:
            def updateDto = given.edition.updateOf("Updated Edition Name")
            def response = restTemplate.exchange("/api/edition/$wrongId", HttpMethod.PUT, new HttpEntity<TestUpdateEdition>(updateDto), Object.class)

        then:
            response.statusCode == NOT_FOUND
    }

    def 'should not update when update has empty name'() {
        given:
            def edition = given.edition.anySavedEdition()

        when:
            def updateDto = given.edition.updateOf("")
            def response = restTemplate.exchange("/api/edition/$edition.id", HttpMethod.PUT, new HttpEntity<TestUpdateEdition>(updateDto), Object.class)

        then:
            response.statusCode == BAD_REQUEST
    }

    // TODO: find out what sould be mocked or invoked manually. How to should this test be written in general
    @Ignore
    def 'should notify authors they can start to write new edition reflections'() {
        given:
            def edition = given.edition.anySavedEdition()

        when:
            def cron

        then:
            true == false
            // 1*emailService
    }

    def "should close edition"() {
        //really? do we need that? maybe "closing" means "accepting last reflection?

    }

    def "should validate edition before closing"() {
        //same as below - will the closing be automatic after last reflection or do we want "close/print edition" button?

    }

    def "should return closed edition to print"() {
        TestEdition edition = given.edition.verified() //this statuses need polishing

        //todo how do we want to display edition to print? what endpoint and response do we need? Maybe simple GET /latest is enough?
    }

//    def "should send email to Kuba when Adam correctred his reflection"() {
//        def reflection = given.day.withCorrectedReflection()
//
//        val resp = rest.post()
//
//        with (resp) {
//
//        }
//    }
}
