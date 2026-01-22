package com.vinteque.vinteque.service

import com.vinteque.vinteque.dto.TestimonialUpdateRequestDto
import com.vinteque.vinteque.model.Testimonial
import com.vinteque.vinteque.repository.TestimonialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TestimonialService(
    private val testimonialRepository: TestimonialRepository,
    private val resourceService: ResourceService
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
    fun updateTestimonial(testimonialUpdateRequestDto: TestimonialUpdateRequestDto): Testimonial {
        val testimonial = getTestimonialById(testimonialUpdateRequestDto.id)
        testimonialUpdateRequestDto.let { update ->
            update.name?.let { if (it.isNotBlank()) testimonial.name = it }
            update.role?.let { if (it.isNotBlank()) testimonial.role = it }
            update.company?.let { if (it.isNotBlank()) testimonial.company = it }
            update.testimony?.let { if (it.isNotBlank()) testimonial.testimony = it }
            update.profilePic?.let { if (!it.isEmpty) testimonial.profilePic = resourceService.uploadFile(it) }
        }
        return testimonialRepository.save(testimonial)
    }

    @Transactional
    fun deleteTestimonial(id: Long) {
        testimonialRepository.deleteById(id)
    }
}