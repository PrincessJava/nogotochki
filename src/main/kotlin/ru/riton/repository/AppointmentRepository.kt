package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.riton.model.AppointmentEntity

interface AppointmentRepository : JpaRepository<AppointmentEntity, Int> {

    fun findByUserId(userId: Int): List<AppointmentEntity>
}