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
    open var version: Long = 1L,

    @CreatedDate
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    open var created: Date = Date(),

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    open var modified: Date = Date(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var id: Int = 0

) : Serializable