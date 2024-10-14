package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.enums.WeekDay

interface ScheduleRepository : JpaRepository<ScheduleEntity, Int> {

    @Query("select s from ScheduleEntity s " +
            "where s.masterId = ?1 " +
            "and s.capacity > (select COALESCE(COUNT(distinct a.user), 0) from AppointmentEntity a " +
            "                  where a.slot.id = s.id)")
    fun getFreeByMasterId(masterId: Int): List<ScheduleEntity>

    @Query("select s from ScheduleEntity s " +
            "where s.weekDay in ?1 " +
            "and s.capacity > (select COALESCE(COUNT(distinct a.user), 0) from AppointmentEntity a " +
            "                  where a.slot.id = s.id)")
    fun getFreeByWeekDays(weekDays: List<WeekDay>): List<ScheduleEntity>

}