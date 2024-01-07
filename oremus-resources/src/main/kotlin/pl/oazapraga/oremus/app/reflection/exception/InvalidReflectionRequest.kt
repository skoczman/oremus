package pl.oazapraga.oremus.app.reflection.exception

import org.springframework.http.HttpStatus.BAD_REQUEST
import pl.oazapraga.oremus.app.ValidationIssue
import pl.oazapraga.oremus.app.exception.ApiException

class InvalidReflectionRequest(issues: MutableSet<ValidationIssue>) :
    ApiException("Invalid Reflection Request", BAD_REQUEST, issues) {
}