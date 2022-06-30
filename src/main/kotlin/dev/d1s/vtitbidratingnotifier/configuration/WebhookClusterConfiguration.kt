package dev.d1s.vtitbidratingnotifier.configuration

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.WebhookCluster
import dev.d1s.vtitbidratingnotifier.properties.VtitbidRatingNotifierConfigurationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebhookClusterConfiguration {

    @set:Autowired
    lateinit var properties: VtitbidRatingNotifierConfigurationProperties

    @Bean
    fun webhookCluster() = WebhookCluster(
        properties.webhooks.map {
            WebhookClient.withUrl(it)
        }
    )
}