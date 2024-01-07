package pl.oazapraga.oremus.app.edition

import pl.oazapraga.oremus.app.ValidationIssue
import pl.oazapraga.oremus.app.ValidationIssue.*

class EditionValidator {

    fun validate(request: EditionCreateRequest): MutableSet<ValidationIssue> {
        val issues = mutableSetOf<ValidationIssue>()

        hasValidDaysRangeDates(request, issues)
        hasValidCreatingReflectionDates(request, issues)
        hasName(request, issues)

        return issues
    }

    fun validate(request: EditionUpdateRequest): MutableSet<ValidationIssue> {
        val issues = mutableSetOf<ValidationIssue>()

        hasName(request, issues)

        return issues
    }

    private fun hasValidDaysRangeDates(request: EditionCreateRequest, issues: MutableSet<ValidationIssue>) {
        val valid = request.daysRangeDateFrom?.isBefore(request.daysRangeDateTo) ?: true
        if (!valid) {
            issues.add(DAYS_RANGE_END_DATE_BEFORE_DAYS_RANGE_START_DATE)
        }
    }

    private fun hasValidCreatingReflectionDates(request: EditionCreateRequest, issues: MutableSet<ValidationIssue>) {
        val valid = request.creatingReflectionBeginningDate.isBefore(request.creatingReflectionEndDate) ?: true
        if (!valid) {
            issues.add(CREATING_REFLECTION_BEGINNING_DATE_BEFORE_CREATING_REFLECTION_END_DATE)
        }
    }

    private fun hasName(request: EditionCreateRequest, issues: MutableSet<ValidationIssue>) {
        val valid = request.name.isNotEmpty()
        if (!valid) {
            issues.add(MISSING_NAME)
        }
    }

    private fun hasName(request: EditionUpdateRequest, issues: MutableSet<ValidationIssue>) {
        val valid = request.name.isNotEmpty()
        if (!valid) {
            issues.add(MISSING_NAME)
        }
    }

}