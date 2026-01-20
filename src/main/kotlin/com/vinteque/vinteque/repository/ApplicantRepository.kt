package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Applicant
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ApplicantRepository : JpaRepository<Applicant, UUID> {
    fun findByJobId(jobId: UUID, pageable: Pageable): Page<Applicant>
    fun findByStatus(status: String, pageable: Pageable): Page<Applicant>
    fun findByStatusOrderByCreatedAtDesc(status: String, pageable: Pageable): Page<Applicant>
    fun findByEmailContainingIgnoreCase(email: String, pageable: Pageable): Page<Applicant>
    fun findByNameContainingIgnoreCase(name: String, pageable: Pageable): Page<Applicant>
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Applicant>
}