package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Testimonial
import org.springframework.data.jpa.repository.JpaRepository

interface TestimonialRepository: JpaRepository<Testimonial, Long> {
}