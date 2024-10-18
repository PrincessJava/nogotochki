package ru.riton.ru.riton.service

import org.springframework.stereotype.Service
import ru.riton.ru.riton.model.UserSession

@Service
class UserSessionService {
    private val sessions = mutableMapOf<Long, UserSession>()

    fun getSession(userId: Long): UserSession {
        return sessions.getOrPut(userId) { UserSession() }
    }

    fun updateSession(userId: Long, session: UserSession) {
        sessions[userId] = session
    }

    fun clearSession(userId: Long) {
        sessions.remove(userId)
    }
}