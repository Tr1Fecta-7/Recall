package com.recall.api.published.deck.requests

import com.recall.api.published.card.requests.PublishCardRequest

data class PublishDeckPostRequest(
	val title: String,
	val icon: String,
	val color: String,
	val cards: List<PublishCardRequest>
)
