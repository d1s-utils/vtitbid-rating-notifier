package dev.d1s.vtitbidratingnotifier.watcher.impl

import dev.d1s.vtitbidratingnotifier.service.NotificationService
import dev.d1s.vtitbidratingnotifier.service.RatingService
import dev.d1s.vtitbidratingnotifier.watcher.RatingWatcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RatingWatcherImpl : RatingWatcher {

    @set:Autowired
    lateinit var ratingService: RatingService

    @set:Autowired
    lateinit var notificationService: NotificationService

    private var lastDate: String? = null

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    override fun checkRating() {
        val rating = ratingService.requestAndParseRating()
            ?: throw IllegalStateException("Rating is not available.")

        val date = rating.date

        if (lastDate != date) {
            notificationService.sendNotification(rating)

            lastDate = date
        }
    }
}