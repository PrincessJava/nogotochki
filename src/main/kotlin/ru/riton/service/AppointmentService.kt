package ru.riton.ru.riton.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.riton.exception.NoDataFoundException
import ru.riton.model.AppointmentEntity
import ru.riton.model.ScheduleEntity
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.AddAppointmentDto
import ru.riton.ru.riton.repository.AppointmentRepository
import ru.riton.ru.riton.repository.ScheduleRepository
import ru.riton.ru.riton.repository.UserRepository


@Service
class AppointmentService(private val appointmentRepository: AppointmentRepository,
    private val scheduleRepository: ScheduleRepository,
    private val userRepository: UserRepository) {

    fun addAppointment(appointmentDto: AddAppointmentDto): AppointmentEntity {
        val slot: ScheduleEntity = scheduleRepository.findById(appointmentDto.slotId)
            .orElseThrow { NoDataFoundException("Расписание этого мастера не найдено","Schedule ${appointmentDto.slotId} not found") }
        val user: UserEntity = userRepository.findById(appointmentDto.userId)
            .orElseThrow { NoDataFoundException("Мастер не найден", "User ${appointmentDto.userId} not found") }

        val appointment = AppointmentEntity()
        appointment.slot = slot
        appointment.user = user

        return appointmentRepository.save(appointment)
    }

    fun getAppointments(userId: Int): List<AppointmentEntity> {
       return appointmentRepository.findByUserId(userId)
    }

}