package com.vinteque.vinteque.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.vinteque.vinteque.service.ResourceService
import jakarta.persistence.*
import org.hibernate.annotations.Generated
import org.hibernate.generator.EventType
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "applicants")
class Applicant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null,

    @Column(nullable = false, length = 20)
    var status: String? = "new",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @JsonBackReference
    var job: Job?=null,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(nullable = false, length = 255)
    var email: String,

    @Column(length = 20)
    var phone: String? = null,

    @Column(nullable = false, length = 500)
    var resume: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = Instant.now(),

    @Generated(event = [EventType.INSERT, EventType.UPDATE])
    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    var updatedAt: Instant?=null
) {
    @PreRemove
    fun preRemove(){
        val resourceService= ResourceService()
        resourceService.deleteFile(resume)
    }
}
