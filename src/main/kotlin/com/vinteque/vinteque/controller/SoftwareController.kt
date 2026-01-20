package com.vinteque.vinteque.controller

import com.vinteque.vinteque.dto.SoftwareRequestDto
import com.vinteque.vinteque.dto.SoftwareUpdateRequestDto
import com.vinteque.vinteque.dto.toSoftware
import com.vinteque.vinteque.model.Software
import com.vinteque.vinteque.service.ResourceService
import com.vinteque.vinteque.service.SoftwareService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/software")
class SoftwareController(
    private val softwareService: SoftwareService,
    private val resourceService: ResourceService
) {

    @GetMapping
    fun getAllSoftware(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "createdAt") sortBy: String,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<Page<Software>> {
        val software = if (status != null) {
            softwareService.getSoftwareByStatus(status, page, size)
        } else {
            softwareService.getAllSoftware(page, size, sortBy)
        }
        return ResponseEntity.ok(software)
    }

    @GetMapping("/published")
    fun getPublishedSoftware(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Software>> {
        val software = softwareService.getPublishedSoftware(page, size)
        return ResponseEntity.ok(software)
    }

    @GetMapping("/search")
    fun searchSoftware(
        @RequestParam(required = false) company: String?,
        @RequestParam(required = false) location: String?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) technology: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Software>> {
        val software = when {
            company != null -> softwareService.searchByCompany(company, page, size)
            location != null -> softwareService.searchByLocation(location, page, size)
            title != null -> softwareService.searchByTitle(title, page, size)
            technology != null -> softwareService.searchByTechnology(technology, page, size)
            else -> softwareService.getAllSoftware(page, size)
        }
        return ResponseEntity.ok(software)
    }

    @GetMapping("/{id}")
    fun getSoftwareById(@PathVariable id: UUID): ResponseEntity<Software> {
        val software = softwareService.getSoftwareById(id)
        return ResponseEntity.ok(software)
    }

    @PostMapping
    fun createSoftware(@ModelAttribute softwareRequestDto: SoftwareRequestDto): ResponseEntity<Software> {
        val logoUrl = softwareRequestDto.logo?.let { resourceService.uploadFile(it) }
        val aboutImgUrl= softwareRequestDto.aboutImg?.let { resourceService.uploadFile(it) }
        val showcaseImgUrls= softwareRequestDto.showcaseImgs?.let { it.map { showcaseImg -> resourceService.uploadFile(showcaseImg) } }
        val mockupImgUrl= softwareRequestDto.mockupImg?.let { resourceService.uploadFile(it) }
        val mockupBgImgUrl= softwareRequestDto.mockupBgImg?.let { resourceService.uploadFile(it) }
        val createdSoftware = softwareService.createSoftware(softwareRequestDto.toSoftware(logoUrl,aboutImgUrl,showcaseImgUrls,mockupImgUrl,mockupBgImgUrl))
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSoftware)
    }

    @PutMapping
    fun updateSoftware(
        @ModelAttribute softwareUpdateRequestDto: SoftwareUpdateRequestDto
    ): ResponseEntity<Software> {
        val updatedSoftware = softwareService.updateSoftware(softwareUpdateRequestDto)
        return ResponseEntity.ok(updatedSoftware)
    }

    @PatchMapping("/{id}/publish")
    fun publishSoftware(@PathVariable id: UUID): ResponseEntity<Software> {
        val software = softwareService.publishSoftware(id)
        return ResponseEntity.ok(software)
    }

    @PatchMapping("/{id}/archive")
    fun archiveSoftware(@PathVariable id: UUID): ResponseEntity<Software> {
        val software = softwareService.archiveSoftware(id)
        return ResponseEntity.ok(software)
    }

    @DeleteMapping("/{id}")
    fun deleteSoftware(@PathVariable id: UUID): ResponseEntity<Void> {
        softwareService.deleteSoftware(id)
        return ResponseEntity.noContent().build()
    }
}