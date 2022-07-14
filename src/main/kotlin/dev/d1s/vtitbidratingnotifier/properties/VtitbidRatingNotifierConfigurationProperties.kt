package dev.d1s.vtitbidratingnotifier.properties

import dev.d1s.vtitbidratingnotifier.constant.VTITBID_RATING_NOTIFIER_PROPERTY_PREFIX
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = VTITBID_RATING_NOTIFIER_PROPERTY_PREFIX)
data class VtitbidRatingNotifierConfigurationProperties(

    @NotBlank
    val specialization: String,

    val costRecovery: Boolean = true,

    @NotBlank
    val botToken: String
)