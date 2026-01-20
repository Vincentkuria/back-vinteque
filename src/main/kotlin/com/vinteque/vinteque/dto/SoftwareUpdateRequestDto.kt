package com.vinteque.vinteque.dto

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class SoftwareUpdateRequestDto(
    var id: UUID,

    var status: String?,

    var companyName: String?,

    var location: String?,

    var logo: MultipartFile?,

    var barColor: String?,

    var title: String?,

    var aboutClient: String?,

    var deliverables: String?,

    var aboutImg: MultipartFile?,

    var challenge: String?,

    var solution: String?,

    var stack: String?,

    var showcaseImgs: List<MultipartFile>?,

    var showcaseBgColor: String?,

    var achievements: List<String>?,

    var mockupImg: MultipartFile?,

    var mockupBgImg: MultipartFile?,

    var softwareDescription: String?,
)
