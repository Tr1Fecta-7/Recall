package com.recall.api.rest

import com.recall.api.models.PublishedCard
import com.recall.api.models.PublishedDeck
import com.recall.api.repositories.PublishedCardRepository
import com.recall.api.repositories.PublishedDeckRepository
import com.recall.api.request.PublishDeckRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDate
import java.util.*

@RequestMapping("/api/v1/deck")
@RestController
class PublishedDeckController(
	@Autowired val publishedDeckRepository: PublishedDeckRepository,
	@Autowired val publishedCardRepository: PublishedCardRepository
) {
	@GetMapping
	fun getAllDecks(): ResponseEntity<List<PublishedDeck>> {
		return ResponseEntity.ok(
			publishedDeckRepository.findAll()
		)
	}

	@PostMapping
	fun saveDeck(@RequestBody deck: PublishDeckRequest): ResponseEntity<Any> {
		val savedDeck = publishedDeckRepository.save(
			PublishedDeck(
				title = deck.title,
				creation = LocalDate.now(),
				icon = deck.icon,
				color = deck.color,
				downloads = 0,
				cards = emptyList()
			)
		)

		deck.cards.forEach { card ->
			publishedCardRepository.save(
				PublishedCard(front = card.front, back = card.back, deck = savedDeck)
			)
		}

		val location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.build(savedDeck.id)

		return ResponseEntity
			.created(location)
			.build()
	}

	@GetMapping("{id}")
	fun getDeckById(@PathVariable id: Long): ResponseEntity<PublishedDeck> {
		val deck = publishedDeckRepository
			.findById(id)
			.orElseThrow{ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the published deck with id $id")}

		return ResponseEntity.ok(deck)
	}
}
