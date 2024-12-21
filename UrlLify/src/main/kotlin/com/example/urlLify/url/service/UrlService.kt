package com.example.urlLify.url.service

import com.example.urlLify.core.exception.InvalidInputException
import com.example.urlLify.url.repository.UrlRepository
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class UrlService(
    private val urlRepository: UrlRepository,
) {
    private val baseChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val base = baseChars.length
    private val delimiter: String = "^|$"

    fun shorten(origin: String): String {
        val cachedShorten = urlRepository.findShortenByOrigin(origin)
        if (cachedShorten != null) return cachedShorten.split(delimiter)[0]

        var shorten: String = generateShortHash(origin)
        var appender = ""
        for (i in 0..10) {
            if (urlRepository.findOriginByShorten(shorten) == null) break
            shorten = generateShortHash(origin + appender)
            if (i == 10) {
                throw InvalidInputException("Shorten URL 생성 실패")
            }
            appender += "%"
        }

        println(shorten)
        urlRepository.cacheOriginByShorten(origin, shorten)
        urlRepository.cacheShortenByOrigin(shorten, origin)

        return shorten
    }

    // SHA-256 해시 생성
    private fun generateHash(input: String): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input.toByteArray())
    }

    // 해시를 Base62로 변환 후 10문자로 제한
    private fun generateShortHash(input: String): String {
        val hashBytes = generateHash(input)
        val number = hashBytes.fold(0L) { acc, byte -> (acc shl 8) or (byte.toLong() and 0xFF) }

        val encoded = StringBuilder()
        var tempNumber = number
        while (tempNumber > 0 && encoded.length < 10) {
            val remainder = (tempNumber % base).toInt()
            encoded.append(baseChars[remainder])
            tempNumber /= base
        }

        return encoded.reverse().toString().padStart(10, 'a') // 길이가 짧으면 'a'로 채움
    }

    fun redirect(shorten: String): String {
        return urlRepository.findOriginByShorten(shorten) ?: throw InvalidInputException("Shorten URL 정보를 찾을 수 없습니다.")
    }
}
