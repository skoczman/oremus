package pl.oazapraga.oremus.app.day

import jakarta.persistence.*
import pl.oazapraga.oremus.app.edition.EditionEntity
import pl.oazapraga.oremus.app.reflection.ReflectionDto.ReflectionResponse
import java.time.LocalDate

@Entity
@Table(name = "oremus_day")
class DayEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

    @Column(name = "day_date")
        var date: LocalDate,

    @Column(name = "author")
        var author: String? = null,

    @Column(name = "title_history")
        var title: String? = null,

    @Column(name = "text_history")
        var text: String? = null,

    @Column(name = "status")
    var status: String? = null,

    @Column(name = "language_rev")
    var languageRev: String? = null,

    @Column(name = "substantive_rev")
    var substantiveRev: String? = null,

    @Column(name = "language_rev_complete")
    var languageRevComplete: Boolean = false,

    @Column(name = "substantive_rev_complete")
    var substantiveRevComplete: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id", insertable = false, updatable = false)
    var edition: EditionEntity?,

    @Column(name = "edition_id")
    var editionId: Int?
) {

    constructor(date: LocalDate, author: String) :
            this(
                null,
                date,
                author,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                null,
                null
            ) {
    }

    constructor(dto: Day) :
            this(
                dto.id, dto.date, dto.author, dto.title, dto.text, dto.status,
                dto.languageRev, dto.substantiveRev, dto.languageRevComplete, dto.substantiveRevComplete,
                null, dto.editionId
            )


    fun toDto(): Day {
        return Day(
            id,
            date,
            author,
            title,
            text,
            status,
            languageRev,
            substantiveRev,
            languageRevComplete,
            substantiveRevComplete,
            edition?.id
        )
    }

    fun asReflectionResponse(): ReflectionResponse {
        return ReflectionResponse(this.title, this.text)
    }
}