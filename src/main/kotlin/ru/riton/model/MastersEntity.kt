package ru.riton.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import ru.riton.ru.riton.model.BasicEntity
import java.util.*

@Entity
@Table(name = "masters", schema = "public", catalog = "nogotochki")
class MastersEntity(
    @Basic
    @Column(name = "name")
    val name: String
) : BasicEntity()  {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    var user: UserEntity? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as MastersEntity
        return id == that.id && name == that.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }
}
