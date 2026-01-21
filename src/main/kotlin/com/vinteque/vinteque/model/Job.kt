package com.vinteque.vinteque.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "jobs")
data class Job(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false, length = 20)
    var status: String? = "draft",

    @Column(length = 200)
    var location: String? = null,

    @Column(nullable = false, length = 20)
    var type: String,

    @Column(length = 100)
    var department: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var body: String,

    var deadline: LocalDate? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @OneToMany(mappedBy = "job", cascade = [CascadeType.ALL], orphanRemoval = true)
    var applicants: MutableList<Applicant>? = mutableListOf()
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}