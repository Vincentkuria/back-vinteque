package com.vinteque.vinteque.controller

import com.vinteque.vinteque.dto.BlogRequestDto
import com.vinteque.vinteque.dto.BlogUpdateRequestDto
import com.vinteque.vinteque.dto.toBlog
import com.vinteque.vinteque.model.Blog
import com.vinteque.vinteque.service.BlogService
import com.vinteque.vinteque.service.ResourceService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/blogs")
class BlogController(
    private val blogService: BlogService,
    private val resourceService: ResourceService
) {

    @GetMapping
    fun getAllBlogs(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "createdAt") sortBy: String,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<Page<Blog>> {
        val blogs = if (status != null) {
            blogService.getBlogsByStatus(status, page, size)
        } else {
            blogService.getAllBlogs(page, size, sortBy)
        }
        return ResponseEntity.ok(blogs)
    }

    @GetMapping("/published")
    fun getPublishedBlogs(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Blog>> {
        val blogs = blogService.getPublishedBlogs(page, size)
        return ResponseEntity.ok(blogs)
    }

    @GetMapping("/{id}")
    fun getBlogById(@PathVariable id: UUID): ResponseEntity<Blog> {
        val blog = blogService.getBlogById(id)
        return ResponseEntity.ok(blog)
    }

    @PostMapping
    fun createBlog(@ModelAttribute blogRequestDto: BlogRequestDto): ResponseEntity<Blog> {
        var heroImgUrl:String?=null
        blogRequestDto.heroImg?.let {
            if (!it.isEmpty){
                heroImgUrl=resourceService.uploadFile(it)
            }
        }
        val createdBlog = blogService.createBlog(blogRequestDto.toBlog(heroImgUrl))
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog)
    }

    @PutMapping
    fun updateBlog(
        @ModelAttribute blogUpdateRequestDto: BlogUpdateRequestDto
    ): ResponseEntity<Blog> {

        val updatedBlog = blogService.updateBlog(blogUpdateRequestDto)
        return ResponseEntity.ok(updatedBlog)
    }

    @PatchMapping("/{id}/publish")
    fun publishBlog(@PathVariable id: UUID): ResponseEntity<Blog> {
        val blog = blogService.publishBlog(id)
        return ResponseEntity.ok(blog)
    }

    @PatchMapping("/{id}/archive")
    fun archiveBlog(@PathVariable id: UUID): ResponseEntity<Blog> {
        val blog = blogService.archiveBlog(id)
        return ResponseEntity.ok(blog)
    }

    @DeleteMapping("/{id}")
    fun deleteBlog(@PathVariable id: UUID): ResponseEntity<Void> {
        blogService.deleteBlog(id)
        return ResponseEntity.noContent().build()
    }
}