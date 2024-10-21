package ru.riton.ru.riton.service

import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.riton.exception.NoDataFoundException
import ru.riton.exception.UserException
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.SetPasswordDto
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.repository.UserRepository

@Service
open class UserService(private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder) {

    fun getUser(phone: String): UserEntity {
        return userRepository.findByPhoneNumber(phone) ?: throw NoDataFoundException(phone, "User not found by phone: $phone")
    }

    fun addUser(userDto: UserDto): UserEntity {
        val phoneNumber = userDto.phoneNumber
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw UserException("Пользователь с таким номером телефона $phoneNumber уже зарегистрирован",
                "User with phone $phoneNumber already exists")
        }
        val user = UserEntity(name = userDto.name, phoneNumber = phoneNumber, tgId = userDto.tgId)
        return userRepository.save(user)
    }

    fun setPassword(setPasswordDto: SetPasswordDto) {
        val userEntity = userRepository.getReferenceById(setPasswordDto.id)
        userEntity.setPassword(setPasswordDto.password, passwordEncoder)
        userRepository.save(userEntity)
    }

    open fun existsByPhoneNumber(phone: String): Boolean {
        return userRepository.existsByPhoneNumber(phone)
    }

    open fun userExistsByTgId(tgId: Long): Boolean {
        return userRepository.existsByTgId(tgId)
    }

    open fun getByTgId(tgId: Long): UserEntity {
        return userRepository.findByTgId(tgId) ?: throw NoDataFoundException(tgId.toString(), "User not found by telegram id: $tgId")
    }

    @Transactional
    open fun addTgId(phone: String, tgId: Long) {
        userRepository.updateByPhoneNumber(tgId = tgId, phone = phone)
    }
}