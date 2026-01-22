package com.vinteque.vinteque.controller

import com.vinteque.vinteque.dto.TestimonialRequestDto
import com.vinteque.vinteque.dto.TestimonialUpdateRequestDto
import com.vinteque.vinteque.dto.toTestimonial
import com.vinteque.vinteque.model.Testimonial
import com.vinteque.vinteque.service.ResourceService
import com.vinteque.vinteque.service.TestimonialService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/testimonials")
class TestimonialController(
    private val testimonialService: TestimonialService,
    private val resourceService: ResourceService
) {
    
    @GetMapping
    fun getAllTestimonials(): ResponseEntity<List<Testimonial>> {
        val testimonials = testimonialService.getAllTestimonials()
        return ResponseEntity.ok(testimonials)
    }
    
    @GetMapping("/{id}")
    fun getTestimonialById(@PathVariable id: Long): ResponseEntity<Testimonial> {
        val testimonial = testimonialService.getTestimonialById(id)
        return ResponseEntity.ok(testimonial)
    }
    
    @PostMapping
    fun createTestimonial(@ModelAttribute testimonialRequestDto: TestimonialRequestDto): ResponseEntity<Testimonial> {
        val profilePicUrl= resourceService.uploadFile(testimonialRequestDto.profilePic)
        val createdTestimonial = testimonialService.createTestimonial(testimonialRequestDto.toTestimonial(profilePicUrl))
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial)
    }
    
    @PutMapping("/{id}")
    fun updateTestimonial(
        @ModelAttribute testimonialUpdateRequestDto: TestimonialUpdateRequestDto
    ): ResponseEntity<Testimonial> {
        val updatedTestimonial = testimonialService.updateTestimonial(testimonialUpdateRequestDto)
        return ResponseEntity.ok(updatedTestimonial)
    }
    
    @DeleteMapping("/{id}")
    fun deleteTestimonial(@PathVariable id: Long): ResponseEntity<Void> {
        testimonialService.deleteTestimonial(id)
        return ResponseEntity.noContent().build()
    }
}