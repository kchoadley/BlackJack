/**
 * A playing Card class that stores an enum for the face and an enum for the value.
 * When returning the value of the face, ACE is 1.
 * 
 * @author Kristofer
 *
 */
public class Card {
	public enum Suit{
		HEART("\u2665"), DIAMOND("\u2666"), CLUB("\u2663"), SPADE("\u2660");
		String suitChar;
		private Suit(String suitChar){
			this.suitChar = suitChar;
		}
	}
	public enum Face{
		ACE(1, 'A'), TWO(2, '2'), THREE(3, '3'), FOUR(4, '4'), FIVE(5, '5'), SIX(6, '6'), SEVEN(7, '7'), EIGHT(8, '8'), NINE(9, '9'), TEN(10, 'T'), JACK(10, 'J'), QUEEN(10, 'Q'), KING(10, 'K');
		private int value;
		private char faceChar;
		private Face(int value, char faceChar){
			this.value = value;
			this.faceChar = faceChar;
		}
	}
	Suit suit;
	Face face;
	
	public Card(Suit suit, Face face){
		this.suit = suit;
		this.face = face;
	}
	/**
	 * Returns the value of the face of the card.
	 * In the case of Ace, returns 1.
	 * 
	 * @return face value
	 */
	public int getValue(){
		return this.face.value;
	}
	/**
	 * Returns a string representation of the card. Will be 2 characters long.
	 * 1 character for the face and 1 character for the suit.
	 */
	public String toString()
    {
        String result = face.faceChar + "" + suit.suitChar;
        return result;
    }
}
