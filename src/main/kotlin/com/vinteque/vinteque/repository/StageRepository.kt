package com.vinteque.vinteque.repository

import com.vinteque.vinteque.model.Stage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface StageRepository: JpaRepository<Stage, UUID> {
    fun findByName(name: String): Optional<Stage>
}