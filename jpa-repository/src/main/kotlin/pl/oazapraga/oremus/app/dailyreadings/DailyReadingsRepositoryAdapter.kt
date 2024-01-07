package pl.oazapraga.oremus.app.dailyreadings

import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class DailyReadingsRepositoryAdapter(val repository: DailyReadingsRepository) : DailyReadingsRepositoryPort {

    override fun findByDate(date: LocalDate): List<DailyReadings> {
        return repository.findByDate(date)
            .map { it.toDto() }
    }

    override fun findById(id: Int): DailyReadings? {
        return repository.findByIdOrNull(id)
            ?.toDto()
    }

    override fun save(dto: DailyReadings): DailyReadings {
        return repository.save(DailyReadingsEntity(dto))
            .toDto()
    }

    override fun delete(dailyReadings: DailyReadings) {
        repository.delete(DailyReadingsEntity(dailyReadings))
    }
}