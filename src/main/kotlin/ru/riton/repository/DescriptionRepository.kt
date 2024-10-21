package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.DescriptionEntity

interface DescriptionRepository : JpaRepository<DescriptionEntity, Int> {
}