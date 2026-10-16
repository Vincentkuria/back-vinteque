package com.vinteque.vinteque.service

import com.vinteque.vinteque.model.Stage
import com.vinteque.vinteque.repository.StageRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class StageService(private val repository: StageRepository) {

    fun getAll(): List<Stage> = repository.findAll()
    fun getById(id: UUID): Stage? = repository.findById(id).orElse(null)
    fun getByName(stageName: String): Stage? = repository.findByName(stageName).orElse(null)
}