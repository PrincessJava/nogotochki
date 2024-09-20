package ru.riton.model

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import ru.riton.ru.riton.model.BasicEntity
import java.util.*

@Entity
@Table(name = "admin", schema = "public", catalog = "nogotochki")
class AdminEntity : BasicEntity() {
    @Basic
    @Column(name = "name")
    var name: String? = null

    @Basic
    @Column(name = "tg_id")
    var tgId: String? = null

    @Basic
    @Column(name = "org_id")
    var orgId: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AdminEntity
        return id == that.id && orgId == that.orgId && name == that.name && tgId == that.tgId
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, tgId, orgId)
    }
}
