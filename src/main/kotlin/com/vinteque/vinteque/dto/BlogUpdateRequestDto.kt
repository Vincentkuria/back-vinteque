package com.vinteque.vinteque.dto

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class BlogUpdateRequestDto (
    var id: UUID,

    var status: String?,

    var title: String?,

    var heroImg: MultipartFile? = null,

    var body: String?,
)