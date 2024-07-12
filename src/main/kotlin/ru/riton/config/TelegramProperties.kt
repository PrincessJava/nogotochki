package ru.riton.ru.riton.config

import org.springframework.boot.context.properties.ConfigurationProperties

//@ConstructorBinding
@ConfigurationProperties(prefix = "telegram")
data class TelegramProperties (
    val botToken: String,
    val botUsername: String
)