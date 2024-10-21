package ru.riton.model

import jakarta.persistence.*
import ru.riton.ru.riton.model.BasicEntity
import java.util.*

@Entity
@Table(name = "payment", schema = "public", catalog = "nogotochki")
class PaymentEntity : BasicEntity()  {
    @Basic
    @Column(name = "user_id")
    var userId: Int = 0

    @Basic
    @Column(name = "subscription_id")
    var subscriptionId: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as PaymentEntity
        return id == that.id && userId == that.userId && subscriptionId == that.subscriptionId
    }

    override fun hashCode(): Int {
        return Objects.hash(id, userId, subscriptionId)
    }

    override fun toString(): String {
        return "PaymentEntity(userId=$userId, subscriptionId=$subscriptionId)"
    }
}
