package com.vinteque.vinteque.model

import com.vinteque.vinteque.service.ResourceService
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "brands")
data class Brand(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false, length = 20)
    var status: String? = "draft",

    @Column(name = "display_img", length = 500)
    var displayImg: String? = null,

    @Column(name = "company_name", nullable = false, length = 150)
    var companyName: String,

    @Column(nullable = false, length = 255)
    var title: String,

    @Column(name = "bg_color", length = 7)
    var bgColor: String? = null,

    @Column(length = 200)
    var location: String? = null,

    @Column(name = "hero_img", length = 500)
    var heroImg: String? = null,

    @Column(name = "about_client", columnDefinition = "TEXT")
    var aboutClient: String? = null,

    @Column(columnDefinition = "TEXT")
    var goal: String? = null,

    @Column(name = "showcase_imgs", columnDefinition = "text[]")
    var showcaseImgs: List<String>? = null,

    @Column(name = "showcase_bg_color", length = 7)
    var showcaseBgColor: String? = null,

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
        val resourceService = ResourceService()
        heroImg?.let { resourceService.deleteFile(it) }
        showcaseImgs?.let { showcaseImg ->
            showcaseImg.forEach { resourceService.deleteFile(it) }
        }
    }
}