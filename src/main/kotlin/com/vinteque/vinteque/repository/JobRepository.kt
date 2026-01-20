package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Job
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface JobRepository : JpaRepository<Job, UUID> {
    fun findByStatus(status: String, pageable: Pageable): Page<Job>
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Job>
    fun findByStatusOrderByCreatedAtDesc(status: String, pageable: Pageable): Page<Job>
    fun findByTypeOrderByCreatedAtDesc(type: String, pageable: Pageable): Page<Job>
    fun findByDepartmentContainingIgnoreCase(department: String, pageable: Pageable): Page<Job>
    fun findByLocationContainingIgnoreCase(location: String, pageable: Pageable): Page<Job>
    fun findByDeadlineAfter(date: LocalDate, pageable: Pageable): Page<Job>
    fun findByDeadlineBefore(date: LocalDate, pageable: Pageable): Page<Job>
}