package com.recall.api.published.deck.models

import com.recall.api.published.card.models.PublishedCard
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDate

@Entity
data class PublishedDeck(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	var title: String,
	var creation: LocalDate,
	var icon: String,
	var color: String,
	var downloads: Long,
	@OneToMany(mappedBy = "deck")
	var cards: List<PublishedCard>
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
		other as PublishedDeck

		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()

	@Override
	override fun toString(): String {
		return this::class.simpleName + "(id = $id )"
	}
}
