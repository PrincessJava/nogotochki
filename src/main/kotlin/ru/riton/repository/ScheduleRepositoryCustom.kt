package ru.riton.ru.riton.repository

import ru.riton.model.ScheduleEntity
import ru.riton.ru.riton.model.enums.ScheduleType
import java.sql.Timestamp

interface ScheduleRepositoryCustom {
    fun getFreeByRangeMasterAndDescription(
        type: ScheduleType,
        start: Timestamp,
        finish: Timestamp,
        descriptionId: Int?,
        masterId: Int?
    ): List<ScheduleEntity>
}