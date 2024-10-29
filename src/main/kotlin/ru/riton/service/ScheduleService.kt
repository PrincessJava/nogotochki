package ru.riton.ru.riton.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.riton.ru.riton.exception.UserException
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.dto.ScheduleRequest
import ru.riton.ru.riton.model.dto.ScheduleResponse
import ru.riton.ru.riton.model.enums.ScheduleType
import ru.riton.ru.riton.model.enums.WeekDay
import ru.riton.ru.riton.repository.MasterRepository
import ru.riton.ru.riton.repository.ScheduleRepository
import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository,
    private val masterRepository: MasterRepository,
    @Value("\${app.open-weeks-amount}") val openWeeksAmount: Long
) {
    @Transactional
    @Scheduled(cron = "0 0 0 * * MON") // Запуск каждый понедельник в 00:00
    fun extendRegularSchedules() {
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

    fun addSlot(scheduleRequest: ScheduleRequest): ScheduleEntity {
        val master = masterRepository.findById(scheduleRequest.masterId)
        val schedule = ScheduleEntity(
            master.get(), scheduleRequest.start, scheduleRequest.finish, //todo check not null
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

    fun getFreeSlotsByMaster(masterId: Int): List<ScheduleEntity> {
        return scheduleRepository.getFreeByMasterId(masterId)
    }

    fun getFreeSlotsByWeekDays(weekDays: List<WeekDay>): List<ScheduleEntity> {
        val weekDaysParam = weekDays.ifEmpty { WeekDay.entries.distinct() }
        val nextStart = LocalDateTime.now()
        val nextFinish = LocalDateTime.now().plusWeeks(openWeeksAmount)
        return scheduleRepository.getFreeByWeekDays(
            weekDaysParam,
            Timestamp.valueOf(nextStart),
            Timestamp.valueOf(nextFinish)
        )
    }

    fun getFreeRegularSlotsByRange(nextStartUsr: LocalDateTime, nextEndUsr: LocalDateTime, descriptionId: String?, masterId: String?): List<ScheduleEntity> {
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

    fun getFreeRegularSlotsByDayAndMaster(time: LocalDateTime, master: String): List<ScheduleEntity> {
        val start = time.with(LocalTime.MIN)
        val end = time.with(LocalTime.MAX)
        val masterId = master.toInt()

        return scheduleRepository.getFreeByRangeMasterAndDescription(
            ScheduleType.REGULAR,
            Timestamp.valueOf(start),
            Timestamp.valueOf(end), null, masterId
        )
    }

    fun getById(id: Int?): ScheduleEntity {
        id ?: throw UserException("Слот не найден", "Slot is is null")
        return scheduleRepository.findById(id).orElseThrow { UserException("Слот не найден. Возможно он был удален", "No slot found by id = $id") }
    }

    fun getAllSlotsByWeek(date: Timestamp): List<ScheduleResponse>? {
        val dateTime = date.toLocalDateTime()
        val startOfTheWeek = dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .with(LocalTime.MIN)
        val endOfTheWeek = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .with(LocalTime.MAX)

        return scheduleRepository.getAllByStartAndFinish(Timestamp.valueOf(startOfTheWeek), Timestamp.valueOf(endOfTheWeek))
    }


}