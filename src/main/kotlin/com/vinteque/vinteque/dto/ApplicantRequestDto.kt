package com.vinteque.vinteque.dto

import com.vinteque.vinteque.model.Applicant
import com.vinteque.vinteque.model.Job
import org.springframework.web.multipart.MultipartFile

data class ApplicantRequestDto (
    var status: String?="new",

    var name: String,

    var email: String,

    var phone: String? = null,

    var resume: MultipartFile,
    )

fun ApplicantRequestDto.toApplicant(resumeUrl: String): Applicant {
    return Applicant(
        status = status,
        name = name,
        email = email,
        phone = phone,
        resume = resumeUrl,
    )
}