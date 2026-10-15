package com.vinteque.vinteque.model

import com.vinteque.vinteque.service.ResourceService
import jakarta.persistence.*
import org.hibernate.annotations.Generated
import org.hibernate.generator.EventType
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "blogs")
class Blog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null,

    @Column(nullable = false, length = 20)
    var status: String? = "draft",

    @Column(nullable = false, length = 255)
    var title: String,

    @Column(name = "hero_img", length = 500)
    var heroImg: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var body: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = Instant.now(),

    @Generated(event=[EventType.INSERT, EventType.UPDATE])
    @Column(name = "updated_at", nullable = false, updatable = false, insertable = false)
    var updatedAt: Instant?
) {
    @PreRemove
    fun preRemove() {
        val resourceService= ResourceService()
        heroImg?.let { resourceService.deleteFile(it) }
    }
}
