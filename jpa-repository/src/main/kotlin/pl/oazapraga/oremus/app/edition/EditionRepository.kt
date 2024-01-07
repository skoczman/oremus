package pl.oazapraga.oremus.app.edition

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EditionRepository : JpaRepository<EditionEntity, Int> {

    @Query("SELECT e FROM EditionEntity e WHERE e.id IN (SELECT d.editionId FROM DayEntity d WHERE d.id = :dayId)")
    fun findByDayId(dayId: Int): EditionEntity?
}