package com.recall.api.request

data class PublishDeckPutRequest(
	val id: Long,
	val title: String,
	val icon: String,
	val color: String,
	val downloads: Long,
)
