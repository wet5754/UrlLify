package com.example.urlLify.url.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UrlRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    private val defaultTtl = 60L * 60L * 24L // 1일 (단위: 초)

    fun findShortenByOrigin(origin: String): String? {
        return redisTemplate.opsForValue().get(origin)
    }

    fun findOriginByShorten(shorten: String): String? {
        return redisTemplate.opsForValue().get(shorten)
    }

    fun cacheOriginByShorten(origin: String, shortUrl: String, ttl: Long = defaultTtl) {
        redisTemplate.opsForValue().set(origin, shortUrl, ttl)
    }

    fun cacheShortenByOrigin(shortUrl: String, origin: String, ttl: Long = defaultTtl) {
        redisTemplate.opsForValue().set(shortUrl, shortUrl, ttl)
    }
}
