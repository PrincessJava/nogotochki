package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.riton.model.ScheduleEntity
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.dto.ScheduleDto
import ru.riton.ru.riton.model.dto.SetPasswordDto
import ru.riton.ru.riton.model.dto.UserDto
import ru.riton.ru.riton.model.enums.WeekDay
import ru.riton.ru.riton.service.ScheduleService

@RestController
@RequestMapping("/schedule")
class ScheduleController(private val scheduleService: ScheduleService) {

    @GetMapping("/{masterId}")
    fun getFreeSlotsByMaster(@PathVariable masterId: Int): ResponseEntity<List<ScheduleEntity>> {
        return ResponseEntity.ok(scheduleService.getFreeSlotsByMaster(masterId))
    }

    @GetMapping("/")
    fun getFreeSlotsByWeekDays(@RequestParam weekDays: List<WeekDay>): ResponseEntity<List<ScheduleEntity>> {
        return ResponseEntity.ok(scheduleService.getFreeSlotsByWeekDays(weekDays))
    }

//    @PutMapping
//    fun setPassword(@RequestBody setPasswordDto: SetPasswordDto): ResponseEntity<Unit> {
//        scheduleService.setPassword(setPasswordDto)
//        return ResponseEntity.ok().build()
//    }

    @PostMapping("/admin/slot")
    fun addSlot(@RequestBody scheduleDto: ScheduleDto): ResponseEntity<ScheduleEntity> {
        return ResponseEntity.ok(scheduleService.addSlot(scheduleDto))
    }
}