
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;


public class Game extends Canvas implements Runnable {

	// automatically generated, not to sure why
	private static final long serialVersionUID = 1222540838952399849L;

	//size of our game window
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9; 
															
	private Thread thread;
	private boolean running = false;

	private Random r;
	private Handler handler;
	private Spawn spawner;
	private KeyInput keyinput;
	private Menu menu;
	
	public enum STATE {
		MainMenu,
		Help,
		Game,
		PlayerMenu,
		LevelTypeMenu,
		End
	};
	
	//create enum (gameState) and set it to Menu
	public static STATE gameState = STATE.MainMenu;

	/**
	 * Game constructor
	 */
	public Game() {
		//Creates a new Handler. Handler is the class that "handles" all gameObjects
		this.handler = new Handler();
		//Creates a new Spawner, The Spawner can add and remove gameObjects from the handler
		this.spawner = new Spawn(handler, this);
		
		//create Menu class
		this.menu = new Menu(this, handler);
		//needed for the menu class as we need to "listen" when the user clicks 
		this.addMouseListener(menu);
		
		//creates a random number
		r = new Random();
		
		if(gameState == STATE.Game){
			spawner.levelLoader(spawner.currentLevelNumber, handler);
		} else {
			
			//cool background animations
		}
		
		// Set Key Input
		this.setKeyinput(new KeyInput(handler));
		// add the Key Input so it actually registers
		this.addKeyListener(getKeyinput());
		
		//Create Window for the game
		new Window(WIDTH, HEIGHT, "Comp2911", this);
	}
	
	/**
	 * auto generated.
	 * only runs during start up.
	 * sets this Thread to this
	 * turns running into true (we just started our game)
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	/**
	 * this is the same as clicking the close button
	 * turns running to false, so it no longer runs
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The Run function
	 * Calculates the FPS and calls the render function 
	 * if it is running
	 * this function runs continuously until running = false
	 */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int sleepCounter = 10;
		int targetFPS = 60;
		while (running) {
			
			try {
				Thread.sleep(sleepCounter);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				System.out.println(gameState);
				System.out.println(keyinput.currentSteps);
				if(frames > targetFPS){
					sleepCounter++;
				} else if (frames < targetFPS){
					sleepCounter--;
				}
				frames = 0;
			}
		}
		stop();
	}

	/**
	 * runs every frame,
	 * you can think of it as a tick for a clock
	 * basically updates the game
	 */
	private void tick() {
		
		if(gameState == STATE.Game){
			handler.tick();
			spawner.tick();
		} else if (gameState == STATE.MainMenu){
			menu.tick();
		}
	}

	/**
	 * draws images onto the screen
	 * runs once every frame as well
	 * heads up: images that are drawn last will overlap images that are drawn first
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		
		
		if(gameState == STATE.Game){
			handler.render(g);
		} else if(gameState == STATE.MainMenu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.PlayerMenu || gameState == STATE.LevelTypeMenu){
			menu.render(g);
		} 

		g.dispose();
		bs.show();
	}

	/**
	 * clamps a variable between a maximum and a minimum
	 * @param var
	 * @param max
	 * @param min
	 * @return var between max and min
	 */
	public static int clamp(int var, int max, int min) {
		if (var >= max)
			return max;
		else if (var <= min)
			return min;
		else
			return var;
	}

	/**
	 * main method, creates new game
	 * @param args
	 */
	public static void main(String args[]) {
		new Game();
	}
	
	
	


	// getters and setters functions
	public KeyInput getKeyinput() {
		return keyinput;
	}

	public void setKeyinput(KeyInput keyinput) {
		this.keyinput = keyinput;
	}
	
	public boolean IsgameStateMenu(){
		if(gameState == STATE.MainMenu){
			return true;
		}
		return false;
	}
	
	public boolean IsgameStateHelp(){
		if(gameState == STATE.Help){
			return true;
		}
		return false;
	}
	
	public boolean IsgameStateGame(){
		if(gameState == STATE.Game){
			return true;
		}
		return false;
	}
	
	public boolean IsgameStateEnd(){
		if(gameState == STATE.End){
			return true;
		}
		return false;
	}
	
	public boolean IsgameStatePlayerMenu(){
		if(gameState == STATE.PlayerMenu){
			return true;
		}
		return false;
	}
	
	public boolean isgameStateLevelTypeMenu(){
		if(gameState == STATE.LevelTypeMenu){
			return true;
		}
		return false;
	}
	
	public void setgameState(String state){
		if(state.equals("Help")){
			gameState = STATE.Help;
		} else if(state.equals("Game")){
			gameState = STATE.Game;
		} else if (state.equals("MainMenu")){
			gameState = STATE.MainMenu;
		} else if (state.equals("End")){
			gameState = STATE.End;
		} else if (state.equals("PlayerMenu")){
			gameState = STATE.PlayerMenu;
		} else if (state.equals("LevelTypeMenu")){
			gameState = STATE.LevelTypeMenu;
		}
	}
	
	public Spawn getSpawner(){
		return this.spawner;
	}

	public STATE getGameState() {
		return gameState;
	}

	public void setGameState(STATE gameState) {
		Game.gameState = gameState;
	}
}
