package ru.riton

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import ru.riton.ru.riton.config.TelegramProperties


@SpringBootApplication
@EnableConfigurationProperties(TelegramProperties::class)
open class TelegramBotApplication

fun main(args: Array<String>) {
    SpringApplication.run(TelegramBotApplication::class.java, *args)
}