package pl.oazapraga.oremus.app.dailyreadings

import java.time.LocalDate


data class DailyReadings(
    val id: Int?,
    val date: LocalDate,
    val siglum: String?,
    val memorial: String?,
) {

    constructor(request: DailyReadingsCreateRequest) :
            this(null, request.date, request.siglum, request.memorial)

    fun asResponse(): DailyReadingsResponse {
        return DailyReadingsResponse(
            this.id ?: 0,
            this.date,
            this.siglum,
            this.memorial
        )
    }

}

