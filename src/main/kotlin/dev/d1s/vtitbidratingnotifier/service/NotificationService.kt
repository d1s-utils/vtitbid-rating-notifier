package dev.d1s.vtitbidratingnotifier.service

import dev.d1s.vtitbidratingnotifier.entity.Rating
import dev.inmo.tgbotapi.types.ChatId

interface NotificationService {

    suspend fun subscribe(chat: ChatId)

    suspend fun sendNotification(rating: Rating)
}