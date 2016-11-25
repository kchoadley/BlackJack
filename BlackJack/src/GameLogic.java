
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
			
			update();
			
			
			
			//When player has hit
			if(game.getHit()){
				thisGame.getTurn().receiveCard(deck.dealCard());
				if(thisGame.getTurn().getHandValue()>21)
					BlackjackLogic.endTurn();
				game.resetHit();
			}
			else{
				
			}
			//when player has stayed
			if(game.getStay()){
				BlackjackLogic.endTurn();
				game.resetHit();
			}
			else{
				
			}
			//when player has split
			if(game.getIncrease()){
				thisGame.getTurn().increaseBet(100);
				game.resetHit();
			}
			else{
				
			}
			//when player has doubled
			if(game.getSplit()){
				
				game.resetHit();
			}
			else{
				
			}
			//when player wants to increase bet
			if(game.getSplit()){
				
				game.resetHit();
			}
			else{
				
			}
			//when player wants to decrease bet
			if(game.getSplit()){
				
				game.resetHit();
			}
			else{
				
			}
			
			
			
			
			
			
			
			//logic so player can't decrease bet once they have kept their bet
			if(thisGame.getTurn().firstHandShowing() && thisGame.getTurn().getBet()>0){
				game.EnableDecrease();
			}
			else{
				game.DisableDecrease();
			}
			
			
			//logic so player can't increase bet passed their chip count
			if(thisGame.getTurn().firstHandShowing() && thisGame.getTurn().getBet()<thisGame.getTurn().getChips()){
				game.EnableIncrease();
			}
			else{
				game.DisableIncrease();
			}
			
			//logic for allowing player to split
			if(thisGame.getPlayer(1).canSplit()){
				game.EnableSplit();	
			}
			else{
				game.DisableSplit();
			}
			
			//logic for allowing player to double
			if(true){
				game.EnableDouble();	
			}
			else{
				game.DisableDouble();
			}
			
			
			
			
			
			
		}
	}
	
	//not important
	private static void update(){
		game.setPlayerCards(thisGame.getPlayer().getHand());
		game.setDealerCards(thisGame.getDealer().getHand());
		game.setChips(Integer.toString(thisGame.getPlayer().getChips()));
		game.setBetAmount(Integer.toString(thisGame.getPlayer().getBet()));
	}
}
