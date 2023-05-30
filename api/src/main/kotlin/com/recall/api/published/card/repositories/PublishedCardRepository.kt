package com.recall.api.published.card.repositories

import com.recall.api.published.card.models.PublishedCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishedCardRepository : JpaRepository<PublishedCard, Long>