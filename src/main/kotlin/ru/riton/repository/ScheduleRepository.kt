package ru.riton.ru.riton.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.dto.ScheduleResponse
import ru.riton.ru.riton.model.enums.ScheduleType
import ru.riton.ru.riton.model.enums.WeekDay
import java.sql.Timestamp

interface ScheduleRepository : JpaRepository<ScheduleEntity, Int>, ScheduleRepositoryCustom {

    @Query(
        "select s from ScheduleEntity s " +
                "where s.master.id = ?1 " +
                "and s.capacity > (select COALESCE(COUNT(distinct a.user), 0) from AppointmentEntity a " +
                "                  where a.slot.id = s.id)"
    )
    fun getFreeByMasterId(masterId: Int): List<ScheduleEntity>

    @Query(
        "select s from ScheduleEntity s " +
                "where s.weekDay in :weekDays " +
                "and s.start between :startDay and :endDay " +
                "and s.capacity > (select COALESCE(COUNT(distinct a.user), 0) from AppointmentEntity a " +
                "                  where a.slot.id = s.id)"
    )
    fun getFreeByWeekDays(
        @Param("weekDays") weekDays: List<WeekDay>,
        @Param("startDay") startDay: Timestamp,
        @Param("endDay") endDay: Timestamp
    ): List<ScheduleEntity>

    @Query(
        "select s from ScheduleEntity s " +
                "where s.start between :startDay and :endDay " +
                "and s.type = :type "
    )
    fun getByTypeLastWeek(
        @Param("type") type: ScheduleType,
        @Param("startDay") startDay: Timestamp,
        @Param("endDay") endDay: Timestamp
    ): List<ScheduleEntity>

    @Query(
        "select s from ScheduleEntity s " +
                "where s.start between :start and :finish " +
                "and s.type = :type " +
                "and s.capacity > (select COALESCE(COUNT(distinct a.user), 0) from AppointmentEntity a " +
                "                  where a.slot.id = s.id)"
    )
    fun getFreeByRange(@Param("type") type: ScheduleType, @Param("start") start: Timestamp, @Param("finish") finish: Timestamp): List<ScheduleEntity>

    @Query(
        "select s from ScheduleEntity s " +
                "where s.start between :start and :finish " +
                "and s.type = :type " +
                "and s.capacity > (select COALESCE(COUNT(distinct a.user.id), 0) from AppointmentEntity a " +
                "                  where a.slot.id = s.id) " +
                "and s.description.id = :descriptionId " +
                "and s.master.id = :masterId"
    )
    fun getFreeByRangeAndDescription(
        @Param("type") type: ScheduleType,
        @Param("start") start: Timestamp,
        @Param("finish") finish: Timestamp,
        @Param("descriptionId") descriptionId: Int,
        @Param("masterId") masterId: Int
    ): List<ScheduleEntity>

    @Query(
        "select new ru.riton.ru.riton.model.dto.ScheduleResponse(s.id, s.start, s.finish, s.location, s.description.value, s.capacity, s.type, s.master.name) from ScheduleEntity s " +
                "where s.start between :start and :finish "
    )
    fun getAllByStartAndFinish(@Param("start") start: Timestamp, @Param("finish") finish: Timestamp): List<ScheduleResponse>?

}