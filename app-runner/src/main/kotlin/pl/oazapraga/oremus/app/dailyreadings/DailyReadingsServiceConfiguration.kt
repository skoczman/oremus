package pl.oazapraga.oremus.app.dailyreadings

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DailyReadingsServiceConfiguration {

    @Bean
    open fun dailyReadingsService(repository: DailyReadingsRepository): DailyReadingsService {
        val repositoryPort = DailyReadingsRepositoryAdapter(repository)
        return DailyReadingsService(repositoryPort)
    }
}