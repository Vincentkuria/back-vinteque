package com.vinteque.vinteque.service

import com.vinteque.vinteque.dto.BlogUpdateRequestDto
import com.vinteque.vinteque.model.Blog
import com.vinteque.vinteque.repository.BlogRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class BlogService(
    private val blogRepository: BlogRepository,
    private val resourceService: ResourceService
) {

    fun getAllBlogs(page: Int = 0, size: Int = 10, sortBy: String = "createdAt"): Page<Blog> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy))
        return blogRepository.findAll(pageable)
    }

    fun getPublishedBlogs(page: Int = 0, size: Int = 10): Page<Blog> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return blogRepository.findByStatusOrderByCreatedAtDesc("publish", pageable)
    }

    fun getBlogsByStatus(status: String, page: Int = 0, size: Int = 10): Page<Blog> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return blogRepository.findByStatus(status, pageable)
    }

    fun getBlogById(id: UUID): Blog {
        return blogRepository.findById(id)
            .orElseThrow { RuntimeException("Blog not found with id: $id") }
    }

    @Transactional
    fun createBlog(blog: Blog): Blog {
        return blogRepository.save(blog)
    }

    @Transactional
    fun updateBlog(blogUpdateRequestDto: BlogUpdateRequestDto): Blog {
        val blog=getBlogById(blogUpdateRequestDto.id)

        blogUpdateRequestDto.heroImg?.let { if (!it.isEmpty) blog.heroImg=resourceService.uploadFile(it)}
        blogUpdateRequestDto.title?.let { if(it.isNotBlank()) blog.title=it }
        blogUpdateRequestDto.body?.let { if(it.isNotBlank()) blog.body=it }
        blogUpdateRequestDto.status?.let { if(it.isNotBlank()) blog.status=it }
        return blogRepository.save(blog)
    }

    @Transactional
    fun publishBlog(id: UUID): Blog {
        val blog = getBlogById(id)
        blog.status = "publish"
        return blogRepository.save(blog)
    }

    @Transactional
    fun archiveBlog(id: UUID): Blog {
        val blog = getBlogById(id)
        blog.status = "archived"
        return blogRepository.save(blog)
    }

    @Transactional
    fun deleteBlog(id: UUID) {
        blogRepository.deleteById(id)
    }
}