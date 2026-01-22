package com.vinteque.vinteque.dto

import org.springframework.web.multipart.MultipartFile

data class TestimonialUpdateRequestDto(
    val id: Long,

    var name: String?,

    var role: String?,

    var company: String?,

    var testimony: String?,

    var profilePic: MultipartFile?
)
