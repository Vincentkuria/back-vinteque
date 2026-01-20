package com.vinteque.vinteque.service

import com.vinteque.vinteque.model.Job
import com.vinteque.vinteque.repository.JobRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class JobService(
    private val jobRepository: JobRepository
) {

    fun getAllJobs(page: Int = 0, size: Int = 10, sortBy: String = "createdAt"): Page<Job> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy))
        return jobRepository.findAll(pageable)
    }

    fun getOpenJobs(page: Int = 0, size: Int = 10): Page<Job> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return jobRepository.findByStatusOrderByCreatedAtDesc("open", pageable)
    }

    fun getJobsByStatus(status: String, page: Int = 0, size: Int = 10): Page<Job> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return jobRepository.findByStatus(status, pageable)
    }

    fun getJobsByType(type: String, page: Int = 0, size: Int = 10): Page<Job> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return jobRepository.findByTypeOrderByCreatedAtDesc(type, pageable)
    }

    fun searchByDepartment(department: String, page: Int = 0, size: Int = 10): Page<Job> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return jobRepository.findByDepartmentContainingIgnoreCase(department, pageable)
    }

    fun searchByLocation(location: String, page: Int = 0, size: Int = 10): Page<Job> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return jobRepository.findByLocationContainingIgnoreCase(location, pageable)
    }

    fun getActiveJobs(page: Int = 0, size: Int = 10): Page<Job> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return jobRepository.findByDeadlineAfter(LocalDate.now(), pageable)
    }

    fun getJobById(id: UUID): Job {
        return jobRepository.findById(id)
            .orElseThrow { RuntimeException("Job not found with id: $id") }
    }

    @Transactional
    fun createJob(job: Job): Job {
        return jobRepository.save(job)
    }

    @Transactional
    fun updateJob(id: UUID, updatedJob: Job): Job {
        val job = getJobById(id)
        job.status = updatedJob.status
        job.location = updatedJob.location
        job.type = updatedJob.type
        job.department = updatedJob.department
        job.body = updatedJob.body
        job.deadline = updatedJob.deadline
        return jobRepository.save(job)
    }

    @Transactional
    fun openJob(id: UUID): Job {
        val job = getJobById(id)
        job.status = "open"
        return jobRepository.save(job)
    }

    @Transactional
    fun closeJob(id: UUID): Job {
        val job = getJobById(id)
        job.status = "closed"
        return jobRepository.save(job)
    }

    @Transactional
    fun deleteJob(id: UUID) {
        jobRepository.deleteById(id)
    }
}