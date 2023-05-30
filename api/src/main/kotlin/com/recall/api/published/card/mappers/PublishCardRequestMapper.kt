package com.recall.api.published.card.mappers

import com.recall.api.published.card.models.PublishedCard
import com.recall.api.published.card.requests.PublishCardRequest
import com.recall.api.published.deck.models.PublishedDeck

object PublishCardRequestMapper {
	fun PublishCardRequest.toPublishedCard(deck: PublishedDeck): PublishedCard {
		return PublishedCard(
			front = front,
			back = back,
			deck = deck
		)
	}
}