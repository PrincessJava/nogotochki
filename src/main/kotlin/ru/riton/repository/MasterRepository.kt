package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.riton.model.MastersEntity
import ru.riton.model.UserEntity

interface MasterRepository : JpaRepository<MastersEntity, Int> {
    fun existsByUserId(userId: Int): Boolean
}