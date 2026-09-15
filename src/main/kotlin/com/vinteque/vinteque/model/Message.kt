package com.vinteque.vinteque.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime



@Entity
@Table(name = "messages")
@SQLDelete(sql = "UPDATE messages SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

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
    var deletedAt: LocalDateTime? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
