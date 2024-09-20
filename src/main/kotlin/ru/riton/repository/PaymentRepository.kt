package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.riton.model.PaymentEntity

interface PaymentRepository : JpaRepository<PaymentEntity, Int> {
}