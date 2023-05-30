package com.recall.api.published.card.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.recall.api.published.deck.models.PublishedDeck
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
data class PublishedCard(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	var front: String,
	var back: String,
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "deck_id", nullable = false)
	var deck: PublishedDeck
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
		other as PublishedCard

		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()

	@Override
	override fun toString(): String {
		return this::class.simpleName + "(id = $id )"
	}
}