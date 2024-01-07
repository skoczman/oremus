package pl.oazapraga.oremus.app.precondition

import pl.oazapraga.oremus.app.day.Day
import pl.oazapraga.oremus.app.edition.Edition
import pl.oazapraga.oremus.app.edition.EditionCreateRequest
import pl.oazapraga.oremus.app.edition.EditionUpdateRequest

import java.time.LocalDate

import static java.time.LocalDate.now
import static java.time.LocalDate.parse

class EditionPrecondition {

    static def unsaved(LocalDate daysRangeDateFrom = now().plusDays(40), LocalDate daysRangeDateTo = now().plusDays(70),
                       LocalDate creatingReflectionBeginningDate = now(), LocalDate creatingReflectionEndDate = now().plusDays(30)) {
        return new EditionCreateRequest(
                now().toString(),
                daysRangeDateFrom,
                daysRangeDateTo,
                creatingReflectionBeginningDate,
                creatingReflectionEndDate
        )
    }

    static def saved(LocalDate creatingReflectionBeginningDate = null, LocalDate creatingReflectionEndDate = null, days = null) {
        if (days == null) {
            days = [
                    new Day(1, parse("2023-02-01"), "author", null, null, null, null, null, false, false, 0),
                    new Day(2, parse("2023-02-02"), "author", null, null, null, null, null, false, false, 0),
                    new Day(3, parse("2023-02-03"), "author", null, null, null, null, null, false, false, 0),
            ]
            creatingReflectionBeginningDate = parse("2023-01-01")
            creatingReflectionEndDate = parse("2023-01-31")
        }
        return new Edition(
                0,
                "name",
                creatingReflectionBeginningDate,
                creatingReflectionEndDate,
                days.toSet()
        )
    }

    static def updateOf(String updatedName) {
        return new EditionUpdateRequest(updatedName)
    }

    static List<Edition> mockedEditionList(List<Object> data) {
        if (data.isEmpty()) {
            return List.of(
                    new Edition(1, "First Edition", parse("2023-01-01"), parse("2023-01-31"), Set.of(
                            new Day(1, parse("2023-02-01"), 'author', null, null, null, null, null, false, false, 1),
                            new Day(2, parse("2023-02-02"), 'author', null, null, null, null, null, false, false, 1),
                            new Day(3, parse("2023-02-03"), 'author', null, null, null, null, null, false, false, 1)
                    )),
                    new Edition(2, "Second Edition", parse("2023-03-01"), parse("2023-03-31"), Set.of(
                            new Day(4, parse("2023-04-01"), 'author', null, null, null, null, null, false, false, 2),
                            new Day(5, parse("2023-04-02"), 'author', null, null, null, null, null, false, false, 2),
                            new Day(6, parse("2023-04-03"), 'author', null, null, null, null, null, false, false, 2)
                    ))
            )
        } else {
            List<Edition> mockEditions = []
            int id = 1
            data.forEach {
                {
                    mockEditions.push(
                            new Edition(
                                    id++,
                                    "Edition $id Name",
                                    it.creatingReflectionBeginningDate,
                                    it.creatingReflectionEndDate,
                                    it.daysRange.startDate.datesUntil(it.daysRange.endDate.plusDays(1))
                                            .toList().stream()
                                            .map { new Day(it.hashCode().toInteger(), it, "mockedAuthor", null, null, null, null, null, false, false, id) }
                                            .toSet())
                    )
                }
            }
            return mockEditions
        }
    }
}
