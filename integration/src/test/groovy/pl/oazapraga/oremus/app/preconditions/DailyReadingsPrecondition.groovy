package pl.oazapraga.oremus.app.preconditions

import pl.oazapraga.oremus.app.domain.dailyreadings.DailyReadingsEntity
import pl.oazapraga.oremus.app.dto.TestNewDailyReadings
import pl.oazapraga.oremus.app.dto.TestUpdateDailyReadings
import pl.oazapraga.oremus.app.repository.DailyReadingsRepository

import java.time.LocalDate
import java.util.stream.Collectors

import static java.time.LocalDate.parse

class DailyReadingsPrecondition {

    DailyReadingsRepository repository

    DailyReadingsPrecondition(DailyReadingsRepository repository) {
        this.repository = repository
    }

    DailyReadingsEntity anyDailyReading(LocalDate date = parse("2023-01-03")) {
        repository.save(
            new DailyReadingsEntity(
                date: date,
                siglum: "Lb 6, 22-27; Ps 67 (66), 2-3. 5; Ga 4, 4-7; Łk 2, 16-21",
                memorial: "Uroczystość Świętej Bożej Rodzicielki Maryi"
            ))
    }

    List<DailyReadingsEntity> someDailyReadings(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
            .map {anyDailyReading(it)}
            .collect(Collectors.toList())
    }

    static TestNewDailyReadings unsaved(LocalDate date = parse("2023-01-03")) {
        return new TestNewDailyReadings(
            date: date,
            siglum: "Lb 6, 22-27; Ps 67 (66), 2-3. 5; Ga 4, 4-7; Łk 2, 16-21",
            memorial: "Uroczystość Świętej Bożej Rodzicielki Maryi"
        )
    }

    static TestUpdateDailyReadings updateOf(String siglum, String memorial) {
        return new TestUpdateDailyReadings(
            siglum: siglum,
            memorial: memorial
        )
    }
}
