package com.vinteque.vinteque.controller

import com.vinteque.vinteque.dto.JobRequestDto
import com.vinteque.vinteque.dto.JobUpdateRequestDto
import com.vinteque.vinteque.dto.toJob
import com.vinteque.vinteque.model.Job
import com.vinteque.vinteque.service.JobService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/jobs")
class JobController(
    private val jobService: JobService
) {

    @GetMapping
    fun getAllJobs(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "createdAt") sortBy: String,
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) type: String?
    ): ResponseEntity<Page<Job>> {
        val jobs = when {
            status != null -> jobService.getJobsByStatus(status, page, size)
            type != null -> jobService.getJobsByType(type, page, size)
            else -> jobService.getAllJobs(page, size, sortBy)
        }
        return ResponseEntity.ok(jobs)
    }

    @GetMapping("/open")
    fun getOpenJobs(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Job>> {
        val jobs = jobService.getOpenJobs(page, size)
        return ResponseEntity.ok(jobs)
    }

    @GetMapping("/active")
    fun getActiveJobs(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Job>> {
        val jobs = jobService.getActiveJobs(page, size)
        return ResponseEntity.ok(jobs)
    }

    @GetMapping("/search")
    fun searchJobs(
        @RequestParam(required = false) department: String?,
        @RequestParam(required = false) location: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Job>> {
        val jobs = when {
            department != null -> jobService.searchByDepartment(department, page, size)
            location != null -> jobService.searchByLocation(location, page, size)
            else -> jobService.getAllJobs(page, size)
        }
        return ResponseEntity.ok(jobs)
    }

    @GetMapping("/{id}")
    fun getJobById(@PathVariable id: UUID): ResponseEntity<Job> {
        val job = jobService.getJobById(id)
        return ResponseEntity.ok(job)
    }

    @PostMapping
    fun createJob(@RequestBody jobRequestDto: JobRequestDto): ResponseEntity<Job> {
        val createdJob = jobService.createJob(jobRequestDto.toJob())
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob)
    }

    @PutMapping("/{id}")
    fun updateJob(
       @RequestBody jobUpdateRequestDto: JobUpdateRequestDto
    ): ResponseEntity<Job> {
        val updatedJob = jobService.updateJob(jobUpdateRequestDto)
        return ResponseEntity.ok(updatedJob)
    }

    @PatchMapping("/{id}/open")
    fun openJob(@PathVariable id: UUID): ResponseEntity<Job> {
        val job = jobService.openJob(id)
        return ResponseEntity.ok(job)
    }

    @PatchMapping("/{id}/close")
    fun closeJob(@PathVariable id: UUID): ResponseEntity<Job> {
        val job = jobService.closeJob(id)
        return ResponseEntity.ok(job)
    }

    @DeleteMapping("/{id}")
    fun deleteJob(@PathVariable id: UUID): ResponseEntity<Void> {
        jobService.deleteJob(id)
        return ResponseEntity.noContent().build()
    }
}