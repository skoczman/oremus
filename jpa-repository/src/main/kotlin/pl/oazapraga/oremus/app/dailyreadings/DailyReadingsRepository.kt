package pl.oazapraga.oremus.app.dailyreadings

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface DailyReadingsRepository: JpaRepository<DailyReadingsEntity, Int> {
    fun findByDate(date: LocalDate): List<DailyReadingsEntity>
}