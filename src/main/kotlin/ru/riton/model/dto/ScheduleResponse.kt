package ru.riton.ru.riton.model.dto

import ru.riton.ru.riton.model.enums.ScheduleType
import java.sql.Timestamp

data class ScheduleResponse (
    val id: Int,
    val start: Timestamp,
    val finish: Timestamp,
    var location: String,
    var description: String,
    val capacity: Int,
    val type: ScheduleType,
    val master: String
)