package pl.oazapraga.oremus.app.reflection

import pl.oazapraga.oremus.app.ValidationIssue
import pl.oazapraga.oremus.app.ValidationIssue.OUT_OF_EDITION_CREATE_REFLECTION_PERIOD
import pl.oazapraga.oremus.app.edition.Edition
import pl.oazapraga.oremus.app.reflection.ReflectionDto.ReflectionCreateRequest
import java.time.LocalDate.now

class ReflectionValidator {

    fun validate(request: ReflectionCreateRequest, edition: Edition): MutableSet<ValidationIssue> {
        val issues = mutableSetOf<ValidationIssue>()

        currentDateIsInEditionCreateReflectionPeriod(edition, issues)

        return issues
    }


    private fun currentDateIsInEditionCreateReflectionPeriod(edition: Edition, issues: MutableSet<ValidationIssue>) {
        val valid =
            !now().isBefore(edition?.creatingReflectionBeginningDate) &&
                    !now().isAfter(edition?.creatingReflectionEndDate)

        if (!valid) {
            issues.add(OUT_OF_EDITION_CREATE_REFLECTION_PERIOD)
        }
    }
}