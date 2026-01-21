package com.vinteque.vinteque.service

import com.vinteque.vinteque.dto.SoftwareUpdateRequestDto
import com.vinteque.vinteque.model.Software
import com.vinteque.vinteque.repository.SoftwareRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import kotlin.text.isNotBlank

@Service
class SoftwareService(
    private val softwareRepository: SoftwareRepository,
    private val resourceService: ResourceService
) {

    fun getAllSoftware(page: Int = 0, size: Int = 10, sortBy: String = "createdAt"): Page<Software> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy))
        return softwareRepository.findAll(pageable)
    }

    fun getPublishedSoftware(page: Int = 0, size: Int = 10): Page<Software> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return softwareRepository.findByStatusOrderByCreatedAtDesc("published", pageable)
    }

    fun getSoftwareByStatus(status: String, page: Int = 0, size: Int = 10): Page<Software> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return softwareRepository.findByStatus(status, pageable)
    }

    fun searchByCompany(companyName: String, page: Int = 0, size: Int = 10): Page<Software> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return softwareRepository.findByCompanyNameContainingIgnoreCase(companyName, pageable)
    }

    fun searchByLocation(location: String, page: Int = 0, size: Int = 10): Page<Software> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return softwareRepository.findByLocationContainingIgnoreCase(location, pageable)
    }

    fun searchByTitle(title: String, page: Int = 0, size: Int = 10): Page<Software> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return softwareRepository.findByTitleContainingIgnoreCase(title, pageable)
    }

    fun searchByTechnology(technology: String, page: Int = 0, size: Int = 10): Page<Software> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return softwareRepository.findByStackContaining(technology, pageable)
    }

    fun getSoftwareById(id: UUID): Software {
        return softwareRepository.findById(id)
            .orElseThrow { RuntimeException("Software not found with id: $id") }
    }

    @Transactional
    fun createSoftware(software: Software): Software {
        return softwareRepository.save(software)
    }

    @Transactional
    fun updateSoftware(softwareUpdateRequestDto: SoftwareUpdateRequestDto): Software {
        val software = getSoftwareById(softwareUpdateRequestDto.id)
        softwareUpdateRequestDto.let { dto ->
            dto.status?.let { if(it.isNotBlank()) software.status = it }
            dto.companyName?.let { if(it.isNotBlank()) software.companyName = it }
            dto.location?.let { if(it.isNotBlank()) software.location = it }
            dto.logo?.let { if(!it.isEmpty) software.logo= resourceService.uploadFile(it)  }
            dto.barColor?.let { if(it.isNotBlank()) software.barColor = it }
            dto.title?.let { if(it.isNotBlank()) software.title = it }
            dto.aboutClient?.let { if(it.isNotBlank()) software.aboutClient = it }
            dto.deliverables?.let { if(it.isNotBlank()) software.deliverables = it }
            dto.aboutImg?.let { if(!it.isEmpty) software.aboutImg = resourceService.uploadFile(it) }
            dto.challenge?.let { if(it.isNotBlank()) software.challenge = it }
            dto.solution?.let { if(it.isNotBlank()) software.solution = it }
            dto.stack?.let { if(it.isNotBlank()) software.stack = it }
            dto.showcaseImgs?.let { if(!it.isEmpty()) software.showcaseImgs = it.map { showcaseImg -> resourceService.uploadFile(showcaseImg) } }
            dto.showcaseBgColor?.let { if(it.isNotBlank()) software.showcaseBgColor = it }
            dto.achievements?.let { if(!it.isEmpty()) software.achievements = it }
            dto.mockupImg?.let { if(!it.isEmpty) software.mockupImg = resourceService.uploadFile(it) }
            dto.mockupBgImg?.let { if(!it.isEmpty) software.mockupBgImg = resourceService.uploadFile(it) }
            dto.softwareDescription?.let { if(it.isNotBlank()) software.softwareDescription = it }
        }
        return softwareRepository.save(software)
    }

    @Transactional
    fun publishSoftware(id: UUID): Software {
        val software = getSoftwareById(id)
        software.status = "published"
        return softwareRepository.save(software)
    }

    @Transactional
    fun archiveSoftware(id: UUID): Software {
        val software = getSoftwareById(id)
        software.status = "archived"
        return softwareRepository.save(software)
    }

    @Transactional
    fun deleteSoftware(id: UUID) {
        softwareRepository.deleteById(id)
    }
}