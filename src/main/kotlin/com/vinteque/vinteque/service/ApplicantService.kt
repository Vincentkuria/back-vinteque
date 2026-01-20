package com.vinteque.vinteque.service

import com.vinteque.vinteque.model.Applicant
import com.vinteque.vinteque.repository.ApplicantRepository
import com.vinteque.vinteque.repository.JobRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ApplicantService(
    private val applicantRepository: ApplicantRepository,
    private val jobRepository: JobRepository
) {

    fun getAllApplicants(page: Int = 0, size: Int = 10): Page<Applicant> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return applicantRepository.findAll(pageable)
    }

    fun getApplicantsByJobId(jobId: UUID, page: Int = 0, size: Int = 10): Page<Applicant> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return applicantRepository.findByJobId(jobId, pageable)
    }

    fun getApplicantsByStatus(status: String, page: Int = 0, size: Int = 10): Page<Applicant> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return applicantRepository.findByStatusOrderByCreatedAtDesc(status, pageable)
    }

    fun searchByEmail(email: String, page: Int = 0, size: Int = 10): Page<Applicant> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return applicantRepository.findByEmailContainingIgnoreCase(email, pageable)
    }

    fun searchByName(name: String, page: Int = 0, size: Int = 10): Page<Applicant> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return applicantRepository.findByNameContainingIgnoreCase(name, pageable)
    }

    fun getApplicantById(id: UUID): Applicant {
        return applicantRepository.findById(id)
            .orElseThrow { RuntimeException("Applicant not found with id: $id") }
    }

    @Transactional
    fun createApplicant(jobId: UUID, applicant: Applicant): Applicant {
        val job = jobRepository.findById(jobId)
            .orElseThrow { RuntimeException("Job not found with id: $jobId") }
        applicant.job = job
        return applicantRepository.save(applicant)
    }

    @Transactional
    fun updateApplicantStatus(id: UUID, status: String): Applicant {
        val applicant = getApplicantById(id)
        applicant.status = status
        return applicantRepository.save(applicant)
    }

    @Transactional
    fun markAsViewed(id: UUID): Applicant {
        return updateApplicantStatus(id, "viewed")
    }

    @Transactional
    fun shortlistApplicant(id: UUID): Applicant {
        return updateApplicantStatus(id, "shortlisted")
    }

    @Transactional
    fun rejectApplicant(id: UUID): Applicant {
        return updateApplicantStatus(id, "rejected")
    }

    @Transactional
    fun hireApplicant(id: UUID): Applicant {
        return updateApplicantStatus(id, "hired")
    }

    @Transactional
    fun deleteApplicant(id: UUID) {
        applicantRepository.deleteById(id)
    }

    // Bulk operations
    @Transactional
    fun markMultipleAsViewed(ids: List<UUID>): List<Applicant> {
        return ids.map { markAsViewed(it) }
    }

    @Transactional
    fun shortlistMultiple(ids: List<UUID>): List<Applicant> {
        return ids.map { shortlistApplicant(it) }
    }

    @Transactional
    fun rejectMultiple(ids: List<UUID>): List<Applicant> {
        return ids.map { rejectApplicant(it) }
    }
}