package ru.riton.ru.riton.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.riton.ru.riton.repository.RoleRepository
import ru.riton.ru.riton.repository.UserRepository


@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository, private val roleRepository: RoleRepository): UserDetailsService {

    override fun loadUserByUsername(phone: String): UserDetails {
        val user = userRepository.findByPhoneNumber(phone)
            ?: return User(" ", " ", true, true, true, true, emptyList())

        return User(user.phoneNumber, user.password, true, true, true, true, emptyList())
    }
}