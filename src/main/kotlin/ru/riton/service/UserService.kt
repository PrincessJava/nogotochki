package ru.riton.ru.riton.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.riton.exception.NoDataFoundException
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.SetPasswordDto
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder) {

    fun getUser(phone: String): UserEntity {
        return userRepository.findByPhoneNumber(phone) ?: throw NoDataFoundException(phone)
    }

    fun addUser(userDto: UserDto): UserEntity {
        val user = UserEntity(name = userDto.name, phoneNumber = userDto.phoneNumber, tgId = userDto.tgId)
        return userRepository.save(user) //todo check that user is new
    }

    fun setPassword(setPasswordDto: SetPasswordDto) {
        val userEntity = userRepository.getReferenceById(setPasswordDto.id)
        userEntity.setPassword(setPasswordDto.password, passwordEncoder)
        userRepository.save(userEntity)
    }

}