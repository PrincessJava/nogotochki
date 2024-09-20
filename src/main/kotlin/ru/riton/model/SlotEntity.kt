package ru.riton.model

import jakarta.persistence.*
import ru.riton.ru.riton.model.BasicEntity
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "slot", schema = "public", catalog = "nogotochki")
class SlotEntity : BasicEntity()  {

    @Basic
    @Column(name = "master_id")
    var masterId: Int = 0

    @Basic
    @Column(name = "start")
    var start: Timestamp? = null

    @Basic
    @Column(name = "end")
    var end: Timestamp? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as SlotEntity
        return id == that.id && masterId == that.masterId && start == that.start && end == that.end
    }

    override fun hashCode(): Int {
        return Objects.hash(id, masterId, start, end)
    }
}
