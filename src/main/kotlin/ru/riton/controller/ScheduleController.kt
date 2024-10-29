package ru.riton.ru.riton.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.dto.ScheduleRequest
import ru.riton.ru.riton.model.dto.ScheduleResponse
import ru.riton.ru.riton.parseDate
import ru.riton.ru.riton.service.ScheduleService
import java.sql.Timestamp

@RestController
@RequestMapping("/schedule")
class ScheduleController(private val scheduleService: ScheduleService) {

    @GetMapping("/{masterId}")
    fun getFreeSlotsByMaster(@PathVariable masterId: Int): ResponseEntity<List<ScheduleEntity>> {
        return ResponseEntity.ok(scheduleService.getFreeSlotsByMaster(masterId))
    }

//    @GetMapping("/")
//    fun getFreeSlotsByWeekDays(@RequestParam weekDays: List<WeekDay>): ResponseEntity<List<ScheduleEntity>> {
//        return ResponseEntity.ok(scheduleService.getFreeSlotsByWeekDays(weekDays))
//    }

    @PostMapping("/admin/slot")
    fun addSlot(@RequestBody scheduleRequest: ScheduleRequest): ResponseEntity<ScheduleEntity> {
        return ResponseEntity.ok(scheduleService.addSlot(scheduleRequest))
    }

    @GetMapping
    fun getAllSlotsByWeek(@RequestParam date: String): ResponseEntity<List<ScheduleResponse>?> {
        return ResponseEntity.ok(scheduleService.getAllSlotsByWeek(Timestamp.valueOf(parseDate(date))))
    }
}