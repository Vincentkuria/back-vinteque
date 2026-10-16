package com.vinteque.vinteque.controller

import com.vinteque.vinteque.model.Stage
import com.vinteque.vinteque.service.StageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/stages"])
class StageController (private val stageService: StageService) {

    @GetMapping
    fun getStages(): ResponseEntity<List<Stage>> {
        return ResponseEntity.ok(stageService.getAll())
    }
}