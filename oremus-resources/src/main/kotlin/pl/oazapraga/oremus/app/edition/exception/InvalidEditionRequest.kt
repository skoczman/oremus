package pl.oazapraga.oremus.app.edition.exception

import org.springframework.http.HttpStatus
import pl.oazapraga.oremus.app.ValidationIssue
import pl.oazapraga.oremus.app.exception.ApiException

class InvalidEditionRequest(issues: MutableSet<ValidationIssue>) :
    ApiException("Invalid Edition Request", HttpStatus.BAD_REQUEST, issues) {
}