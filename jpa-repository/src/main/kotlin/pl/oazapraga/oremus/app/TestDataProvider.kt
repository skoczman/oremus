package pl.oazapraga.oremus.app


import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.oazapraga.oremus.app.edition.Edition
import pl.oazapraga.oremus.app.edition.EditionEntity
import pl.oazapraga.oremus.app.edition.EditionRepository
import java.time.LocalDate

@Configuration
@ConditionalOnProperty(
    value = ["oremus.testData"],
    havingValue = "true",
    matchIfMissing = false
)
open class TestDataProvider(
    private val editionRepository: EditionRepository,
) {

    @Bean
    fun initData(): InitializingBean {
        return InitializingBean {

            editionRepository.save(
                EditionEntity(
                    Edition(
                    0,
                    "Styczeń - Marzec",
                    LocalDate.parse("2023-01-01"),
                    LocalDate.parse("2023-03-31"),
                    mutableSetOf())
                )
            )
        }
    }
}
