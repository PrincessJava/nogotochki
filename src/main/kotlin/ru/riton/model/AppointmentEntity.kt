package ru.riton.model

import jakarta.persistence.*
import ru.riton.ru.riton.model.BasicEntity
import java.util.*

@Entity
@Table(name = "appointment", schema = "public", catalog = "nogotochki")
class AppointmentEntity : BasicEntity()  {

    @Basic
    @Column(name = "slot_id")
    var slotId: Int = 0

    @Basic
    @Column(name = "user_id")
    var userId: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AppointmentEntity
        return id == that.id && slotId == that.slotId && userId == that.userId
    }

    override fun hashCode(): Int {
        return Objects.hash(id, slotId, userId)
    }
}
