package ru.riton.model

import jakarta.persistence.*
import ru.riton.ru.riton.model.BasicEntity
import ru.riton.ru.riton.model.enums.ScheduleType
import ru.riton.ru.riton.model.enums.WeekDay
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "schedule", schema = "public", catalog = "nogotochki")
class ScheduleEntity(
    @Basic
    @Column(name = "master_id")
    var masterId: Int = 0,

    @Basic
    @Column(name = "start")
    var start: Timestamp = Timestamp.from(Instant.now()),

    @Basic
    @Column(name = "end")
    var end: Timestamp = Timestamp.from(Instant.now()),

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "week_day")
    var weekDay: WeekDay = WeekDay.MON,

    @Basic
    @Column(name = "location")
    var location: String? = null,

    @Basic
    @Column(name = "description")
    var description: String? = null,

    @Basic
    @Column(name = "capacity")
    var capacity: Int = 0,

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: ScheduleType = ScheduleType.REGULAR

) : BasicEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScheduleEntity

        if (masterId != other.masterId) return false
        if (start != other.start) return false
        if (end != other.end) return false
        if (weekDay != other.weekDay) return false
        if (location != other.location) return false
        if (description != other.description) return false
        if (capacity != other.capacity) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = masterId
        result = 31 * result + start.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + weekDay.hashCode()
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + capacity
        result = 31 * result + type.hashCode()
        return result
    }
}
