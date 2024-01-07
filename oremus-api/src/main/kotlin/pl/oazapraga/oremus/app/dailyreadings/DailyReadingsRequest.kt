package pl.oazapraga.oremus.app.dailyreadings

import java.time.LocalDate

data class DailyReadingsCreateRequest(val date: LocalDate, val siglum: String?, val memorial: String?)
data class DailyReadingsUpdateRequest(val siglum: String?, val memorial: String?)