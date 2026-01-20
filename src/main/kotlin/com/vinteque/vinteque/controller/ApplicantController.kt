package com.vinteque.vinteque.controller

import com.vinteque.vinteque.dto.ApplicantRequestDto
import com.vinteque.vinteque.dto.toApplicant
import com.vinteque.vinteque.model.Applicant
import com.vinteque.vinteque.service.ApplicantService
import com.vinteque.vinteque.service.ResourceService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/applicants")
class ApplicantController(
    private val applicantService: ApplicantService,
    private val uploadingService: ResourceService
) {

    @GetMapping
    fun getAllApplicants(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<Page<Applicant>> {
        val applicants = if (status != null) {
            applicantService.getApplicantsByStatus(status, page, size)
        } else {
            applicantService.getAllApplicants(page, size)
        }
        return ResponseEntity.ok(applicants)
    }

    @GetMapping("/job/{jobId}")
    fun getApplicantsByJob(
        @PathVariable jobId: UUID,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Applicant>> {
        val applicants = applicantService.getApplicantsByJobId(jobId, page, size)
        return ResponseEntity.ok(applicants)
    }

    @GetMapping("/search")
    fun searchApplicants(
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Applicant>> {
        val applicants = when {
            email != null -> applicantService.searchByEmail(email, page, size)
            name != null -> applicantService.searchByName(name, page, size)
            else -> applicantService.getAllApplicants(page, size)
        }
        return ResponseEntity.ok(applicants)
    }

    @GetMapping("/{id}")
    fun getApplicantById(@PathVariable id: UUID): ResponseEntity<Applicant> {
        val applicant = applicantService.getApplicantById(id)
        return ResponseEntity.ok(applicant)
    }

    @PostMapping("/job/{jobId}")
    fun createApplicant(
        @PathVariable jobId: UUID,
        @ModelAttribute applicantRequestDto: ApplicantRequestDto
    ): ResponseEntity<out Any?>? {
        if (applicantRequestDto.resume.isEmpty){
            return ResponseEntity.badRequest().body(mapOf("message" to "all fields are required"))
        }
        val resumeUrl: String=uploadingService.uploadFile(applicantRequestDto.resume)
        val createdApplicant = applicantService.createApplicant(jobId, applicantRequestDto.toApplicant(resumeUrl))
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplicant)
    }

    @PatchMapping("/{id}/status")
    fun updateApplicantStatus(
        @PathVariable id: UUID,
        @RequestBody status: String
    ): ResponseEntity<Applicant> {
        val applicant = applicantService.updateApplicantStatus(id, status)
        return ResponseEntity.ok(applicant)
    }

    @PatchMapping("/{id}/viewed")
    fun markAsViewed(@PathVariable id: UUID): ResponseEntity<Applicant> {
        val applicant = applicantService.markAsViewed(id)
        return ResponseEntity.ok(applicant)
    }

    @PatchMapping("/{id}/shortlist")
    fun shortlistApplicant(@PathVariable id: UUID): ResponseEntity<Applicant> {
        val applicant = applicantService.shortlistApplicant(id)
        return ResponseEntity.ok(applicant)
    }

    @PatchMapping("/{id}/reject")
    fun rejectApplicant(@PathVariable id: UUID): ResponseEntity<Applicant> {
        val applicant = applicantService.rejectApplicant(id)
        return ResponseEntity.ok(applicant)
    }

    @PatchMapping("/{id}/hire")
    fun hireApplicant(@PathVariable id: UUID): ResponseEntity<Applicant> {
        val applicant = applicantService.hireApplicant(id)
        return ResponseEntity.ok(applicant)
    }

    @DeleteMapping("/{id}")
    fun deleteApplicant(@PathVariable id: UUID): ResponseEntity<Void> {
        applicantService.deleteApplicant(id)
        return ResponseEntity.noContent().build()
    }

    // Bulk operations
    @PatchMapping("/bulk/viewed")
    fun markMultipleAsViewed(@RequestBody request: ApplicantBulkOperationRequest): ResponseEntity<List<Applicant>> {
        val applicants = applicantService.markMultipleAsViewed(request.ids)
        return ResponseEntity.ok(applicants)
    }

    @PatchMapping("/bulk/shortlist")
    fun shortlistMultiple(@RequestBody request: ApplicantBulkOperationRequest): ResponseEntity<List<Applicant>> {
        val applicants = applicantService.shortlistMultiple(request.ids)
        return ResponseEntity.ok(applicants)
    }

    @PatchMapping("/bulk/reject")
    fun rejectMultiple(@RequestBody request: ApplicantBulkOperationRequest): ResponseEntity<List<Applicant>> {
        val applicants = applicantService.rejectMultiple(request.ids)
        return ResponseEntity.ok(applicants)
    }
}

data class ApplicantBulkOperationRequest(
    val ids: List<UUID>
)