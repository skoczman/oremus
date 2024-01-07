package pl.oazapraga.oremus.app.preconditions

import pl.oazapraga.oremus.app.repository.DailyReadingsRepository
import pl.oazapraga.oremus.app.repository.EditionRepository

class Precondition {

    DailyReadingsRepository dailyReadingsRepository
    EditionRepository editionRepository

    Precondition(
            DailyReadingsRepository dailyReadingsRepository,
            EditionRepository editionRepository) {

        this.dailyReadingsRepository = dailyReadingsRepository
        this.editionRepository = editionRepository
    }

    def edition = new EditionPrecondition(editionRepository)
    def dailyReadings = new DailyReadingsPrecondition(dailyReadingsRepository)
    def reflection = new ReflectionPrecondition()
}
