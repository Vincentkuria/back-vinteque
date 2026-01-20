package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Long> {
    fun findByStatus(status: String, pageable: Pageable): Page<Message>
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Message>
    fun findByStatusOrderByCreatedAtDesc(status: String, pageable: Pageable): Page<Message>
    fun findByEmailContainingIgnoreCase(email: String, pageable: Pageable): Page<Message>
    fun findByNameContainingIgnoreCase(name: String, pageable: Pageable): Page<Message>
}