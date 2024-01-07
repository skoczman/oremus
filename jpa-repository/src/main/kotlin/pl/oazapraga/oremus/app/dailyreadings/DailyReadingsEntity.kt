package pl.oazapraga.oremus.app.dailyreadings

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class DailyReadingsEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    var date: LocalDate,

    @Column(length = 500)
    var siglum: String?,

    @Column(length = 500)
    var memorial: String?,

    ) {
    constructor(dto: DailyReadings) :
            this(dto.id ?: 0, dto.date, dto.siglum, dto.memorial)

    fun toDto(): DailyReadings {
        return DailyReadings(id, date, siglum, memorial)
    }
}

