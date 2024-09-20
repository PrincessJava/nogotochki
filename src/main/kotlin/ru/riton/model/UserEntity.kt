package ru.riton.model

import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder
import ru.riton.ru.riton.model.BasicEntity
import java.util.*


@Entity
@Table(name = "user", schema = "public", catalog = "nogotochki")
class UserEntity : BasicEntity()  {

    @Basic
    @Column(name = "name")
    var name: String? = null

    @Basic
    @Column(name = "tg_id")
    var tgId: String? = null

    @Basic
    @Column(name = "phone_number")
    var phoneNumber: String? = null

    @Basic
    @Column(name = "org_id")
    var orgId: Int = 0

    @Column(name = "password")
    var password: String? = null

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    private val roles: Collection<RoleEntity>? = null

    fun setPassword(password: String, passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(password)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as UserEntity
        return id == that.id && orgId == that.orgId && name == that.name && tgId == that.tgId && phoneNumber == that.phoneNumber
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, tgId, phoneNumber, orgId)
    }
}
