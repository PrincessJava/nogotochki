package ru.riton.ru.riton.service

import org.springframework.stereotype.Service
import ru.riton.exception.NoDataFoundException
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.dto.ScheduleDto
import ru.riton.ru.riton.model.enums.WeekDay
import ru.riton.ru.riton.repository.ScheduleRepository

@Service
class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    fun addSlot(scheduleDto: ScheduleDto): ScheduleEntity {
        val schedule = ScheduleEntity(scheduleDto.masterId, scheduleDto.start, scheduleDto.end,
            scheduleDto.weekDay, scheduleDto.location, scheduleDto.description, scheduleDto.capacity, scheduleDto.type)

        return scheduleRepository.save(schedule)
    }

    fun getFreeSlotsByMaster(masterId: Int): List<ScheduleEntity> {
        return scheduleRepository.getFreeByMasterId(masterId)
    }

    fun getFreeSlotsByWeekDays(weekDays: List<WeekDay>): List<ScheduleEntity> {
        val weekDaysParam = weekDays.ifEmpty { WeekDay.entries.distinct() }
        return scheduleRepository.getFreeByWeekDays(weekDaysParam)
    }
}