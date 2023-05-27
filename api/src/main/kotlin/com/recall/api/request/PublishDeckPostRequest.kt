package com.recall.api.request

data class PublishDeckPostRequest(
	val title: String,
	val icon: String,
	val color: String,
	val cards: List<PublishCardRequest>
)
