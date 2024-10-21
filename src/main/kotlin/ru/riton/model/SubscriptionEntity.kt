package ru.riton.model

import jakarta.persistence.*
import ru.riton.ru.riton.model.BasicEntity
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "subscription", schema = "public", catalog = "nogotochki")
class SubscriptionEntity : BasicEntity()  {

    @Basic
    @Column(name = "name")
    var name: String? = null

    @Basic
    @Column(name = "validity")
    var validity: Timestamp? = null

    @Basic
    @Column(name = "operations_count")
    var operationsCount: Int? = null


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as SubscriptionEntity
        return id == that.id && name == that.name && validity == that.validity && operationsCount == that.operationsCount
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, validity, operationsCount)
    }

    override fun toString(): String {
        return "SubscriptionEntity(name=$name, validity=$validity, operationsCount=$operationsCount)"
    }
}
