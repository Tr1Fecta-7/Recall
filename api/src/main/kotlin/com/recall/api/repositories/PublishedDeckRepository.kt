package com.recall.api.repositories

import com.recall.api.models.PublishedDeck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishedDeckRepository : JpaRepository<PublishedDeck, Long> {
    fun findAllByTitleContainingIgnoreCaseOOrderByDownloads(title: String): List<PublishedDeck>
}