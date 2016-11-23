/**
 * Player class for Blackjack game. Players hold a hand of cards, and have chips that can be bet.
 * The first player created should be the dealer. No other player should have name "Dealer".
 * 
 * @author Kristofer
 *
 */
public class Player {
	private static final String DEALER = "Dealer";
	private String name;
	private boolean aces;
	private boolean splitHandAces;
	private boolean hasSplitHand;
	private Card[] hand;
	private Card[] splitHand;
	private int top;
	private int splitTop;
	private int chips;
	private int bet;
	private int maxChips;
	private int roundsPlayed;
	private static int players = 0;
	private boolean npc;
	private boolean firstHandShowing;
	/**
	 * Default constructor. If it is creating the first player it creates the dealer.
	 * Use for dealer creation.
	 */
	public Player(){
		if(players == 0){
			this.name = DEALER;
			this.chips = 0;
			this.bet = 0;
		}
		else{
			this.name = "Player" + players;
			this.chips = 5000;
			this.bet = 200;
		}
		players++;
		this.hand = new Card[24];
		this.splitHand = new Card[24];
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.hasSplitHand = false;
		this.npc=true;
		this.firstHandShowing = true;
		this.roundsPlayed = 0;
		this.maxChips = this.chips;
	}
	/**
	 * Creates a player. If it is the first player, it creates the dealer.
	 * @param name of player
	 * @param npc is Non-Player Character
	 */
	public Player(String name, boolean npc){
		if(players == 0){
			this.name = DEALER;
			this.chips = 0;
			this.bet = 0;
			this.npc = true;
		}
		else{
			this.name = name;
			this.chips = 5000;
			this.bet = 200;
			this.npc = npc;
		}
		players++;
		this.hand = new Card[24];
		this.splitHand = new Card[24];
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.hasSplitHand = false;
		this.npc=true;
		this.firstHandShowing = true;
		this.roundsPlayed = 0;
		this.maxChips = this.chips;
	}
	/**
	 * Creates a player. If it is the first player, it creates the dealer.
	 * @param name of player
	 * @param npc is Non-Player Character
	 */
	public Player(String name, boolean npc, int startingChips){
		if(players == 0){
			this.name = DEALER;
			this.chips = 0;
			this.bet = 0;
			this.npc = true;
		}
		else{
			this.name = name;
			this.chips = startingChips;
			this.bet = 200;
			this.npc = npc;
		}
		players++;
		this.hand = new Card[24];
		this.splitHand = new Card[24];
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.hasSplitHand = false;
		this.npc=true;
		this.firstHandShowing = true;
		this.roundsPlayed = 0;
		this.maxChips = this.chips;
	}
	/**
	 * Return name of player.
	 * @return name of player
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Return currently held chips of player.
	 * @return chips of player
	 */
	public int getChips(){
		return this.chips;
	}
	/**
	 * Decreases player chips by chips entered.
	 * @param chips removed from player
	 */
	public void removeChips(int chips){
		this.chips -= chips;
	}
	/**
	 * Increases player chips by chips entered.
	 * @param chips given to player
	 */
	public void addChips(int chips){
		this.chips += chips;
		if(this.chips > this.maxChips)
			this.maxChips = this.chips;
	}
	/**
	 * Changes the amount bet by player.
	 * @param bet by player
	 */
	public void setBet(int bet){
		this.bet = bet;
	}
	/**
	 * Returns the current bet of this player.
	 * @return bet of this player
	 */
	public int getBet(){
		return this.bet;
	}
	/**
	 * Returns true if this player is Non-Player Character.
	 * @return true if this player is Non-Player Character
	 */
	public boolean isNPC(){
		return this.npc;
	}
	/**
	 * Return true if the current hand can be split.
	 * @return true if the current hand can be split
	 */
	public boolean canSplit(){
		//Must have only two cards, faces must match, and have enough chips to split.
		if(top==2 && hand[0].face == hand[1].face && this.chips-this.getBet()>=0 && this.name != DEALER)
			return true;
		return false;
	}
	public void splitHand(){
		this.hasSplitHand = true;
		this.receiveSplitHandCard(this.hand[--top]);
		this.splitHandAces = this.aces;
		this.removeChips(this.getBet());
		// Having a Singleton deck class just came in handy.
		this.receiveCard(Deck.getInstance().dealCard());
		this.receiveSplitHandCard(Deck.getInstance().dealCard());
	}
	/**
	 * Return true if the current hand is Blackjack.
	 * @return true if the current hand is Blackjack
	 */
	public boolean hasBlackjack(){
		if(top==2){
			if(hand[0].face == Card.Face.ACE && hand[1].getValue()==10){
				return true;
			}
			if(hand[1].face == Card.Face.ACE && hand[0].getValue()==10){
				return true;
			}
		}
		return false;
	}
	/**
	 * Return true if the current hand is Blackjack.
	 * @return true if the current hand is Blackjack
	 */
	public boolean hasSplitHandBlackjack(){
		if(splitTop==2){
			if(splitHand[0].face == Card.Face.ACE && splitHand[1].getValue()==10){
				return true;
			}
			if(splitHand[1].face == Card.Face.ACE && splitHand[0].getValue()==10){
				return true;
			}
		}
		return false;
	}
	/**
	 * Passes a card to the player that is stored in the Card array at position top.
	 * @param card added to player Card array
	 */
	public void receiveCard(Card card){
		hand[top++] = card;
		if(card.face == Card.Face.ACE){
			aces = true;
		}
	}
	public boolean hasSplitHand(){
		return this.hasSplitHand;
	}
	/**
	 * Passes a card to the player that is stored in the Card array at position top.
	 * @param card added to player Card array
	 */
	public void receiveSplitHandCard(Card card){
		splitHand[splitTop++] = card;
		if(card.face == Card.Face.ACE){
			splitHandAces = true;
		}
	}
	/**
	 * Returns a string representation of this player's hand.
	 * If this player is named "Dealer", the second card is hidden until the end of the hand.
	 * @return String player's hand.
	 */
	public String getHand(){
		StringBuilder s = new StringBuilder("");
		for(int i = 0; i < top; i++){
			if(this.name.equals("Dealer")&&i==1&&firstHandShowing){
				s.append("?? ");
				firstHandShowing = false;
			}
			else
				s.append(hand[i].toString() + " ");
		}
		if(this.hasSplitHand){
			s.append(" || ");
			for(int i = 0; i < splitTop;i++){
				s.append(splitHand[i].toString() + " ");
			}
		}
		return s.toString();
	}
	/**
	 * Returns the current value of this player's hand.
	 * If there are any Aces in the hand, it will return the highest value under 22.
	 * @return
	 */
	public int getHandValue(){
		int i = 0;
		for(int j = 0; j < top; j++){
			i += hand[j].getValue();
		}
		if(aces && i + 10 <= 21){
			i += 10;
		}
		return i;
	}
	/**
	 * Returns the current value of this player's hand.
	 * If there are any Aces in the hand, it will return the highest value under 22.
	 * @return
	 */
	public int getSplitHandValue(){
		int i = 0;
		for(int j = 0; j < splitTop; j++){
			i += splitHand[j].getValue();
		}
		if(splitHandAces && i + 10 <= 21){
			i += 10;
		}
		return i;
	}
	/**
	 * Resets this players hand so they have no cards.
	 */
	public void discardHand(){
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.firstHandShowing = true;
		this.hasSplitHand = false;
		this.roundsPlayed++;
	}
	public String toString(){
		StringBuilder s = new StringBuilder("");
		s.append("Name: " + this.name + " Max Chips: " + this.maxChips + " Rounds Played: " + this.roundsPlayed);
		return s.toString();
		
	}
}
