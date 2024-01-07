package pl.oazapraga.oremus.app.day

import java.time.LocalDate
import java.util.*

interface DayRepositoryPort {

    fun findById(id: Int): Day
    fun save(dto: Day): Day
    fun findByDateBetween(dateFrom: LocalDate, dateTo: LocalDate?): List<Day>
    fun findByDateGreaterThanEqual(dateFrom: LocalDate): List<Day>
    fun findByDate(date: LocalDate?): Optional<Day>
}