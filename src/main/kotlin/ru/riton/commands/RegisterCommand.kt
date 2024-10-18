package ru.riton.ru.riton.commands

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.model.enums.CommandName
import ru.riton.ru.riton.model.enums.UserState
import ru.riton.ru.riton.service.UserService
import ru.riton.ru.riton.service.UserSessionService


@Component
class RegisterCommand(private val userSessionService: UserSessionService,
                      private val userService: UserService) : BotCommand(CommandName.REGISTER.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val userId = user.id
        val session = userSessionService.getSession(userId)

        if (session.state == UserState.COMPLETE) {
            absSender.execute(SendMessage(chat.id.toString(), "Вы уже зарегистрированы."))
        } else {
            absSender.execute(SendMessage(chat.id.toString(), "Введите ваше имя:"))
            session.state = UserState.ASKING_NAME
            userSessionService.updateSession(userId, session)
        }
    }
}