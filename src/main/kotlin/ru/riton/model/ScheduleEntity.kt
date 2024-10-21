package ru.riton.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import ru.riton.ru.riton.model.BasicEntity
import ru.riton.ru.riton.model.DescriptionEntity
import ru.riton.ru.riton.model.enums.ScheduleType
import ru.riton.ru.riton.model.enums.WeekDay
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "schedule", schema = "public", catalog = "nogotochki")
class ScheduleEntity(
    @NotNull
    @ManyToOne
    @JoinColumn(name = "master_id", referencedColumnName = "id")
    @JsonBackReference
    var master: MastersEntity? = null,

    @Basic
    @Column(name = "start")
    var start: Timestamp = Timestamp.from(Instant.now()),

    @Basic
    @Column(name = "finish")
    var finish: Timestamp = Timestamp.from(Instant.now()),

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "week_day")
    var weekDay: WeekDay = WeekDay.MON,

    @Basic
    @Column(name = "location")
    var location: String? = null,

    @NotNull
    @ManyToOne
    @JoinColumn(name = "description_id", referencedColumnName = "id")
    @JsonBackReference
    var description: DescriptionEntity? = null,

    @Basic
    @Column(name = "capacity")
    var capacity: Int = 0,

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: ScheduleType = ScheduleType.REGULAR

) : BasicEntity() {


    //todo refactor
    companion object {
        fun copy(oldSchedule: ScheduleEntity, start: Timestamp, finish: Timestamp): ScheduleEntity {
            val schedule = ScheduleEntity()
            schedule.master = oldSchedule.master
            schedule.weekDay = oldSchedule.weekDay
            schedule.start = start
            schedule.finish = finish
            schedule.location = oldSchedule.location
            schedule.description = oldSchedule.description
            schedule.capacity = oldSchedule.capacity
            schedule.type = oldSchedule.type
            return schedule
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScheduleEntity) return false

        if (master != other.master) return false
        if (start != other.start) return false
        if (finish != other.finish) return false
        if (weekDay != other.weekDay) return false
        if (location != other.location) return false
        if (description != other.description) return false
        if (capacity != other.capacity) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = master?.hashCode() ?: 0
        result = 31 * result + start.hashCode()
        result = 31 * result + finish.hashCode()
        result = 31 * result + weekDay.hashCode()
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + capacity
        result = 31 * result + type.hashCode()
        return result
    }
}
