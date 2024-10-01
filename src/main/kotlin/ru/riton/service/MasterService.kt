package ru.riton.ru.riton.service

import org.springframework.stereotype.Service
import ru.riton.exception.NoDataFoundException
import ru.riton.model.MastersEntity
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.repository.MasterRepository

@Service
class MasterService(
    private val masterRepository: MasterRepository,
    private val userService: UserService
) {

    fun addMaster(userDto: UserDto): MastersEntity { //todo refactor
        val user = try {
            userService.getUser(userDto.phoneNumber)
        } catch (ex: NoDataFoundException) {
            userService.addUser(userDto)
        }

        val master = MastersEntity(user.name)
        master.user = user

        return masterRepository.save(master)
    }


}