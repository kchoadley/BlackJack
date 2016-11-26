/**
 * Ideas of features to add:
 * 		Add automated card counter?(up if high card, down if low card?)
 * 		Store player max chips?
 * 		Store player rounds lasted?
 * 		Second Player array for dead players?
 * 		NPC difficulty and randomness built into Player class if NPC?
 * 
 * First player added must be "Dealer".
 * This is the logical backend to the Blackjack game. Needs a user interface to work for player controlled characters.
 * Currently simulates NPC's playing until everyone runs out of chips.
 * 
 * 
 * @author Kristofer
 *
 */
public class BlackjackLogic {
	// Initialize class variables.
	Player[] players;
	private static int top;
	private static int turn;
	private static int rounds;
	private StringBuilder endResults;
	private static Deck deck;
	/**
	 * Default constructor contains 2 players.
	 */
	public BlackjackLogic(){
		top = 0;
		this.players = new Player[2];
		rounds = 1;
		turn = -1;
		this.endResults = new StringBuilder("This is the end results\n");
		deck = Deck.getInstance();
		deck.shuffle();
	}
	/**
	 * Constructor has input for number of players.
	 * @param i number of players
	 */
	public BlackjackLogic(int players){
		top = 0;
		this.players = new Player[players];
		rounds = 1;
		turn = -1;
		this.endResults = new StringBuilder("This is the end results\n");
		deck = Deck.getInstance();
		for(int i = 3; players>=i;i+=2)
			deck.addDeck();
		deck.shuffle();
	}
	/**
	 * Adds a player to the game.
	 * @param player
	 */
	public void addPlayer(Player player){
		players[top++] = player;
		turn++;
	}
	/**
	 * Return the player in the array at spot i.
	 * @param i place in array
	 * @return Player
	 */
	public Player getPlayer(){
		return this.players[1];
	}
	/**
	 * Return the player in the array at spot i.
	 * @param i place in array
	 * @return Player
	 */
	public Player getDealer(){
		return this.players[0];
	}
	/**
	 * Return the player in the array at spot i.
	 * @param i place in array
	 * @return Player
	 */
	public Player getPlayer(int i){
		if(i<top)
			return this.players[i];
		return null;
	}
	/**
	 * Returns the number of players.
	 * @return number of players
	 */
	public int getPlayers(){
		return top;
	}
	/**
	 * Deals 1 card from the deck to each player.
	 * @param deck deals card to each player
	 */
	public void dealHand(Deck deck){
		for(int i = 0; i < top; i ++){
			players[i].receiveCard(deck.dealCard());
		}
	}
	/**
	 * Run at the end of a round before resetting the hands of all players.
	 * Will check each player hand against the dealer and pay out accordingly.
	 */
	public void payTheWinners(){
		if(getDealer().hasBlackjack()){
			for(int i = 1; i<getPlayers();i++){
				if(getPlayer(i).hasBlackjack())
					getPlayer(i).addChips(getPlayer(i).getBet());
			}
			// Start next round.
			return;
		}
		for(int i = top-1; i >0;i--){
			if(players[i].getHandValue()<=21){
				//player wins
				if(players[i].hasBlackjack())
					players[i].addChips((int)(getPlayer(i).getBet()*2.5));
				else if(getPlayer(i).getHandValue() > getDealer().getHandValue() || getDealer().getHandValue()>21)
					getPlayer(i).addChips(getPlayer(i).getBet()*2);
				//push
				else if(getPlayer(i).getHandValue() == getDealer().getHandValue())
					getPlayer(i).addChips(getPlayer(i).getBet());
			}
			if(getPlayer(i).getSplitHandValue()<=21 && getPlayer(i).hasSplitHand()){
				//player wins
				if(getPlayer(i).hasSplitHandBlackjack())
					getPlayer(i).addChips((int)(getPlayer(i).getBet()*2.5));
				else if(getPlayer(i).getSplitHandValue() > getDealer().getHandValue() || getDealer().getHandValue()>21)
					getPlayer(i).addChips(getPlayer(i).getBet()*2);
				//push
				else if(getPlayer(i).getSplitHandValue() == getDealer().getHandValue())
					getPlayer(i).addChips(getPlayer(i).getBet());
			}
			if(players[1].getDoubleDown()){
				if(players[i].getHandValue()<=21){
					//player wins
					if(players[i].hasBlackjack())
						players[i].addChips((int)(getPlayer(i).getBet()*2.5));
					else if(getPlayer(i).getHandValue() > getDealer().getHandValue() || getDealer().getHandValue()>21)
						getPlayer(i).addChips(getPlayer(i).getBet()*2);
					//push
					else if(getPlayer(i).getHandValue() == getDealer().getHandValue())
						getPlayer(i).addChips(getPlayer(i).getBet());
				}
				if(getPlayer(i).getSplitHandValue()<=21 && getPlayer(i).hasSplitHand()){
					//player wins
					if(getPlayer(i).hasSplitHandBlackjack())
						getPlayer(i).addChips((int)(getPlayer(i).getBet()*2.5));
					else if(getPlayer(i).getSplitHandValue() > getDealer().getHandValue() || getDealer().getHandValue()>21)
						getPlayer(i).addChips(getPlayer(i).getBet()*2);
					//push
					else if(getPlayer(i).getSplitHandValue() == getDealer().getHandValue())
						getPlayer(i).addChips(getPlayer(i).getBet());
				}
				
			}
		}
	}
	/**
	 * Has each player discard their current hand.
	 */
	public void discardHands(){
		for(int i = 0; i < top; i ++){
			players[i].discardHand();
		}
	}
	public static void endTurn(){
		if(turn == 0){
			turn = top-1;
			rounds++;
		}
		else
			turn--;
	}
	/**
	 * Returns the player whose turn it is.
	 * @return
	 */
	public Player getTurn(){
		return this.getPlayer(turn);
	}
	/**
	 * Returns the number of hands played.
	 * @return number of hands played
	 */
	public int getRounds(){
		return this.rounds;
	}
	/**
	 * Builds a String representation of each player's hand.
	 * @return
	 */
	public String allHandstoString(){
		StringBuilder s = new StringBuilder("");
		s.append("This is hand: " + this.rounds+" There are "+Deck.getInstance().cardsLeft()+" cards left in deck." + "\n");
		for(int i = 0; i < top; i ++){
			if(players[i].getName().equals("Dealer"))
				s.append(players[i].getName() +"\t"+ players[i].getHand() +"\tHand Value:"+ players[i].getHandValue() +"\t"+"\n");
			else{
				s.append(players[i].getName() +"\t"+ players[i].getHand() +"\tHand Value:"+ players[i].getHandValue());
				if(players[1].hasSplitHand())
					s.append("\tSplit Hand Value:"+ players[i].getSplitHandValue());
				s.append("\tChips:"+ players[i].getChips()+"\n");
			}
		}
		return s.toString();
	}
	public String toString(){
		return this.endResults.toString();
	}
	/**
	 * Removes any players that do not have enough chips to place their bet.
	 */
	public void removeDeadPlayers(){
		for(int player = top-1; player>0; player--){
			if(players[player].getChips()-players[player].getBet() < 0){
				endResults.append(players[player].toString() + "\n");
				top--;
				for(int i=player; i < top; i++){
					players[i] = players[i+1];
				}
			}
		}
	}
	/**
	 * Checks to see if at least 1 player has enough chips for the next hand.
	 * @return
	 */
	public boolean someoneHasChips(){
		if(this.getPlayers()>1)
			return true;
		return false;
	}
	/*
	 * Checks current player to see if they are npc. If so, takes their turn.
	 * Does not end the turn.
	 */
	public void npcTurn(){
		if(getTurn().getHandValue()<21){
			if(getTurn().canSplit() && getTurn() != getDealer())
				getTurn().splitHand();
			while(getTurn().getHandValue()<17)
				getTurn().receiveCard(deck.dealCard());
			while(getTurn().getSplitHandValue()<17 && getTurn().hasSplitHand())
				getTurn().receiveSplitHandCard(deck.dealCard());
		}
	}
	// Runs the game.
	/*public static void main(String args[]){
		// Initialize deck and players.
		BlackjackLogic thisGame = new BlackjackLogic(5);
		thisGame.addPlayer(new Player());
		thisGame.addPlayer(new Player("Ted",true));
		thisGame.getPlayer(1).setBet(200);
		thisGame.addPlayer(new Player("Kris",true));
		thisGame.getPlayer(2).setBet(300);
		thisGame.addPlayer(new Player("Connor",true));
		thisGame.addPlayer(new Player("Kailab",true));
		thisGame.getPlayer(4).setBet(500);
		// Sets number of decks to increase by 1 for every 3 players.
		
		// Tracks minimum number of cards in deck at any point.
		int deckMinimum = 52*deck.getDecks();
		// Game loop.
		do{
			// Resets everyone's hands.
			thisGame.discardHands();
			//Reshuffle deck every 5 hands.
			if(thisGame.getRounds()%5 == 0){
				deck.shuffle();
			}
			// Here is where the GUI should ask to increase or decrease bet. Disable increase bet button if it will reduce chips below 0.
			//Check bet for each player and remove chips.
			for(int i = 1; i<thisGame.getPlayers();i++){
				thisGame.getPlayer(i).removeChips(thisGame.getPlayer(i).getBet());
			}
			// Deal two cards to each player.
			thisGame.dealHand(deck);
			thisGame.dealHand(deck);
			// Initial printout of everyone's cards.
			System.out.println(thisGame.allHandstoString());
			// If dealer has Blackjack, ends round. Pushes if player has Blackjack.
			if(thisGame.getPlayer(0).hasBlackjack()){
				for(int i = 1; i<thisGame.getPlayers();i++){
					if(thisGame.getPlayer(i).hasBlackjack())
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet());
				}
				// Start next round.
				continue;
			}
			// Each player plays their round.
			// Starts with last player and stops at dealer.
			// Every player has to be NPC for now. No inputs yet.
			for(int i = thisGame.getPlayers()-1; i >=0;i--){
				// Prevents hitting if hand value is 21 (or higher).
				if(thisGame.getPlayer(i).getHandValue()<21){
					// NPC logic.
					if(thisGame.getPlayer(i).isNPC())
						if(thisGame.getPlayer(i).canSplit())
							thisGame.getPlayer(i).splitHand();
						while(thisGame.getPlayer(i).getHandValue()<17){
							thisGame.getPlayer(i).receiveCard(deck.dealCard());
							deckMinimum = deckMinimum>deck.cardsLeft() ? deck.cardsLeft() : deckMinimum;
						}
						while(thisGame.getPlayer(i).getSplitHandValue()<17 && thisGame.getPlayer(i).hasSplitHand()){
							thisGame.getPlayer(i).receiveSplitHandCard(deck.dealCard());
							deckMinimum = deckMinimum>deck.cardsLeft() ? deck.cardsLeft() : deckMinimum;
						}
					// First check if can split, implement split logic.
					// Check if can double down, implement double down logic.
					// This is where the interaction with the GUI hit / stay should go.
				}
			}
			
			//check each player's hand against the dealer's hand and give out chips if they won.
			for(int i = thisGame.getPlayers()-1; i >0;i--){
				if(thisGame.getPlayer(i).getHandValue()<=21){
					//player wins
					if(thisGame.getPlayer(i).hasBlackjack())
						thisGame.getPlayer(i).addChips((int)(thisGame.getPlayer(i).getBet()*2.5));
					else if(thisGame.getPlayer(i).getHandValue() > thisGame.getPlayer(0).getHandValue() || thisGame.getPlayer(0).getHandValue()>21)
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet()*2);
					//push
					else if(thisGame.getPlayer(i).getHandValue() == thisGame.getPlayer(0).getHandValue())
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet());
				}
				if(thisGame.getPlayer(i).getSplitHandValue()<=21 && thisGame.getPlayer(i).hasSplitHand()){
					//player wins
					if(thisGame.getPlayer(i).hasSplitHandBlackjack())
						thisGame.getPlayer(i).addChips((int)(thisGame.getPlayer(i).getBet()*2.5));
					else if(thisGame.getPlayer(i).getSplitHandValue() > thisGame.getPlayer(0).getHandValue() || thisGame.getPlayer(0).getHandValue()>21)
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet()*2);
					//push
					else if(thisGame.getPlayer(i).getSplitHandValue() == thisGame.getPlayer(0).getHandValue())
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet());
				}
			}
			// After everyone plays their hand, prints the hands.
			System.out.println(thisGame.allHandstoString());
			//Remove players without enough chips.
			thisGame.removeDeadPlayers();			
		}while(thisGame.someoneHasChips());
		System.out.println("Deck minimum cards at any point: " + deckMinimum);
		System.out.println(thisGame.toString());
	}*/
}