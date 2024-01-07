package pl.oazapraga.oremus.app.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

abstract class ApiException(message: String, val httpStatus: HttpStatus, val issues: Set<Any>?) :
    RuntimeException(message) {

    fun buildResponse(path: String): ResponseEntity<Any> {
        val errorBody = createErrorBody(path)
        return ResponseEntity<Any>(errorBody, httpStatus)
    }

    fun createErrorBody(path: String) =
        ApiError(httpStatus.value(), message, path, issues)
}