package pl.oazapraga.oremus.app.dailyreadings.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
@ResponseStatus(HttpStatus.BAD_REQUEST)
class DailyReadingsAlreadyExist(message: String): RuntimeException(message)