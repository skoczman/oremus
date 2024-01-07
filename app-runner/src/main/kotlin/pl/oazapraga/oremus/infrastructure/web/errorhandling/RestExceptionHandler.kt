package pl.oazapraga.oremus.infrastructure.web.errorhandling

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pl.oazapraga.oremus.app.exception.ApiException

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler() {

    @ExceptionHandler
    fun handlerApiException(exception: ApiException, request: HttpServletRequest): ResponseEntity<Any> {
        val servletPath = request.servletPath
        return exception.buildResponse(servletPath)
    }

}