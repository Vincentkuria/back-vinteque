package com.vinteque.vinteque.dto

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class BrandUpdateRequestDto (
    var id: UUID,

    var status: String?,

    var displayImg: MultipartFile?,

    var companyName: String?,

    var title: String?,

    var bgColor: String?,

    var location: String?,

    var heroImg: MultipartFile?,

    var aboutClient: String?,

    var goal: String?,

    var showcaseImgs: List<MultipartFile>?,

    var showcaseBgColor: String?,
)