package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.riton.model.AppointmentEntity
import java.sql.Timestamp

interface AppointmentRepository : JpaRepository<AppointmentEntity, Int> {
    fun findByUserId(userId: Int): List<AppointmentEntity>

    @Query(
        "SELECT a " +
                "FROM AppointmentEntity a " +
                "JOIN a.user u " +
                "JOIN a.slot s " +
                "WHERE s.id = :slotId " +
                "and u.id = :userId " +
                "   OR (s.start < :finish AND s.finish > :start)"
    )
    fun checkUserAppointments(@Param("userId") userId: Int, @Param("slotId") slotId: Int, @Param("start") start: Timestamp, @Param("finish") finish: Timestamp): List<AppointmentEntity>
}