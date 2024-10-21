package ru.riton.ru.riton.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.riton.exception.UserException
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.dto.ScheduleDto
import ru.riton.ru.riton.model.enums.ScheduleType
import ru.riton.ru.riton.model.enums.WeekDay
import ru.riton.ru.riton.repository.MasterRepository
import ru.riton.ru.riton.repository.ScheduleRepository
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime

@Service
open class ScheduleService(
    private val scheduleRepository: ScheduleRepository,
    private val masterRepository: MasterRepository,
    @Value("\${app.open-weeks-amount}") val openWeeksAmount: Long
) {
    @Transactional
    @Scheduled(cron = "0 0 0 * * MON") // Запуск каждый понедельник в 00:00
    open fun extendRegularSchedules() {
        val today = LocalDateTime.now()
        val oneWeekAgo = today.minusWeeks(1)

        val regularSchedules = scheduleRepository.getByTypeLastWeek(
            ScheduleType.REGULAR,
            Timestamp.valueOf(oneWeekAgo),
            Timestamp.valueOf(today)
        )

        regularSchedules.forEach { schedule ->
            val nextStart = schedule.start.toLocalDateTime().plusWeeks(openWeeksAmount)
            val nextFinish = schedule.finish.toLocalDateTime().plusWeeks(openWeeksAmount)

            // Создаем новую запись с теми же параметрами, но с датами через две недели
            val newSchedule = ScheduleEntity.copy(
                oldSchedule = schedule,
                start = Timestamp.valueOf(nextStart),
                finish = Timestamp.valueOf(nextFinish)
            )

            scheduleRepository.save(newSchedule)
        }
    }

    open fun addSlot(scheduleDto: ScheduleDto): ScheduleEntity {
        val master = masterRepository.findById(scheduleDto.masterId)
        val schedule = ScheduleEntity(
            master.get(), scheduleDto.start, scheduleDto.finish, //todo check not null
//            scheduleDto.weekDay, scheduleDto.location, scheduleDto.description, scheduleDto.capacity, scheduleDto.type
        )
        val newSchedule = scheduleRepository.save(schedule)

        for (weekCount in openWeeksAmount - 1 downTo 1) {
            val nextStart = schedule.start.toLocalDateTime().plusWeeks(weekCount)
            val nextFinish = schedule.finish.toLocalDateTime().plusWeeks(weekCount)

            val scheduleCopy = ScheduleEntity.copy(
                oldSchedule = schedule,
                start = Timestamp.valueOf(nextStart),
                finish = Timestamp.valueOf(nextFinish)
            )
            scheduleRepository.save(scheduleCopy)
        }

        return newSchedule
    }

    open fun getFreeSlotsByMaster(masterId: Int): List<ScheduleEntity> {
        return scheduleRepository.getFreeByMasterId(masterId)
    }

    open fun getFreeSlotsByWeekDays(weekDays: List<WeekDay>): List<ScheduleEntity> {
        val weekDaysParam = weekDays.ifEmpty { WeekDay.entries.distinct() }
        val nextStart = LocalDateTime.now()
        val nextFinish = LocalDateTime.now().plusWeeks(openWeeksAmount)
        return scheduleRepository.getFreeByWeekDays(
            weekDaysParam,
            Timestamp.valueOf(nextStart),
            Timestamp.valueOf(nextFinish)
        )
    }

    open fun getFreeRegularSlotsByRange(nextStartUsr: LocalDateTime, nextEndUsr: LocalDateTime, descriptionId: String?, masterId: String?): List<ScheduleEntity> {
        val nextStart = if (nextStartUsr.isAfter(LocalDateTime.now())) nextStartUsr else LocalDateTime.now()
        val nextEnd = if (nextEndUsr.isBefore(LocalDateTime.now().plusWeeks(openWeeksAmount)))
            nextEndUsr else LocalDateTime.now().plusWeeks(openWeeksAmount).with(LocalTime.MAX)

        return scheduleRepository.getFreeByRangeMasterAndDescription(
            ScheduleType.REGULAR,
            Timestamp.valueOf(nextStart),
            Timestamp.valueOf(nextEnd),
            descriptionId?.toIntOrNull(),
            masterId?.toIntOrNull()
        )
    }

    open fun getFreeRegularSlotsByDayAndMaster(time: LocalDateTime, master: String): List<ScheduleEntity> {
        val start = time.with(LocalTime.MIN)
        val end = time.with(LocalTime.MAX)
        val masterId = master.toInt()

        return scheduleRepository.getFreeByRangeMasterAndDescription(
            ScheduleType.REGULAR,
            Timestamp.valueOf(start),
            Timestamp.valueOf(end), null, masterId
        )
    }

    open fun getById(id: Int?): ScheduleEntity {
        id ?: throw UserException("Слот не найден", "Slot is is null")
        return scheduleRepository.findById(id).orElseThrow { UserException("Слот не найден. Возможно он был удален", "No slot found by id = $id") }
    }


}