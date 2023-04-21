package com.recall.api.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.*

@Entity
data class PublishedDeck(
	@Id
	var id: Long?,
	var title: String,
	var creation: Date,
	var icon: String,
	var backgroundColor: String,
	var downloads: Int,
	@OneToMany(mappedBy = "deck")
	var cards: Set<PublishedCard>
)
