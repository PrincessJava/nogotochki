package ru.riton.ru.riton.service

import org.springframework.stereotype.Service
import ru.riton.exception.NoDataFoundException
import ru.riton.exception.UserException
import ru.riton.model.MastersEntity
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.repository.MasterRepository
import ru.riton.ru.riton.repository.UserRepository

@Service
class MasterService(
    private val masterRepository: MasterRepository,
    private val userService: UserService
) {

    fun addMaster(userDto: UserDto): MastersEntity { //todo refactor
        var user: UserEntity? = null
        val phone = userDto.phoneNumber

        if (userService.checkUserExists(phone)) {
            user = userService.getUser(phone)
            if (masterRepository.existsByUserId(user.id)) {
                throw UserException(
                    "Этот мастер уже есть в базе",
                    "Master with phone number $phone already exists"
                )
            }
        } else {
            user = userService.addUser(userDto)
        }

        val master = MastersEntity(user.name)
        master.user = user

        return masterRepository.save(master)
    }

    fun deleteMaster(id: Int) {
        masterRepository.deleteById(id)
    }

}