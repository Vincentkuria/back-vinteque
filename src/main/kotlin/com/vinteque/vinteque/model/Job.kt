package com.vinteque.vinteque.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.Generated
import org.hibernate.generator.EventType
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "jobs")
class Job(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

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
    var createdAt: Instant? = Instant.now(),

    @Generated(event = [EventType.INSERT, EventType.UPDATE])
    @Column(name = "updated_at", nullable = false , updatable = false, insertable = false)
    var updatedAt: Instant?=null,

    @OneToMany(mappedBy = "job", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var applicants: MutableList<Applicant>? = mutableListOf()
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }
}