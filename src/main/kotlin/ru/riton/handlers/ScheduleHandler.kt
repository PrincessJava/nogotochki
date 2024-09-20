package ru.riton.ru.riton.handlers

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.NogotochkiBot
import ru.riton.ru.riton.model.enums.HandlerName

@Component
class ScheduleHandler(private val applicationContext: ApplicationContext) : CallbackHandler {
    override val name = HandlerName.SCHEDULE

    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>) {
        val command = applicationContext.getBean(NogotochkiBot::class.java).getCommand(arguments.first())
        command.execute(absSender, callbackQuery.from, (callbackQuery.message as Message).chat, arrayOf())
    }
}