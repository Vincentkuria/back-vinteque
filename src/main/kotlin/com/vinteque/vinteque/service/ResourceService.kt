package com.vinteque.vinteque.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@Service
class ResourceService {
    fun uploadFile(file : MultipartFile):String {
        val uniqueName = UUID.randomUUID().toString()+"-"+file.originalFilename
        val uploadDir= Paths.get("uploads/")
        if(!Files.exists(uploadDir)) Files.createDirectories(uploadDir)
        val relativePath="uploads/$uniqueName"
        file.transferTo(Paths.get(relativePath))
        return relativePath
    }

    fun deleteFile(relativePath: String): Boolean {
        try {
            val path = Paths.get(relativePath)
            return Files.deleteIfExists(path)
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }
}