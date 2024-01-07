package pl.oazapraga.oremus.app.day

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface DayRepository : JpaRepository<DayEntity, Int> {
    fun findByDateBetween(dateFrom: LocalDate, dateTo: LocalDate?): List<DayEntity>
    fun findByDateGreaterThanEqual(dateFrom: LocalDate): List<DayEntity>
    fun findByDate(date: LocalDate?): Optional<DayEntity>
}