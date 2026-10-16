package com.vinteque.vinteque.controller

import com.vinteque.vinteque.model.Lead
import com.vinteque.vinteque.service.LeadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/leads"])
class LeadController(private val leadService: LeadService) {

    @GetMapping
    fun getLeadsByCurrentStage(@RequestParam stageName: String?, @RequestParam(defaultValue = "0") pageNumber:Int, @RequestParam(defaultValue = "20") pageSize:Int): ResponseEntity<Iterable<Lead?>> {
        return ResponseEntity.ok(leadService.getLeadByCurrentStage(stageName, pageNumber, pageSize))
    }
}