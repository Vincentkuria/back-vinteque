package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Lead
import com.vinteque.vinteque.model.Stage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LeadRepository: JpaRepository<Lead, UUID> {
    fun findByCurrentStage(currentStage: String,pageable: Pageable): Page<Lead>
}