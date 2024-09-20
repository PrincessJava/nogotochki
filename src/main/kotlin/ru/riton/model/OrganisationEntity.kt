package ru.riton.model

import jakarta.persistence.*
import ru.riton.ru.riton.model.BasicEntity
import java.util.*

@Entity
@Table(name = "organisation", schema = "public", catalog = "nogotochki")
class OrganisationEntity : BasicEntity()  {

    @Basic
    @Column(name = "name")
    var name: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as OrganisationEntity
        return id == that.id && name == that.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }
}
