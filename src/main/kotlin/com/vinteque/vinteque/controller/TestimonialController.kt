package com.vinteque.vinteque.controller

import com.vinteque.vinteque.model.Testimonial
import com.vinteque.vinteque.service.TestimonialService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/testimonials")
class TestimonialController(
    private val testimonialService: TestimonialService
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
    fun createTestimonial(@RequestBody testimonial: Testimonial): ResponseEntity<Testimonial> {
        val createdTestimonial = testimonialService.createTestimonial(testimonial)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial)
    }
    
    @PutMapping("/{id}")
    fun updateTestimonial(
        @PathVariable id: Long,
        @RequestBody testimonial: Testimonial
    ): ResponseEntity<Testimonial> {
        val updatedTestimonial = testimonialService.updateTestimonial(id, testimonial)
        return ResponseEntity.ok(updatedTestimonial)
    }
    
    @DeleteMapping("/{id}")
    fun deleteTestimonial(@PathVariable id: Long): ResponseEntity<Void> {
        testimonialService.deleteTestimonial(id)
        return ResponseEntity.noContent().build()
    }
}