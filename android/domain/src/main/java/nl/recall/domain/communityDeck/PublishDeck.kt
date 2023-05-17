package nl.recall.domain.communityDeck

import nl.recall.domain.deck.model.DeckWithCards
import org.koin.core.annotation.Factory

@Factory
class PublishDeck(private val communityDeckRepository: CommunityDeckRepository) {
    suspend operator fun invoke(deckWithCards: DeckWithCards) {
        return communityDeckRepository.publishDeck(deckWithCards)
    }
}
