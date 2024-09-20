package ru.riton.ru.riton.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.riton.ru.riton.repository.UserRepository

@Service
class AuthenticationService(private val userRepository: UserRepository) {

    private val passwordEncoder = BCryptPasswordEncoder()

    fun authenticate(phone: String, password: String): Boolean {
        val user = userRepository.findByPhoneNumber(phone)
        if (user != null) {
            return passwordEncoder.matches(password, user.password)
        }
        return false
    }
}