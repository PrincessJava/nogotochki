package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.riton.model.AdminEntity

interface AdminRepository : JpaRepository<AdminEntity, Int> {
}