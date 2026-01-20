package com.vinteque.vinteque.controller

import com.vinteque.vinteque.dto.BrandRequestDto
import com.vinteque.vinteque.dto.BrandUpdateRequestDto
import com.vinteque.vinteque.dto.toBrand
import com.vinteque.vinteque.model.Brand
import com.vinteque.vinteque.service.BrandService
import com.vinteque.vinteque.service.ResourceService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/brands")
class BrandController(
    private val brandService: BrandService,
    private val resourceService: ResourceService
) {

    @GetMapping
    fun getAllBrands(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "createdAt") sortBy: String,
        @RequestParam(required = false) status: String?
    ): ResponseEntity<Page<Brand>> {
        val brands = if (status != null) {
            brandService.getBrandsByStatus(status, page, size)
        } else {
            brandService.getAllBrands(page, size, sortBy)
        }
        return ResponseEntity.ok(brands)
    }

    @GetMapping("/published")
    fun getPublishedBrands(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Brand>> {
        val brands = brandService.getPublishedBrands(page, size)
        return ResponseEntity.ok(brands)
    }

    @GetMapping("/search")
    fun searchBrands(
        @RequestParam(required = false) company: String?,
        @RequestParam(required = false) location: String?,
        @RequestParam(required = false) title: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Brand>> {
        val brands = when {
            company != null -> brandService.searchByCompany(company, page, size)
            location != null -> brandService.searchByLocation(location, page, size)
            title != null -> brandService.searchByTitle(title, page, size)
            else -> brandService.getAllBrands(page, size)
        }
        return ResponseEntity.ok(brands)
    }

    @GetMapping("/{id}")
    fun getBrandById(@PathVariable id: UUID): ResponseEntity<Brand> {
        val brand = brandService.getBrandById(id)
        return ResponseEntity.ok(brand)
    }

    @PostMapping
    fun createBrand(@ModelAttribute brandRequestDto: BrandRequestDto): ResponseEntity<Brand> {
        val displayImgUrl=brandRequestDto.displayImg?.let { resourceService.uploadFile(it) }
        val heroImgUrl=brandRequestDto.heroImg?.let { resourceService.uploadFile(it) }
        val showcaseImageUrls=brandRequestDto.showcaseImgs?.let { images ->
            images.map {
                resourceService.uploadFile(it)
            }
        }
        val createdBrand = brandService.createBrand(brandRequestDto.toBrand(displayImgUrl,heroImgUrl,showcaseImageUrls))
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand)
    }

    @PutMapping("/{id}")
    fun updateBrand(
        @ModelAttribute brandUpdateRequestDto: BrandUpdateRequestDto
    ): ResponseEntity<Brand> {
        val updatedBrand = brandService.updateBrand(brandUpdateRequestDto)
        return ResponseEntity.ok(updatedBrand)
    }

    @PatchMapping("/{id}/publish")
    fun publishBrand(@PathVariable id: UUID): ResponseEntity<Brand> {
        val brand = brandService.publishBrand(id)
        return ResponseEntity.ok(brand)
    }

    @PatchMapping("/{id}/archive")
    fun archiveBrand(@PathVariable id: UUID): ResponseEntity<Brand> {
        val brand = brandService.archiveBrand(id)
        return ResponseEntity.ok(brand)
    }

    @DeleteMapping("/{id}")
    fun deleteBrand(@PathVariable id: UUID): ResponseEntity<Void> {
        brandService.deleteBrand(id)
        return ResponseEntity.noContent().build()
    }
}