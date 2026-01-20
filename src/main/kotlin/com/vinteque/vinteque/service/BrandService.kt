package com.vinteque.vinteque.service

import com.vinteque.vinteque.dto.BrandUpdateRequestDto
import com.vinteque.vinteque.model.Brand
import com.vinteque.vinteque.repository.BrandRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class BrandService(
    private val brandRepository: BrandRepository,
    private val resourceService: ResourceService
) {

    fun getAllBrands(page: Int = 0, size: Int = 10, sortBy: String = "createdAt"): Page<Brand> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy))
        return brandRepository.findAll(pageable)
    }

    fun getPublishedBrands(page: Int = 0, size: Int = 10): Page<Brand> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return brandRepository.findByStatusOrderByCreatedAtDesc("published", pageable)
    }

    fun getBrandsByStatus(status: String, page: Int = 0, size: Int = 10): Page<Brand> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return brandRepository.findByStatus(status, pageable)
    }

    fun searchByCompany(companyName: String, page: Int = 0, size: Int = 10): Page<Brand> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return brandRepository.findByCompanyNameContainingIgnoreCase(companyName, pageable)
    }

    fun searchByLocation(location: String, page: Int = 0, size: Int = 10): Page<Brand> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return brandRepository.findByLocationContainingIgnoreCase(location, pageable)
    }

    fun searchByTitle(title: String, page: Int = 0, size: Int = 10): Page<Brand> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        return brandRepository.findByTitleContainingIgnoreCase(title, pageable)
    }

    fun getBrandById(id: UUID): Brand {
        return brandRepository.findById(id)
            .orElseThrow { RuntimeException("Brand not found with id: $id") }
    }

    @Transactional
    fun createBrand(brand: Brand): Brand {
        return brandRepository.save(brand)
    }

    @Transactional
    fun updateBrand(brandUpdateRequestDto: BrandUpdateRequestDto): Brand {
        val brand = getBrandById(brandUpdateRequestDto.id)
        brandUpdateRequestDto.let {
            it.status?.let { status ->brand.status = status }
            it.displayImg?.let { displayImg ->brand.displayImg = resourceService.uploadFile(displayImg)}
            it.companyName?.let { companyName ->brand.companyName = companyName }
            it.title?.let { title ->brand.title = title }
            it.bgColor?.let { bgColor ->brand.bgColor = bgColor }
            it.location?.let { location ->brand.location = location }
            it.heroImg?.let { heroImg ->brand.heroImg = resourceService.uploadFile(heroImg)}
            it.aboutClient?.let { aboutClient ->brand.aboutClient = aboutClient}
            it.goal?.let { goal ->brand.goal = goal }
            it.showcaseImgs?.let { showcaseImgs ->showcaseImgs.map { showcaseImg ->resourceService.uploadFile(showcaseImg)}}
            it.showcaseBgColor?.let { showcaseBgColor ->brand.showcaseBgColor = showcaseBgColor }
        }

        return brandRepository.save(brand)
    }

    @Transactional
    fun publishBrand(id: UUID): Brand {
        val brand = getBrandById(id)
        brand.status = "published"
        return brandRepository.save(brand)
    }

    @Transactional
    fun archiveBrand(id: UUID): Brand {
        val brand = getBrandById(id)
        brand.status = "archived"
        return brandRepository.save(brand)
    }

    @Transactional
    fun deleteBrand(id: UUID) {
        brandRepository.deleteById(id)
    }
}