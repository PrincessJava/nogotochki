package ru.riton.ru.riton.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import ru.riton.exception.NoDataFoundException
import ru.riton.model.AppointmentEntity
import ru.riton.model.ScheduleEntity
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.AddAppointmentDto
import ru.riton.ru.riton.repository.AppointmentRepository
import ru.riton.ru.riton.repository.ScheduleRepository
import ru.riton.ru.riton.repository.UserRepository
import java.sql.Timestamp


@Service
class AppointmentService(
    private val appointmentRepository: AppointmentRepository,
    private val scheduleRepository: ScheduleRepository,
    private val userRepository: UserRepository
) {

    fun addAppointment(userId: Int, slotId: Int): AppointmentEntity {
        val slot: ScheduleEntity = scheduleRepository.findById(slotId)
            .orElseThrow { NoDataFoundException("Расписание этого мастера не найдено", "Schedule $slotId not found") }
        val user: UserEntity = userRepository.findById(userId)
            .orElseThrow { NoDataFoundException("Мастер не найден", "User $userId not found") }


        val appointment = AppointmentEntity()
        appointment.slot = slot
        appointment.user = user

        return appointmentRepository.save(appointment)
    }

    fun getAppointments(userId: Int): List<AppointmentEntity> {
        return appointmentRepository.findByUserId(userId)
    }

    fun checkUserAppointments(userId: Int, slotId: Int, start: Timestamp, finish: Timestamp): List<AppointmentEntity> {
        return appointmentRepository.checkUserAppointments(userId, slotId, start, finish)
    }
}