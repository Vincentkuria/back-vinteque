package com.vinteque.vinteque.dto

import com.vinteque.vinteque.model.Blog
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

data class BlogRequestDto (
    var status: String?,

    var title: String,

    var heroImg: MultipartFile? = null,

    var body: String,
)

fun BlogRequestDto.toBlog(heroImgUrl: String?=null): Blog {
    return Blog(
        status = status,
        title = title,
        heroImg = heroImgUrl,
        body = body
    )
}