package dev.d1s.vtitbidratingnotifier.service.impl

import club.minnced.discord.webhook.WebhookCluster
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import dev.d1s.vtitbidratingnotifier.constant.VTITBID_RATING_LOCATION
import dev.d1s.vtitbidratingnotifier.entity.Rating
import dev.d1s.vtitbidratingnotifier.service.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl : NotificationService {

    @set:Autowired
    lateinit var webhookCluster: WebhookCluster

    override fun sendNotification(rating: Rating) {
        webhookCluster.broadcast(
            WebhookEmbedBuilder().apply {
                setTitle(
                    WebhookEmbed.EmbedTitle("Получен обновленный рейтинг.", VTITBID_RATING_LOCATION)
                )
                setColor(0x0098ff) // голубой (не я)
                field("Специализация", rating.specialization)
                field(
                    "Коммерция?", if (rating.costRecovery) {
                        "Да"
                    } else {
                        "Нет"
                    }
                )
                field("Дата обновления", rating.date)
                setDescription(
                    "```\n" + rating.entries.joinToString("\n") {
                        "${it.position.toString().wrap()} " +
                                "${it.applicantName.wrap()} " +
                                "${it.registrationNumber.wrap()} " +
                                "${it.certificateType.wrap()} " +
                                it.averageScore.wrap()
                    } + "\n```"
                )
            }.build()
        )
    }

    private fun WebhookEmbedBuilder.field(name: String, value: String) = addField(
        WebhookEmbed.EmbedField(true, name, value)
    )

    private fun String.wrap() = "[$this]"
}