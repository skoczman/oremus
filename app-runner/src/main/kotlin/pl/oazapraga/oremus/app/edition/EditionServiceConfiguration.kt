package pl.oazapraga.oremus.app.edition

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.oazapraga.oremus.app.dailyreadings.DailyReadingsService


@Configuration
class EditionServiceConfiguration() {

    @Autowired
    lateinit var repository: EditionRepository

    @Bean
    fun editionRepositoryPort(): EditionRepositoryPort {
        return EditionRepositoryAdapter(repository)
    }

    @Bean
    @Autowired
    fun editionService(editionRepositoryPort: EditionRepositoryPort, dailyReadingsService: DailyReadingsService): EditionService {
        return EditionService(editionRepositoryPort, dailyReadingsService)
    }
}