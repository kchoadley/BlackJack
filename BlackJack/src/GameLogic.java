
public class GameLogic {

	private static Thread thread;
	private static boolean running;
	private static int FPS = 60;
	private static long targetTime = 1000/FPS;
	private static BlackJackGUI game;
	
	public static int countDown = 1000;

	public static void main(String[] args){
		game = new BlackJackGUI();
		game.setSize(400,600);
		running = true;
		run();
		
	}
	public static void run(){
		
		long start;
		long elapsed;
		long wait;
		
		while(running){
			
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
				
				game.resetHit();
			}
			else{
				
			}
			//when player has stayed
			if(game.getStay()){
				
				game.resetHit();
			}
			else{
				
			}
			//when player has split
			if(game.getSplit()){
				
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
			if(true){
				game.DisableDecrease();
			}
			else{
				game.EnableDecrease();
			}
			
			
			//logic so player can't increase bet passed their chip count
			if(true){
				game.DisableIncrease();
			}
			else{
				game.EnableIncrease();
			}
			
			//logic for allowing player to split
			if(true){
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
		countDown -= 1;
		game.setChips(Integer.toString(countDown));
	}
}
