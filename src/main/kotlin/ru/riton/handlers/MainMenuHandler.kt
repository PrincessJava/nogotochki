package ru.riton.ru.riton.handlers

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.NogotochkiBot
import ru.riton.ru.riton.model.enums.ArgumentCode
import ru.riton.ru.riton.model.enums.HandlerName

@Component
class MainMenuHandler(private val applicationContext: ApplicationContext) : CallbackHandler {
    override val name: HandlerName = HandlerName.MAIN_MENU

    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: Map<ArgumentCode, String>) {
        val command = applicationContext.getBean(NogotochkiBot::class.java).getCommand(arguments[ArgumentCode.COMMAND_NAME]!!)
        command.execute(absSender, callbackQuery.from, (callbackQuery.message as Message).chat, arrayOf())
//        removeLastMessage(absSender, callbackQuery)
    }
}