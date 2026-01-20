package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Brand
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BrandRepository : JpaRepository<Brand, UUID> {
    fun findByStatus(status: String, pageable: Pageable): Page<Brand>
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Brand>
    fun findByStatusOrderByCreatedAtDesc(status: String, pageable: Pageable): Page<Brand>
    fun findByCompanyNameContainingIgnoreCase(companyName: String, pageable: Pageable): Page<Brand>
    fun findByLocationContainingIgnoreCase(location: String, pageable: Pageable): Page<Brand>
    fun findByTitleContainingIgnoreCase(title: String, pageable: Pageable): Page<Brand>
}