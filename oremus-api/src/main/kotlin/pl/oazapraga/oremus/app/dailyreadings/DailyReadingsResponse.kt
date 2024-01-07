package pl.oazapraga.oremus.app.dailyreadings

import java.time.LocalDate

data class DailyReadingsResponse(val id: Int, val date: LocalDate, val siglum: String?, val memorial: String?)