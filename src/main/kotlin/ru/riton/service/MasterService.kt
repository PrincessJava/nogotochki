package ru.riton.ru.riton.service

import org.springframework.stereotype.Service
import ru.riton.ru.riton.exception.UserException
import ru.riton.model.MastersEntity
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.UserRequest
import ru.riton.ru.riton.repository.MasterRepository

@Service
class MasterService(
    private val masterRepository: MasterRepository,
    private val userService: UserService
) {

    fun addMaster(userRequest: UserRequest): MastersEntity { //todo refactor
        var user: UserEntity? = null
        val phone = userRequest.phone

        if (userService.existsByPhoneNumber(phone)) {
            user = userService.getUser(phone)
            if (masterRepository.existsByUserId(user.id)) {
                throw UserException(
                    "Этот мастер уже есть в базе",
                    "Master with phone number $phone already exists"
                )
            }
        } else {
            user = userService.addUser(userRequest)
        }

        val master = MastersEntity(user.name)
        master.user = user

        return masterRepository.save(master)
    }

    fun deleteMaster(id: Int) {
        masterRepository.deleteById(id)
    }

    fun getAll(): List<MastersEntity> {
        return masterRepository.findAll()
    }

}