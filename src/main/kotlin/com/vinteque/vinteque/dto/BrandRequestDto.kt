package com.vinteque.vinteque.dto

import com.vinteque.vinteque.model.Brand
import org.springframework.web.multipart.MultipartFile

data class BrandRequestDto (
    var status: String?="draft",

    var displayImg: MultipartFile? = null,

    var companyName: String,

    var title: String,

    var bgColor: String? = null,

    var location: String? = null,

    var heroImg: MultipartFile? = null,

    var aboutClient: String? = null,

    var goal: String? = null,

    var showcaseImgs: List<MultipartFile>? = null,

    var showcaseBgColor: String? = null,
)

fun BrandRequestDto.toBrand(displayImgUrl: String?,heroImgUrl: String?, showcaseImgUrls: List<String>?): Brand {
    return Brand(
        status = status,
        displayImg = displayImgUrl,
        companyName = companyName,
        title = title,
        bgColor = bgColor,
        location = location,
        heroImg = heroImgUrl,
        aboutClient = aboutClient,
        goal = goal,
        showcaseImgs = showcaseImgUrls,
        showcaseBgColor = showcaseBgColor,
    )
}