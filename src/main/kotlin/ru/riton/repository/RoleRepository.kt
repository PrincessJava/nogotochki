package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.riton.model.RoleEntity
import ru.riton.ru.riton.model.enums.Role

interface RoleRepository : JpaRepository<RoleEntity,Int> {
    fun findByName(name: Role): RoleEntity
}