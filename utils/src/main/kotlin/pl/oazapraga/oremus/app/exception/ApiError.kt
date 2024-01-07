package pl.oazapraga.oremus.app.exception

import java.time.Instant

data class ApiError(
        val status: Int,
        val message: String?,
        val path: String?,
        val issues: Set<Any>?,
        val timestamp: Instant? = Instant.now(),
)
