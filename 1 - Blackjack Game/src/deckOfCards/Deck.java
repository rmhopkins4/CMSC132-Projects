package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * Wrapper class for an ArrayList of Card objects in a deck.
 */
public class Deck {

	ArrayList<Card> cards;
	
	public Deck() {
		cards = new ArrayList<>();
		for(Suit s : Suit.values()) {
			for(Rank r : Rank.values()) {
				cards.add(new Card(r, s));
			}
		}
	}
	
	/*
	 * Calls Collections.shuffle to randomize the deck of cards reliably and predictably.
	 */
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(cards, randomNumberGenerator);
	}
	
	/*
	 * Pops the first card from the list and returns it. 
	 */
	public Card dealOneCard() {
		return cards.remove(0);
	}
	
}
