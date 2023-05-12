package com.recall.api.mappers

import com.recall.api.models.PublishedDeck
import com.recall.api.request.PublishDeckRequest
import java.time.LocalDate

object PublishDeckRequestMapper {
	fun PublishDeckRequest.toPublishedDeck(): PublishedDeck {
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