package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.riton.model.UserEntity

interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
    fun existsByPhoneNumber(phoneNumber: String): Boolean
    fun findByTgId(tgId: Long): UserEntity?
    fun existsByTgId(tgId: Long): Boolean
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UserEntity s set s.tgId = ?1 where s.phoneNumber= ?2")
    fun updateByPhoneNumber(tgId: Long, phone: String)
}