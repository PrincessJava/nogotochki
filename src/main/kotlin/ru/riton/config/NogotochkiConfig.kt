package ru.riton.ru.riton.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.riton.ru.riton.NogotochkiBot

@Configuration
open class NogotochkiConfig {
    @Bean
    open fun telegramBotsApi(bot: NogotochkiBot): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java).apply {
            registerBot(bot)
        }
}