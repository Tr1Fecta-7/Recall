package com.recall.api.mappers

import com.recall.api.models.PublishedDeck
import com.recall.api.request.PublishDeckPostRequest
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