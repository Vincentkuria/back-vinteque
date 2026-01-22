package com.vinteque.vinteque.dto

import com.vinteque.vinteque.model.Testimonial
import org.springframework.web.multipart.MultipartFile

data class TestimonialRequestDto(

    var name: String,

    var role: String,

    var company: String,

    var testimony: String,

    var profilePic: MultipartFile
)

fun TestimonialRequestDto.toTestimonial(profilePicUrl: String): Testimonial {
    return Testimonial(
        name = name,
        role = role,
        company = company,
        testimony = testimony,
        profilePic = profilePicUrl,
    )
}
