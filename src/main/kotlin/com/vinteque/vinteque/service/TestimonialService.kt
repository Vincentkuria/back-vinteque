package com.vinteque.vinteque.service

import com.vinteque.vinteque.model.Testimonial
import com.vinteque.vinteque.repository.TestimonialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TestimonialService(
    private val testimonialRepository: TestimonialRepository
) {

    fun getAllTestimonials(): List<Testimonial> {
        return testimonialRepository.findAll()
    }

    fun getTestimonialById(id: Long): Testimonial {
        return testimonialRepository.findById(id)
            .orElseThrow { RuntimeException("Testimonial not found with id: $id") }
    }

    @Transactional
    fun createTestimonial(testimonial: Testimonial): Testimonial {
        return testimonialRepository.save(testimonial)
    }

    @Transactional
    fun updateTestimonial(id: Long, updatedTestimonial: Testimonial): Testimonial {
        val testimonial = getTestimonialById(id)
        testimonial.name = updatedTestimonial.name
        testimonial.role = updatedTestimonial.role
        testimonial.company = updatedTestimonial.company
        testimonial.testimony = updatedTestimonial.testimony
        testimonial.profilePic = updatedTestimonial.profilePic
        return testimonialRepository.save(testimonial)
    }

    @Transactional
    fun deleteTestimonial(id: Long) {
        testimonialRepository.deleteById(id)
    }
}