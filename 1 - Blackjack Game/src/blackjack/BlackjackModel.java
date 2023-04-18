package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

/*
 * Models BlackJack game. Provides methods for managing cards and determining game state and results.
 */
public class BlackjackModel {
	
	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private Deck deck;
	
	/*
	 * getter for dealer's hand
	 */
	public ArrayList<Card> getDealerCards(){
		ArrayList<Card> returnDealerCards = new ArrayList<>();
		for(Card c : dealerCards) {
			returnDealerCards.add(c);
		}
		return returnDealerCards;
	}
	
	/*
	 * getter for player's hand
	 */
	public ArrayList<Card> getPlayerCards(){
		ArrayList<Card> returnPlayerCards = new ArrayList<>();
		for(Card c : playerCards) {
			returnPlayerCards.add(c);
		}
		return returnPlayerCards;
	}
	
	/*
	 * setter for dealer's hand
	 */
	public void setDealerCards(ArrayList<Card> cards) {
		ArrayList<Card> setDealerCards = new ArrayList<>();
		for(Card c : cards) {
			setDealerCards.add(c);
		}
		dealerCards = setDealerCards;
	}
	
	/*
	 * setter for player's hand
	 */
	public void setPlayerCards(ArrayList<Card> cards) {
		ArrayList<Card> setPlayerCards = new ArrayList<>();
		for(Card c : cards) {
			setPlayerCards.add(c);
		}
		playerCards = setPlayerCards;
	}
	
	/*
	 * Assigns a new instance of Deck to the deck and calls the deck's shuffle method.
	 */
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}
	
	/*
	 * Creates and deals two cards to the dealer's hand
	 */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		dealerCards.add(deck.dealOneCard());
		dealerCards.add(deck.dealOneCard());
	}
	
	/*
	 * Creates and deals two cards to the player's hand
	 */
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		playerCards.add(deck.dealOneCard());
		playerCards.add(deck.dealOneCard());
	}
	
	/*
	 * Deals one card to the player using the deck's deal one card method.
	 */
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
	
	/*
	 * Deals one card to the dealer using the deck's deal one card method.
	 */
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}
	
	/*
	 * Evaluates the parameter (hand) and returns an ArrayList representing the possible values of the hand.
	 * Value of the hand is represented by one or two integers, first one is always lesser. 
	 * If the values exceed 21, returns the lowest value. 
	 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand){
		int sum = 0;
		ArrayList<Integer> possibleValues = new ArrayList<>();
		for(Card c : hand) {
			sum += c.getRank().getValue();
		}
		possibleValues.add(sum);	
		sum = 0;
		for(Card c : hand) {
			if(c.getRank() == Rank.ACE) {
				sum += 11;
			} else {
				sum += c.getRank().getValue();
			}
		}
		if(sum <= 21 && sum != possibleValues.get(0)) {
			possibleValues.add(sum);
		}
		return possibleValues;
	}
	
	/*
	 * Assesses a hand and determines whether the hand is a blackjack, a bust, or not. 
	 */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if(hand == null || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		}
		if(possibleHandValues(hand).contains(21) && hand.size() == 2) {
			return HandAssessment.NATURAL_BLACKJACK;
		}
		if(possibleHandValues(hand).get(0) > 21) {
			return HandAssessment.BUST;
		}
		return HandAssessment.NORMAL;
	}
	
	/*
	 * Looks at player and dealer's cards to determine the outcome of the game based on BlackJack logic.
	 */
	public GameResult gameAssessment() {
		if(assessHand(playerCards).equals(HandAssessment.NATURAL_BLACKJACK)) {
			if(assessHand(dealerCards).equals(HandAssessment.NATURAL_BLACKJACK)) {
				return GameResult.PUSH;
			} 
			return GameResult.NATURAL_BLACKJACK;
		}
		if(assessHand(playerCards).equals(HandAssessment.BUST)) {
			return GameResult.PLAYER_LOST;
		}
		if(assessHand(dealerCards).equals(HandAssessment.BUST)) {
			return GameResult.PLAYER_WON;
		}
		if(possibleHandValues(playerCards).get(possibleHandValues(playerCards).size() - 1) >
		possibleHandValues(dealerCards).get(possibleHandValues(dealerCards).size() - 1)) {
			return GameResult.PLAYER_WON;
		}
		
		if(possibleHandValues(playerCards).get(possibleHandValues(playerCards).size() - 1) <
		possibleHandValues(dealerCards).get(possibleHandValues(dealerCards).size() - 1)) {
			return GameResult.PLAYER_LOST;
		}
		
		return GameResult.PUSH;
	}
	
	/*
	 * Looks at the dealer's cards and determines whether the dealer should take another card.
	 * True = dealer should take another card, false = dealer should not take another card.
	 * Method does not actually draw the dealer's cards. 
	 */
	public boolean dealerShouldTakeCard() {
		ArrayList<Integer> possibleValues = possibleHandValues(dealerCards);
		int numPossibleValues = possibleValues.size();
		if(possibleValues.get(numPossibleValues - 1) <= 16) {
			return true;
		}
		if(possibleValues.get(numPossibleValues - 1) >= 18) {
			return false;
		}
		if(numPossibleValues == 2) {
			return true;
		}
		return false;
		
	}

}
