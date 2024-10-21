package ru.riton.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import ru.riton.ru.riton.model.BasicEntity
import java.util.*

@Entity
@Table(name = "appointment", schema = "public", catalog = "nogotochki")
class AppointmentEntity : BasicEntity() {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "slot_id", referencedColumnName = "id")
    @JsonBackReference
    var slot: ScheduleEntity? = null

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    var user: UserEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppointmentEntity

        if (slot != other.slot) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = slot?.hashCode() ?: 0
        result = 31 * result + (user?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AppointmentEntity(slot=$slot, user=$user)"
    }


}
