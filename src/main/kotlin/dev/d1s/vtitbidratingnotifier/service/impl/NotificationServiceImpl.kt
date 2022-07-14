package dev.d1s.vtitbidratingnotifier.service.impl

import dev.d1s.vtitbidratingnotifier.entity.Rating
import dev.d1s.vtitbidratingnotifier.service.NotificationService
import dev.d1s.vtitbidratingnotifier.service.RatingService
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.code
import dev.inmo.tgbotapi.types.message.textsources.regular
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.CopyOnWriteArraySet

@Service
class NotificationServiceImpl : NotificationService {

    @set:Autowired
    lateinit var telegramBot: TelegramBot

    @set:Autowired
    lateinit var ratingService: RatingService

    private val subscribers = CopyOnWriteArraySet<ChatId>()

    private lateinit var lastRating: Rating

    override suspend fun subscribe(chat: ChatId) {
        if (!subscribers.add(chat)) {
            throw IllegalArgumentException()
        }

        if (!::lastRating.isInitialized) {
            lastRating = ratingService.requestAndParseRating()
        }

        telegramBot.sendMessage(
            chat,
            this.serializeRating(lastRating)
        )
    }

    override suspend fun sendNotification(rating: Rating) {
        lastRating = rating

        subscribers.forEach {
            telegramBot.sendMessage(
                it,
                serializeRating(rating)
            )
        }
    }

    private fun serializeRating(rating: Rating) = listOf(
        regular("❯ "),
        bold("Получен обновлённый рейтинг."),
        regular("\n❯ "),
        bold("Специализация: "),
        regular(rating.specialization),
        regular("\n❯ "),
        bold("Коммерция: "),
        regular(
            if (rating.costRecovery) {
                "да"
            } else {
                "нет"
            }
        ),
        regular("\n❯ "),
        bold("Дата обновления: "),
        regular(rating.date),
        regular("\n\n"),
        code(
            rating.entries.joinToString("\n") {
                "${it.position.toString().wrap()} " +
                        "${it.applicantName.wrap()} " +
                        "${it.registrationNumber.wrap()} " +
                        "${it.certificateType.wrap()} " +
                        it.averageScore.wrap()
            }
        )
    )

    private fun String.wrap() = "[$this]"
}