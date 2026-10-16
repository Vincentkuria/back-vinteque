package com.vinteque.vinteque.service

import com.vinteque.vinteque.model.Lead
import com.vinteque.vinteque.model.Stage
import com.vinteque.vinteque.repository.LeadRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class LeadService(
    private val leadRepository: LeadRepository,
    private val stageService: StageService,
) {

    fun getLeadByCurrentStage(stageName: String?,pageNumber: Int,pageSize: Int): Iterable<Lead?> {
        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        if (stageName.isNullOrBlank()) {
            return leadRepository.findAll(pageable)
        }else{
            return leadRepository.findByCurrentStage(stageName,pageable)
        }


    }
}