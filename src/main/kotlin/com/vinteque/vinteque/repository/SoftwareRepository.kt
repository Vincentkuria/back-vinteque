package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Software
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SoftwareRepository : JpaRepository<Software, UUID> {
    fun findByStatus(status: String, pageable: Pageable): Page<Software>
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Software>
    fun findByStatusOrderByCreatedAtDesc(status: String, pageable: Pageable): Page<Software>
    fun findByCompanyNameContainingIgnoreCase(companyName: String, pageable: Pageable): Page<Software>
    fun findByLocationContainingIgnoreCase(location: String, pageable: Pageable): Page<Software>
    fun findByTitleContainingIgnoreCase(title: String, pageable: Pageable): Page<Software>

    @Query("SELECT s FROM Software s WHERE s.stack LIKE %:technology%")
    fun findByStackContaining(technology: String, pageable: Pageable): Page<Software>
}