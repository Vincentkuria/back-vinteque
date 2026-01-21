package com.vinteque.vinteque.dto

import com.vinteque.vinteque.model.Job
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.util.UUID

data class JobRequestDto(

    var status: String? = "draft",

    var location: String? = null,

    var type: String,

    var department: String? = null,

    var body: String,

    var deadline: LocalDate? = null,

    )

fun JobRequestDto.toJob(): Job {
    return Job(
        status = status,
        location = location,
        type = type,
        department = department,
        body = body,
        deadline = deadline
    )
}
