package ru.riton.ru.riton.service

import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.riton.model.UserEntity
import ru.riton.ru.riton.exception.NoDataFoundException
import ru.riton.ru.riton.exception.UserException
import ru.riton.ru.riton.model.dto.SetPasswordDto
import ru.riton.ru.riton.model.dto.UserRequest
import ru.riton.ru.riton.model.dto.UserResponse
import ru.riton.ru.riton.model.enums.Role
import ru.riton.ru.riton.repository.RoleRepository
import ru.riton.ru.riton.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository
) {

    fun getUser(phone: String): UserEntity {
        return userRepository.findByPhoneNumber(phone) ?: throw NoDataFoundException(phone, "User not found by phone: $phone")
    }

    fun addUser(userRequest: UserRequest): UserEntity {
        val phoneNumber = userRequest.phone
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw UserException(
                "Пользователь с таким номером телефона $phoneNumber уже зарегистрирован",
                "User with phone $phoneNumber already exists"
            )
        }
        val role = roleRepository.findByName(Role.USER)
        val user = UserEntity(name = userRequest.name, phoneNumber = phoneNumber, tgId = userRequest.tgId)
        user.addRole(role)
        return userRepository.save(user)
    }

    fun setPassword(setPasswordDto: SetPasswordDto) {
        val userEntity = userRepository.getReferenceById(setPasswordDto.id)
        userEntity.setPassword(setPasswordDto.password, passwordEncoder)
        userRepository.save(userEntity)
    }

    fun existsByPhoneNumber(phone: String): Boolean {
        return userRepository.existsByPhoneNumber(phone)
    }

    fun userExistsByTgId(tgId: Long): Boolean {
        return userRepository.existsByTgId(tgId)
    }

    fun getByTgId(tgId: Long): UserEntity {
        return userRepository.findByTgId(tgId) ?: throw NoDataFoundException(tgId.toString(), "User not found by telegram id: $tgId")
    }

    @Transactional
    fun addTgId(phone: String, tgId: Long) {
        userRepository.updateByPhoneNumber(tgId = tgId, phone = phone)
    }

    fun getClients(): List<UserResponse> {
        return userRepository.findAllByRole(Role.USER)
    }

    fun gelAdmins(): List<UserResponse> {
        return userRepository.findAllByRole(Role.ADMIN)
    }
    @Transactional
    fun updateUserInfo(id: Int, userRequest: UserRequest) {
        val userDB = userRepository.findById(id)
        userDB.ifPresent {
            it.name = userRequest.name
            it.phoneNumber = userRequest.phone

            userRepository.save(it)
        }
    }
}