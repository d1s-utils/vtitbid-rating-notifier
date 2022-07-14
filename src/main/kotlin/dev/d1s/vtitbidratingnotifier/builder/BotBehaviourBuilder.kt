package dev.d1s.vtitbidratingnotifier.builder

import dev.inmo.tgbotapi.bot.TelegramBot

interface BotBehaviourBuilder {

    suspend fun TelegramBot.buildBehaviour()
}