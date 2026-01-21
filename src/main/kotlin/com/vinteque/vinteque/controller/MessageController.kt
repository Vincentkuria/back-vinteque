package com.vinteque.vinteque.controller

import com.vinteque.vinteque.model.Message
import com.vinteque.vinteque.service.MessageService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/messages")
class MessageController(
    private val messageService: MessageService
) {

    @GetMapping
    fun getAllMessages(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "createdAt") sortBy: String,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<Page<Message>> {
        val messages = if (status != null) {
            messageService.getMessagesByStatus(status, page, size)
        } else {
            messageService.getAllMessages(page, size, sortBy)
        }
        return ResponseEntity.ok(messages)
    }

    @GetMapping("/unread")
    fun getUnreadMessages(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Message>> {
        val messages = messageService.getUnreadMessages(page, size)
        return ResponseEntity.ok(messages)
    }

    @GetMapping("/search")
    fun searchMessages(
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Message>> {
        val messages = when {
            email != null -> messageService.searchMessagesByEmail(email, page, size)
            name != null -> messageService.searchMessagesByName(name, page, size)
            else -> messageService.getAllMessages(page, size)
        }
        return ResponseEntity.ok(messages)
    }

    @GetMapping("/{id}")
    fun getMessageById(@PathVariable id: Long): ResponseEntity<Message> {
        val message = messageService.getMessageById(id)
        return ResponseEntity.ok(message)
    }

    @PostMapping
    fun createMessage(@RequestBody message: Message): ResponseEntity<Message> {
        val createdMessage = messageService.createMessage(message)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage)
    }

    @PatchMapping("/{id}/status")
    fun updateMessageStatus(
        @PathVariable id: Long,
        @RequestBody status: String
    ): ResponseEntity<Message> {
        val message = messageService.updateMessageStatus(id, status)
        return ResponseEntity.ok(message)
    }

    @PatchMapping("/{id}/read")
    fun markAsRead(@PathVariable id: Long): ResponseEntity<Message> {
        val message = messageService.markAsRead(id)
        return ResponseEntity.ok(message)
    }

    @PatchMapping("/{id}/replied")
    fun markAsReplied(@PathVariable id: Long): ResponseEntity<Message> {
        val message = messageService.markAsReplied(id)
        return ResponseEntity.ok(message)
    }

    @PatchMapping("/{id}/archive")
    fun archiveMessage(@PathVariable id: Long): ResponseEntity<Message> {
        val message = messageService.archiveMessage(id)
        return ResponseEntity.ok(message)
    }

    @DeleteMapping("/{id}")
    fun deleteMessage(@PathVariable id: Long): ResponseEntity<Void> {
        messageService.deleteMessage(id)
        return ResponseEntity.noContent().build()
    }

    // Bulk operations
    @PatchMapping("/bulk/read")
    fun markMultipleAsRead(@RequestBody request: BulkOperationRequest): ResponseEntity<List<Message>> {
        val messages = messageService.markMultipleAsRead(request.ids)
        return ResponseEntity.ok(messages)
    }

    @PatchMapping("/bulk/archive")
    fun archiveMultiple(@RequestBody request: BulkOperationRequest): ResponseEntity<List<Message>> {
        val messages = messageService.archiveMultiple(request.ids)
        return ResponseEntity.ok(messages)
    }
}

data class BulkOperationRequest(
    val ids: List<Long>
)