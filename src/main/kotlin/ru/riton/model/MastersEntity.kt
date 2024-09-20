package ru.riton.model

import jakarta.persistence.*
import ru.riton.ru.riton.model.BasicEntity
import java.util.*

@Entity
@Table(name = "masters", schema = "public", catalog = "nogotochki")
class MastersEntity : BasicEntity()  {

    @Basic
    @Column(name = "name")
    var name: String? = null

    @Basic
    @Column(name = "org_id")
    var orgId: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as MastersEntity
        return id == that.id && orgId == that.orgId && name == that.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, orgId)
    }
}
