package ru.riton.ru.riton.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BasicEntity(

    @Version
    var version: Long = 1L,

    @CreatedDate
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    var created: Date = Date(),

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    var modified: Date = Date(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

) : Serializable