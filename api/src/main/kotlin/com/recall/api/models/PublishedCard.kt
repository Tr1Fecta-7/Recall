package com.recall.api.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class PublishedCard(
	@Id
	var id: Long,
	var front: String,
	var back: String,
	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	var deck: PublishedDeck
)
