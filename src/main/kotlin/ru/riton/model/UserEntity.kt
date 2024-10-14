package ru.riton.model

import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder
import ru.riton.ru.riton.model.BasicEntity
import java.util.*


@Entity
@Table(name = "user", schema = "public", catalog = "nogotochki")
class UserEntity(
    @Basic
    @Column(name = "name", nullable = false)
    var name: String = "",

    @Basic
    @Column(name = "tg_id")
    var tgId: String? = null,

    @Basic
    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String = ""
) : BasicEntity() {

    @Column(name = "password")
    private var password: String? = null

    @OneToMany(mappedBy = "user")
    var appointments: MutableList<AppointmentEntity> = mutableListOf()

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

    fun getPassword(): String? {
        return this.password
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as UserEntity
        return id == that.id && name == that.name && tgId == that.tgId && phoneNumber == that.phoneNumber
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, tgId, phoneNumber)
    }
}
