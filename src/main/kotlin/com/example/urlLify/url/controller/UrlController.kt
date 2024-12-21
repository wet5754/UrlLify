package com.example.urlLify.url.controller

import com.example.urlLify.core.exception.InvalidInputException
import com.example.urlLify.tenant.service.TenantService
import com.example.urlLify.url.dto.ShortenRequestDto
import com.example.urlLify.url.dto.ShortenResponseDto
import com.example.urlLify.url.service.UrlService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.net.URI

@RequestMapping(value = ["/urls"])
@RestController
class UrlController(
    private val tenantService: TenantService,
    private val urlService: UrlService,
) {
    private val serverDomain = "http://localhost:8080"

    @PostMapping(value = ["/shorten"])
    fun shortener(
        @RequestHeader("X-TENANT-NAME") tenantName: String, @RequestBody @Valid shortenDto: ShortenRequestDto
    ): ResponseEntity<ShortenResponseDto> {
        tenantService.findByName(tenantName) ?: throw InvalidInputException("Tenant 정보를 찾을 수 없습니다.")

        val result = serverDomain + "/urls/" + urlService.shorten(shortenDto.uri)
        return ResponseEntity.created(
            URI.create(result)
        ).body(ShortenResponseDto(result))
    }

    @GetMapping(value = ["/{shorten}"])
    fun redirect(
        @RequestHeader("X-TENANT-NAME") tenantName: String, @PathVariable shorten: String
    ): RedirectView {
        val tenant = tenantService.findByName(tenantName) ?: throw InvalidInputException("Tenant 정보를 찾을 수 없습니다.")
        val origin = urlService.redirect(shorten)
        val result = RedirectView("https://" + tenant.domain + "/" + origin)
        println(result.url)
        return result
    }
}
