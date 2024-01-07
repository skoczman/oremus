package pl.oazapraga.oremus.app.preconditions

import pl.oazapraga.oremus.app.domain.day.DayEntity
import pl.oazapraga.oremus.app.domain.edition.EditionEntity
import pl.oazapraga.oremus.app.dto.TestNewEdition
import pl.oazapraga.oremus.app.dto.TestUpdateEdition
import pl.oazapraga.oremus.app.repository.EditionRepository

import java.time.LocalDate
import java.util.stream.Collectors

import static java.time.LocalDate.now

class EditionPrecondition {

    EditionRepository editionRepository

    EditionPrecondition(EditionRepository editionRepository) {
        this.editionRepository = editionRepository
    }

    static def unsaved(LocalDate daysRangeDateFrom, LocalDate daysRangeDateTo,
                       LocalDate creatingReflectionBeginningDate, LocalDate creatingReflectionEndDate) {
        return new TestNewEdition(
                name: "TestNewEdition",
                daysRangeDateFrom: daysRangeDateFrom,
                daysRangeDateTo: daysRangeDateTo,
                creatingReflectionBeginningDate: creatingReflectionBeginningDate,
                creatingReflectionEndDate: creatingReflectionEndDate
        )
    }

    static TestUpdateEdition updateOf(String name) {
        return new TestUpdateEdition(
                name: name
        )
    }

    EditionEntity anySavedEdition(LocalDate daysRangeDateFrom = now().plusDays(40), LocalDate daysRangeDateTo = now().plusDays(70),
                                  LocalDate creatingReflectionBeginningDate = now(), LocalDate creatingReflectionEndDate = now().plusDays(30)) {
        editionRepository.save(new EditionEntity(
                name: "AnyEditionName",
                days: prepareDayList(daysRangeDateFrom, daysRangeDateTo),
                creatingReflectionBeginningDate: creatingReflectionBeginningDate,
                creatingReflectionEndDate: creatingReflectionEndDate
        ))
    }

    static List<DayEntity> prepareDayList(LocalDate daysRangeDateFrom, LocalDate daysRangeDateTo) {
        return daysRangeDateFrom.datesUntil(daysRangeDateTo.plusDays(1))
                .map {
                    new DayEntity(
                            date: it
                    )
                }
                .collect(Collectors.toList())
    }

}
