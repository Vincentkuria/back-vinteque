package com.vinteque.vinteque.model

import jakarta.persistence.*
import org.hibernate.annotations.Generated
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.generator.EventType
import java.time.Instant
import java.time.LocalDateTime



@Entity
@Table(name = "messages")
@SQLDelete(sql = "UPDATE messages SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(nullable = false, length = 255)
    var email: String,

    @Column(length = 20)
    var phone: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var message: String,

    @Column(nullable = false, length = 20)
    var status: String? = "unread",

    @Column("deleted_at", nullable = true)
    var deletedAt: Instant? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = Instant.now(),

    @Generated(event = [EventType.INSERT, EventType.UPDATE])
    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    var updatedAt: Instant?= null
)
