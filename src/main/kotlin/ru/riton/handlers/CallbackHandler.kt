package ru.riton.ru.riton.handlers

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.model.enums.HandlerName

interface CallbackHandler {
    val name: HandlerName
    fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>)
}