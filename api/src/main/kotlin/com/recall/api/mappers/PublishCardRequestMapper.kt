package com.recall.api.mappers

import com.recall.api.models.PublishedCard
import com.recall.api.models.PublishedDeck
import com.recall.api.request.PublishCardRequest

object PublishCardRequestMapper {
	fun PublishCardRequest.toPublishedCard(deck: PublishedDeck): PublishedCard {
		return PublishedCard(
			front = front,
			back = back,
			deck = deck
		)
	}
}