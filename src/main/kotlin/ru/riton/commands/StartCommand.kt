package ru.riton.ru.riton.commands

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessageWithInlineButtons
import ru.riton.ru.riton.model.enums.CommandName
import ru.riton.ru.riton.model.enums.HandlerName

@Component
class StartCommand : BotCommand(CommandName.START.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val callback = HandlerName.MAIN_MENU.text
        absSender.execute(showMenu(chat, user, callback))
    }

    private fun showMenu(chat: Chat, user: User, callback: String): SendMessage =
        createMessageWithInlineButtons(
            chat.id.toString(),
            "Привет, ${user.firstName}!",
            listOf(
                listOf("$callback|buy" to "Купить абонемент", "$callback|schedule" to "Записаться"),
                listOf("$callback|info" to "Информация", "$callback|admin" to "Связь с администратором"),
            )
        )
}