package pl.oazapraga.oremus.app.day

import pl.oazapraga.oremus.app.domain.day.exception.DayNotFound
import java.time.LocalDate
import java.util.*

class DayRepositoryAdapter(val repository: DayRepository) : DayRepositoryPort {

    override fun findById(id: Int): Day {
        return repository.findById(id)
            .map { it.toDto() }
            .orElseThrow { DayNotFound(id) }
    }

    override fun save(dto: Day): Day {
        return repository.save(DayEntity(dto))
            .toDto()
    }

    override fun findByDateBetween(dateFrom: LocalDate, dateTo: LocalDate?): List<Day> {
        return repository.findByDateBetween(dateFrom, dateTo)
            .map { it.toDto() }
    }

    override fun findByDateGreaterThanEqual(dateFrom: LocalDate): List<Day> {
        return repository.findByDateGreaterThanEqual(dateFrom)
            .map { it.toDto() }
    }

    override fun findByDate(date: LocalDate?): Optional<Day> {
        return repository.findByDate(date)
            .map { it.toDto() }
    }
}