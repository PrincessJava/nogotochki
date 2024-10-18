package ru.riton.ru.riton.handlers

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.model.UserSession
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.model.enums.UserState
import ru.riton.ru.riton.service.UserService
import ru.riton.ru.riton.service.UserSessionService

@Component
class MessageHandler(
    private val userSessionService: UserSessionService,
    private val userService: UserService
) : CallbackHandler {
    override val name: HandlerName = HandlerName.MESSAGE
    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>) {

        val message = callbackQuery.message
        val userId = callbackQuery.from.id
        val chatId = message.chatId
        val session = userSessionService.getSession(userId)

        when (session.state) {
            UserState.ASKING_NAME -> {
                val userName = callbackQuery.data
                session.name = userName
                session.state = UserState.ASKING_PHONE
                userSessionService.updateSession(userId, session)

                absSender.execute(SendMessage(chatId.toString(), "Введите ваш номер телефона:"))
            }

            UserState.ASKING_PHONE -> {
                val phoneNumber = callbackQuery.data
                session.phoneNumber = phoneNumber
                session.state = UserState.COMPLETE
                userSessionService.updateSession(userId, session)

                saveOrUpdateUser(session, userId)
                userSessionService.clearSession(userId)

                absSender.execute(SendMessage(chatId.toString(), "Спасибо, вы зарегистрированы!"))
            }

            UserState.COMPLETE -> {
                absSender.execute(SendMessage(chatId.toString(), "Вы уже зарегистрированы."))
            }
        }
    }

    private fun saveOrUpdateUser(session: UserSession, userId: Long) {
        if (userService.checkUserExistsByPhone(session.phoneNumber!!)) {
            userService.addTgId(session.phoneNumber!!, userId)
        }
        // Сохранение данных в базе
        val user = UserDto(name = session.name!!, phoneNumber = session.phoneNumber!!, tgId = userId)
        userService.addUser(user)
    }

}
