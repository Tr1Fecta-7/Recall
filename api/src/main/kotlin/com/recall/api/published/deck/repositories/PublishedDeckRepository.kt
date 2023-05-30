package com.recall.api.published.deck.repositories

import com.recall.api.published.deck.models.PublishedDeck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishedDeckRepository : JpaRepository<PublishedDeck, Long> {
    fun findAllByTitleContainingIgnoreCaseOrderByDownloadsDesc(title: String): List<PublishedDeck>
    fun findAllByOrderByDownloadsDesc(): MutableList<PublishedDeck>
}