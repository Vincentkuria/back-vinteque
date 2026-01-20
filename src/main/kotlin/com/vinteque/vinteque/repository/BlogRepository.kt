package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Blog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BlogRepository : JpaRepository<Blog, UUID> {
    fun findByStatus(status: String, pageable: Pageable): Page<Blog>
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Blog>
    fun findByStatusOrderByCreatedAtDesc(status: String, pageable: Pageable): Page<Blog>
}