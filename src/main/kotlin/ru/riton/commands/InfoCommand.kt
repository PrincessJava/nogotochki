package ru.riton.ru.riton.commands

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessage
import ru.riton.ru.riton.model.CommandName

@Component
class InfoCommand : BotCommand(CommandName.INFO.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(createMessage(chat.id.toString(), "Тут всякая информация про организацию"))
    }
}