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
class MenuServiceCommand : BotCommand(CommandName.SCHEDULE.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(showMenu(chat))
    }

    private fun showMenu(chat: Chat): SendMessage  {
        val serviceCallback = HandlerName.SERVICE.text
        val masterCallback = HandlerName.MASTER.text

        return createMessageWithInlineButtons(
            chat.id.toString(),
            "Как вам удобнее записаться?",
            listOf(
                listOf("$serviceCallback|service" to "На услугу", "$masterCallback|master" to "К мастеру")
            )
        )
    }
}