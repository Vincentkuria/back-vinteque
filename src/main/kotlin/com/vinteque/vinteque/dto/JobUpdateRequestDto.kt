package com.vinteque.vinteque.dto

import java.time.LocalDate
import java.util.UUID

data class JobUpdateRequestDto (
    var id: UUID,

    var status: String?,

    var location: String?,

    var type: String?,

    var department: String?,

    var body: String?,

    var deadline: LocalDate?,
)