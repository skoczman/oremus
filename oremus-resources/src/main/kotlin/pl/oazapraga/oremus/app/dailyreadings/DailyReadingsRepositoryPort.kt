package pl.oazapraga.oremus.app.dailyreadings

import java.time.LocalDate

interface DailyReadingsRepositoryPort {

    fun findByDate(date: LocalDate): List<DailyReadings>

    fun findById(id: Int): DailyReadings?

    fun save(dailyReadings: DailyReadings): DailyReadings

    fun delete(dailyReadings: DailyReadings)
}