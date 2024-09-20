package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.riton.model.UserEntity

interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
}