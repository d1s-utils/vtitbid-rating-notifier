package dev.d1s.vtitbidratingnotifier.service

import dev.d1s.vtitbidratingnotifier.entity.Rating

interface NotificationService {

    fun sendNotification(rating: Rating)
}