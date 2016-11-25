
public class GameLogic {
	private static Thread thread;
	private static boolean running;
	private static int FPS = 60;
	private static long targetTime = 1000/FPS;
	private static BlackJackGUI game;
	private static BlackjackLogic thisGame;
	private static Deck deck = Deck.getInstance();
	
	public static int countDown = 1000;

	public static void main(String[] args){
		thisGame = new BlackjackLogic();
		game = new BlackJackGUI();
		game.setSize(400,600);
		running = true;
		run();
		
	}
	public static void run(){
		thisGame.addPlayer(new Player());
		thisGame.addPlayer(new Player());
		thisGame.dealHand(deck);
		thisGame.dealHand(deck);
		game.setPlayerName(thisGame.getPlayer().getName());

		long start;
		long elapsed;
		long wait;
		
		while(running){
			if(thisGame.getTurn().isNPC()){
				if(thisGame.getTurn().getHandValue()<21){
					// NPC logic.
					if(thisGame.getTurn().isNPC())
						if(thisGame.getTurn().canSplit()&& thisGame.getTurn() != thisGame.getDealer())
							thisGame.getTurn().splitHand();
						while(thisGame.getTurn().getHandValue()<17){
							thisGame.getTurn().receiveCard(deck.dealCard());
						}
						while(thisGame.getTurn().getSplitHandValue()<17 && thisGame.getTurn().hasSplitHand()){
							thisGame.getTurn().receiveSplitHandCard(deck.dealCard());
						}
						BlackjackLogic.endTurn();
					// First check if can split, implement split logic.
					// Check if can double down, implement double down logic.
					// This is where the interaction with the GUI hit / stay should go.
				}
			}
			start = System.nanoTime();
			elapsed = System.nanoTime() - start;
			wait = (targetTime  - elapsed / 1000000 < 0 ? 5 : targetTime - elapsed / 1000000)*60;	//every second
			try{Thread.sleep(wait);}
			catch(Exception e){
				e.printStackTrace();
			}
			
			if (game.getDeal() == false) {
				//when player wants to increase bet
				//doing 100 for now, we can make custom in a later version
				
				game.DisableHit();
				game.DisableStay();
				game.DisableSplit();
				game.DisableDouble();
				
				game.EnableDecrease();
				game.EnableIncrease();
				
				update();
				
				if(game.getIncrease()){
					thisGame.getTurn().increaseBet(100);
					game.resetIncrease();
				}
				
				//when player wants to decrease bet
				if(game.getDecrease()){
					thisGame.getTurn().decreaseBet(100);
					game.resetDecrease();
				}
			}
			else {
				game.DisableDecrease();
				game.DisableIncrease();
				game.EnableHit();
				game.EnableStay();
				game.EnableSplit();
				game.EnableDouble();
				
				game.setPlayerCards(thisGame.getPlayer().getHand());
				
				//logic for allowing player to split
				if(thisGame.getPlayer(1).canSplit()){
					game.EnableSplit();	
				}
				else{
					game.DisableSplit();
				}
				
				//logic for allowing player to double
				if(thisGame.getPlayer(1).canDouble()){
					game.EnableDouble();	
				}
				else{
					game.DisableDouble();
				}
				
				//When player has hit
				if(game.getHit()){
					thisGame.getTurn().receiveCard(deck.dealCard());
					if(thisGame.getTurn().getHandValue()>21){
						BlackjackLogic.endTurn();
						thisGame.getPlayer(1).setStay();
					}
					game.resetHit();
				}
				else{
					
				}
				//when player has stayed
				//disable all buttons, set dealer's cards
				if(game.getStay() || thisGame.getPlayer(1).getHasStayed()){
					BlackjackLogic.endTurn();
					game.resetStay();
					game.DisablePlayer();
					game.DisableDouble();
					game.setDealerCards(thisGame.getDealer().getHand());
					thisGame.getPlayer(1).setStay();
				}
				else{
					
				}
				//when player has split
				if(game.getSplit()){
					thisGame.getTurn().increaseBet(100);
					game.resetHit();
				}
				else{
					
				}
				//when player has doubled
				if(game.getDouble()){
					thisGame.getTurn().receiveCard(deck.dealCard());
					game.resetDouble();
					thisGame.getTurn().increaseBet(thisGame.getTurn().getBet());
					thisGame.getPlayer(1).setStay();
					BlackjackLogic.endTurn();
				}
				else{
					
				}
				
				//if player has 'stayed' or ended their turn, then deal the dealer's cards
				if (thisGame.getPlayer(1).getHasStayed()){
					game.setDealerCards(thisGame.getDealer().getHand());
				}
				
//				if (game.getPlayAgain() == true){
//					game.resetPlayAgain();
//					game.resetDeal();
//					BlackjackLogic.endTurn();
//					thisGame.discardHands();
//					game.setPlayerCards("");
//					game.setDealerCards("");
//					thisGame.getPlayer(1).resetStay();
//				}
				
			}	
		}
	}
	
	//initialize game table
	private static void update(){
		game.setChips(Integer.toString(thisGame.getPlayer().getChips()));
		game.setBetAmount(Integer.toString(thisGame.getPlayer().getBet()));
	}
}
