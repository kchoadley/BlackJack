
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
			
			if(game.idkYet == true){
				game.setChips("Boolean is true");
			}
			else{
				update();
			}
			
			elapsed = System.nanoTime() - start;
			
			wait = (targetTime  - elapsed / 1000000 < 0 ? 5 : targetTime - elapsed / 1000000);
			
			try{Thread.sleep(wait);}
			catch(Exception e){
				e.printStackTrace();
			}
			
			if(game.idkYet == true){
				game.setChips("Boolean is true");
			}
			
		}
	}
	
	private static void update(){
		countDown -= 1;
		game.setChips(Integer.toString(countDown));
	}
}
