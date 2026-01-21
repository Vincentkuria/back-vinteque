package com.vinteque.vinteque.dto

import com.vinteque.vinteque.model.Software
import org.springframework.web.multipart.MultipartFile

data class SoftwareRequestDto(
    var status: String?="draft",

    var companyName: String,

    var location: String?,

    var logo: MultipartFile?,

    var barColor: String?,

    var title: String,

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

fun SoftwareRequestDto.toSoftware(logoUrl:String?,aboutImgUrl: String?, showcaseImgUrls:List<String>?, mockupImgUrl: String?, mockupBgImgUrl: String?): Software {
    return Software(
        status = status,
        companyName = companyName,
        location = location,
        logo =logoUrl ,
        barColor = barColor,
        title = title,
        aboutClient = aboutClient,
        deliverables = deliverables,
        aboutImg = aboutImgUrl,
        challenge = challenge,
        solution = solution,
        stack = stack,
        showcaseImgs = showcaseImgUrls,
        showcaseBgColor = showcaseBgColor,
        achievements = achievements,
        mockupImg = mockupImgUrl,
        mockupBgImg = mockupBgImgUrl,
        softwareDescription = softwareDescription,
    )
}
