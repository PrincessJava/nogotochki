package ru.riton.ru.riton.service

import org.springframework.stereotype.Service
import ru.riton.ru.riton.model.DescriptionEntity
import ru.riton.ru.riton.repository.DescriptionRepository

@Service
class DescriptionService(private val descriptionRepository: DescriptionRepository) {
    fun getAll(): List<DescriptionEntity> {
        return descriptionRepository.findAll()
    }
}