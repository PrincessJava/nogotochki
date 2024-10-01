package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.enums.WeekDay

interface ScheduleRepository : JpaRepository<ScheduleEntity, Int> {

    @Query("select s from ScheduleEntity s " +
            "where s.masterId = ?1" +
            "and s.capacity > (select COUNT(distinct a.user) from AppointmentEntity a " +
            "                                where a.slot.id = s.id " +
            "                                group by a.slot.id)")
    fun getFreeByMasterId(masterId: Int): List<ScheduleEntity>

    @Query("select s from ScheduleEntity s " +
            "where s.weekDay in ?1 " +
            "and s.capacity > (select COUNT(distinct a.user) from AppointmentEntity a " +
            "                                where a.slot.id = s.id " +
            "                                group by a.slot.id)")
    fun getFreeByWeekDays(weekDays: List<WeekDay>): List<ScheduleEntity>

}