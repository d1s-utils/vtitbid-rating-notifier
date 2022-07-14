package dev.d1s.vtitbidratingnotifier.configuration

import dev.d1s.vtitbidratingnotifier.builder.BotBehaviourBuilder
import dev.d1s.vtitbidratingnotifier.properties.VtitbidRatingNotifierConfigurationProperties
import dev.inmo.tgbotapi.extensions.api.telegramBot
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotConfiguration {

    @set:Autowired
    lateinit var properties: VtitbidRatingNotifierConfigurationProperties

    @set:Autowired
    lateinit var botBehaviourBuilder: BotBehaviourBuilder

    @Bean
    fun telegramBot() = telegramBot(properties.botToken).apply {
        with(botBehaviourBuilder) {
            runBlocking {
                buildBehaviour()
            }
        }
    }
}