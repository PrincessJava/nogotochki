package ru.riton.ru.riton.handlers

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.NogotochkiBot
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.editLastMessage
import ru.riton.ru.riton.model.enums.ArgumentCode

@Component
class DayHandler(private val applicationContext: ApplicationContext) : CallbackHandler {
    override val name = HandlerName.DAY

    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: Map<ArgumentCode, String>) {
//        editLastMessage(absSender, callbackQuery)
        val command = applicationContext.getBean(NogotochkiBot::class.java).getCommand(arguments[ArgumentCode.COMMAND_NAME]!!)
        command.execute(absSender, callbackQuery.from, (callbackQuery.message as Message).chat, arrayOf(arguments[ArgumentCode.DESCRIPTION_ID]))
    }
}