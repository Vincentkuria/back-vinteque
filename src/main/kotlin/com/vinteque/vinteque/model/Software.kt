package com.vinteque.vinteque.model

import com.vinteque.vinteque.service.ResourceService
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "softwares")
data class Software(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false, length = 20)
    var status: String? = "draft",

    @Column(name = "company_name", nullable = false, length = 150)
    var companyName: String,

    @Column(length = 200)
    var location: String? = null,

    @Column(length = 500)
    var logo: String? = null,

    @Column(name = "bar_color", length = 7)
    var barColor: String? = null,

    @Column(nullable = false, length = 255)
    var title: String,

    @Column(name = "about_client", columnDefinition = "TEXT")
    var aboutClient: String? = null,

    @Column(length = 255)
    var deliverables: String? = null,

    @Column(name = "about_img", length = 500)
    var aboutImg: String? = null,

    @Column(columnDefinition = "TEXT")
    var challenge: String? = null,

    @Column(columnDefinition = "TEXT")
    var solution: String? = null,

    @Column(length = 255)
    var stack: String? = null,

    @Column(name = "showcase_imgs", columnDefinition = "text[]")
    var showcaseImgs: List<String>? = null,

    @Column(name = "showcase_bg_color", length = 7)
    var showcaseBgColor: String? = null,

    @Column(columnDefinition = "text[]")
    var achievements: List<String>? = null,

    @Column(name = "mockup_img", length = 500)
    var mockupImg: String? = null,

    @Column(name = "mockup_bg_img", length = 500)
    var mockupBgImg: String? = null,

    @Column(name = "software_description", columnDefinition = "TEXT")
    var softwareDescription: String? = null,

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
        logo?.let { resourceService.deleteFile(it) }
        aboutImg?.let { resourceService.deleteFile(it) }
        showcaseImgs?.forEach { resourceService.deleteFile(it) }
        mockupImg?.let { resourceService.deleteFile(it) }
        mockupBgImg?.let { resourceService.deleteFile(it) }
    }
}
