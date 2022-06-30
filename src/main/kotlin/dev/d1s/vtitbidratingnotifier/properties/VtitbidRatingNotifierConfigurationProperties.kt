package dev.d1s.vtitbidratingnotifier.properties

import dev.d1s.vtitbidratingnotifier.constant.VTITBID_RATING_NOTIFIER_PROPERTY_PREFIX
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.util.StringUtils
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = VTITBID_RATING_NOTIFIER_PROPERTY_PREFIX)
data class VtitbidRatingNotifierConfigurationProperties(
    @NotBlank
    val specialization: String,

    val costRecovery: Boolean = true,

    @NotEmpty
    private val discordWebhooks: String
) {
    val webhooks: Set<String> = StringUtils.commaDelimitedListToSet(discordWebhooks)
}