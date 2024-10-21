package ru.riton.ru.riton.model.dto

import ru.riton.ru.riton.model.enums.ScheduleType
import ru.riton.ru.riton.model.enums.WeekDay
import java.sql.Timestamp

data class ScheduleDto(
    val masterId: Int,
    val start: Timestamp,
    val finish: Timestamp,
    val weekDay: WeekDay,
    var location: String?,
    var description: String?,
    val capacity: Int,
    val type: ScheduleType
)