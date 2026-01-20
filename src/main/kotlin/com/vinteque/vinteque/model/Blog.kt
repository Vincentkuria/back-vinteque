package com.vinteque.vinteque.model

import com.vinteque.vinteque.service.ResourceService
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "blogs")
data class Blog(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false, length = 20)
    var status: String? = "draft",

    @Column(nullable = false, length = 255)
    var title: String,

    @Column(name = "hero_img", length = 500)
    var heroImg: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var body: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }

    @PreRemove
    fun preRemove() {
        val resourceService= ResourceService()
        heroImg?.let { resourceService.deleteFile(it) }
    }
}
