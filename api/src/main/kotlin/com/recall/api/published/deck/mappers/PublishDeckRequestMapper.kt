package com.recall.api.published.deck.mappers

import com.recall.api.published.deck.models.PublishedDeck
import com.recall.api.published.deck.requests.PublishDeckPostRequest
import java.time.LocalDate

object PublishDeckRequestMapper {
	fun PublishDeckPostRequest.toPublishedDeck(): PublishedDeck {
		return PublishedDeck(
			title = title,
			creation = LocalDate.now(),
			icon = icon,
			downloads = 0,
			color = color,
			cards = emptyList()
		)
	}
}