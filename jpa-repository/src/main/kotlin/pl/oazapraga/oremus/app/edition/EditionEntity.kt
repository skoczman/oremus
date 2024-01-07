package pl.oazapraga.oremus.app.edition

import jakarta.persistence.*
import pl.oazapraga.oremus.app.day.DayEntity
import java.time.LocalDate

@Entity
@Table(name = "edition")
class EditionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    var name: String,

    var creatingReflectionBeginningDate: LocalDate,

    var creatingReflectionEndDate: LocalDate,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id")
    var days: MutableSet<DayEntity> = mutableSetOf()
) {

    constructor(dto: Edition) : this(
        dto.id,
        dto.name,
        dto.creatingReflectionBeginningDate,
        dto.creatingReflectionEndDate,
        dto.days.map { DayEntity(it) }.toMutableSet()
    ) {
    }

    fun toDto(): Edition {
        return Edition(
            id,
            name,
            creatingReflectionBeginningDate,
            creatingReflectionEndDate,
            days.map { it.toDto() }.toMutableSet()
        )
    }

}