package com.recall.api.repositories

import com.recall.api.models.PublishedCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishedCardRepository : JpaRepository<PublishedCard, Long>