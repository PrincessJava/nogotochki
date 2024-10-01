package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.riton.model.AppointmentEntity
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.dto.AddAppointmentDto
import ru.riton.ru.riton.model.dto.ScheduleDto
import ru.riton.ru.riton.service.AppointmentService

@RestController
@RequestMapping("/appointment")
class AppointmentController(private val appointmentService: AppointmentService) {

    @PostMapping
    fun addSlot(@RequestBody appointmentDto: AddAppointmentDto): ResponseEntity<AppointmentEntity> {
        return ResponseEntity.ok(appointmentService.addAppointment(appointmentDto))
    }

    @GetMapping("/{userId}")
    fun getAppointments(@PathVariable userId: Int): ResponseEntity<List<AppointmentEntity>> {
        return ResponseEntity.ok(appointmentService.getAppointments(userId))
    }
}