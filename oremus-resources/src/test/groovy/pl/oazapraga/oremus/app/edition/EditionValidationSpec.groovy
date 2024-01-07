package pl.oazapraga.oremus.app.edition

import pl.oazapraga.oremus.app.BaseUnitTest
import pl.oazapraga.oremus.app.ValidationIssue
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsService
import pl.oazapraga.oremus.app.edition.exception.InvalidEditionRequest

import java.time.LocalDate

import static java.time.LocalDate.parse
import static pl.oazapraga.oremus.app.ValidationIssue.*

class EditionValidationSpec extends BaseUnitTest {

    DailyReadingsService dailyReadingsService = GroovyMock()

    EditionRepositoryPort repository = new EditionInMemoryRepository()
    EditionService service = new EditionService(repository, dailyReadingsService)

    def cleanup() {
        repository.store.removeAll()
    }

    def 'should throw exception and return proper issue when try to create edition'(
            String name, LocalDate daysRangeDateFrom, LocalDate daysRangeDateTo,
            LocalDate creatingReflectionBeginningDate, LocalDate creatingReflectionEndDate, Class exception, ValidationIssue issue) {

        given:
            def request = new EditionCreateRequest(name, daysRangeDateFrom, daysRangeDateTo, creatingReflectionBeginningDate, creatingReflectionEndDate)

        when:
            service.validation(request)

        then:
            def ex = thrown(exception)
            ex.issues.contains(issue)

        where:
            name           | daysRangeDateFrom   | daysRangeDateTo     | creatingReflectionBeginningDate | creatingReflectionEndDate | exception             | issue
            "Edition Name" | parse("2023-10-10") | parse("2023-10-05") | parse("2023-09-05")             | parse("2023-09-15")       | InvalidEditionRequest | DAYS_RANGE_END_DATE_BEFORE_DAYS_RANGE_START_DATE
            "Edition Name" | parse("2023-10-10") | parse("2023-10-05") | parse("2023-09-15")             | parse("2023-09-05")       | InvalidEditionRequest | CREATING_REFLECTION_BEGINNING_DATE_BEFORE_CREATING_REFLECTION_END_DATE
            ""             | parse("2023-10-10") | parse("2023-10-15") | parse("2023-09-05")             | parse("2023-09-15")       | InvalidEditionRequest | MISSING_NAME
    }

    def 'should throw exception and return proper issue when try to update edition'(
            String name, Class exception, ValidationIssue issue) {

        given:
            def request = new EditionUpdateRequest(name)

        when:
            service.validation(request)

        then:
            def ex = thrown(exception)
            ex.issues.contains(issue)

        where:
            name | exception             | issue
            ""   | InvalidEditionRequest | MISSING_NAME
    }
}
