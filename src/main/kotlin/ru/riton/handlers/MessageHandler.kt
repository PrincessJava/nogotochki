package ru.riton.ru.riton.handlers

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.phoneMatchesPattern
import ru.riton.ru.riton.model.UserSession
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.model.enums.UserState
import ru.riton.ru.riton.service.UserService
import ru.riton.ru.riton.service.UserSessionService

@Component
class MessageHandler(
    private val userSessionService: UserSessionService,
    private val userService: UserService
) {
    fun processTextMessage(update: Update, absSender: AbsSender) {
        val message = update.message
        val userId = message.from.id
        val chatId = message.chatId.toString()
        val session = userSessionService.getSession(userId)

        when (session.state) {
            UserState.ASKING_NAME -> {
                val userName = message.text
                session.name = userName
                session.state = UserState.ASKING_PHONE
                userSessionService.updateSession(userId, session)

                absSender.execute(SendMessage(chatId, "Введите ваш номер телефона в формате 8XXXXXXXXXX:"))
            }

            UserState.ASKING_PHONE -> {
                val phoneNumber = message.text
                if (!phoneMatchesPattern(phoneNumber)) {
                    absSender.execute(SendMessage(chatId, "Пожалуйста, введите номер телефона в формате 8XXXXXXXXXX:"))
                } else {
                    session.phoneNumber = phoneNumber
                    session.state = UserState.COMPLETE
                    userSessionService.updateSession(userId, session)

                    saveOrUpdateUser(session, userId)
                    userSessionService.clearSession(userId)

                    absSender.execute(SendMessage(chatId, "Спасибо, вы зарегистрированы!"))
                }
            }

            UserState.COMPLETE -> {
                absSender.execute(SendMessage(chatId, "Вы уже зарегистрированы."))
            }
        }
    }

    private fun saveOrUpdateUser(session: UserSession, userId: Long) {
        if (userService.existsByPhoneNumber(session.phoneNumber!!)) {
            userService.addTgId(session.phoneNumber!!, userId)
        }
        val user = UserDto(name = session.name!!, phoneNumber = session.phoneNumber!!, tgId = userId)
        userService.addUser(user)
    }
}
