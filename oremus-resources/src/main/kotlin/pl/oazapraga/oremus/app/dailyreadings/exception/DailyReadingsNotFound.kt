package pl.oazapraga.oremus.app.dailyreadings.exception

import org.springframework.http.HttpStatus
import pl.oazapraga.oremus.app.exception.ApiException

class DailyReadingsNotFound(message: String) :
    ApiException(message, HttpStatus.NOT_FOUND, setOf("Daily readings not found"))