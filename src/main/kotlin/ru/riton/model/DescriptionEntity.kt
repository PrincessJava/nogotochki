package ru.riton.ru.riton.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import ru.riton.model.ScheduleEntity
import ru.riton.model.UserEntity

@Entity
@Table(name = "description", schema = "public", catalog = "nogotochki")
class DescriptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Int = 0,

    @Basic
    @Column(name = "value")
    var value: String? = null
) {
}