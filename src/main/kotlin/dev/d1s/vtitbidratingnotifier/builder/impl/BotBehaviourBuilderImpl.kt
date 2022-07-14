package dev.d1s.vtitbidratingnotifier.builder.impl

import dev.d1s.vtitbidratingnotifier.builder.BotBehaviourBuilder
import dev.d1s.vtitbidratingnotifier.properties.VtitbidRatingNotifierConfigurationProperties
import dev.d1s.vtitbidratingnotifier.service.NotificationService
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BotBehaviourBuilderImpl : BotBehaviourBuilder {

    @set:Autowired
    lateinit var notificationService: NotificationService

    @set:Autowired
    lateinit var properties: VtitbidRatingNotifierConfigurationProperties

    override suspend fun TelegramBot.buildBehaviour() {
        buildBehaviourWithLongPolling {
            onCommand(START_COMMAND) {
                try {
                    notificationService.subscribe(it.chat.id)

                    reply(it, "Вы подписались на обновления рейтинга ${properties.specialization}.")
                } catch (_: IllegalArgumentException) {
                    reply(it, "Вы уже подписаны.")
                }
            }
        }
    }

    private companion object {

        private const val START_COMMAND = "start"
    }
}