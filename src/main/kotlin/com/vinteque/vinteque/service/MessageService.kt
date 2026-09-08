package com.vinteque.vinteque.service

import com.vinteque.vinteque.model.Message
import com.vinteque.vinteque.repository.MessageRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val sseService: SseService
) {

    fun getAllMessages(page: Int = 0, size: Int = 10, sortBy: String = "createdAt"): Page<Message> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy))
        return messageRepository.findAll(pageable)
    }

    fun getMessagesByStatus(status: String, page: Int = 0, size: Int = 10): Page<Message> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return messageRepository.findByStatusOrderByCreatedAtDesc(status, pageable)
    }

    fun getUnreadMessages(page: Int = 0, size: Int = 10): Page<Message> {
        return getMessagesByStatus("unread", page, size)
    }

    fun getUnreadCount(): Long {
        return messageRepository.countByStatus("unread")
    }

    fun getReadMessages(page: Int = 0, size: Int = 10): Page<Message> {
        return getMessagesByStatus("read", page, size)
    }

    fun searchMessagesByEmail(email: String, page: Int = 0, size: Int = 10): Page<Message> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return messageRepository.findByEmailContainingIgnoreCase(email, pageable)
    }

    fun searchMessagesByName(name: String, page: Int = 0, size: Int = 10): Page<Message> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return messageRepository.findByNameContainingIgnoreCase(name, pageable)
    }

    fun getMessageById(id: Long): Message {
        return messageRepository.findById(id)
            .orElseThrow { RuntimeException("Message not found with id: $id") }
    }

    @Transactional
    fun createMessage(message: Message): Message {
        val savedMessage = messageRepository.save(message)
        sseService.send(savedMessage)
        return savedMessage
    }

    @Transactional
    fun updateMessageStatus(id: Long, status: String): Message {
        val message = getMessageById(id)
        message.status = status
        return messageRepository.save(message)
    }

    @Transactional
    fun markAsRead(id: Long): Message {
        return updateMessageStatus(id, "read")
    }

    @Transactional
    fun markAsReplied(id: Long): Message {
        return updateMessageStatus(id, "replied")
    }

    @Transactional
    fun archiveMessage(id: Long): Message {
        return updateMessageStatus(id, "archived")
    }

    @Transactional
    fun deleteMessage(id: Long) {
        messageRepository.deleteById(id)
    }

    // Bulk operations
    @Transactional
    fun markMultipleAsRead(ids: List<Long>): List<Message> {
        return ids.map { markAsRead(it) }
    }

    @Transactional
    fun archiveMultiple(ids: List<Long>): List<Message> {
        return ids.map { archiveMessage(it) }
    }
}