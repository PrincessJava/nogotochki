package ru.riton.ru.riton.handlers

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessage
import ru.riton.ru.riton.model.enums.HandlerName

@Component
class TimeHandler(private val applicationContext: ApplicationContext) : CallbackHandler {
    override val name = HandlerName.TIME


    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>) {
        absSender.execute(createMessage((callbackQuery.message as Message).chat.id.toString(), "Пришли из $arguments[1]"))
    }
}