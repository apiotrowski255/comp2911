
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1222540838952399849L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9; 
															
	private Thread thread;
	private boolean running = false;

	private Random r;
	private Handler handler;
	
	public enum STATE {
		Menu,
		Help,
		Game,
		End
	};
	
	public static STATE gameState = STATE.Menu;

	public Game() {
		this.handler = new Handler();
		
		r = new Random();

		loadLevel1(handler);
		
		
		this.addKeyListener(new KeyInput(handler));

		new Window(WIDTH, HEIGHT, "Comp2911", this);


	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
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
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		handler.tick();
		
		ArrayList<GameObject> GoalSquares = handler.getGoalSquares();
		ArrayList<GameObject> Boxes = handler.getBoxes();

		int i = 0;
		for(GameObject g :  GoalSquares){
			for(GameObject g1 : Boxes){
				if(g1.getX() == g.getX() && g1.getY() == g.getY()){
					i++;
				}
			}
		}
		if(i == GoalSquares.size() && i != 0){
			System.out.println("You Win!");
			handler.object.clear();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		g.dispose();
		bs.show();
	}

	public static int clamp(int var, int max, int min) {
		if (var >= max)
			return max;
		else if (var <= min)
			return min;
		else
			return var;
	}

	public static void main(String args[]) {
		new Game();
	}
	
	public static void loadLevel1(Handler handler){
		handler.addObject(new GoalSquare(32 * 1, 32 * 5, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 1, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 2, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 4, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 3, 32 * 6, ID.GoalSquare, handler));
		
		handler.addObject(new Player(32 * 1, 32 * 2, ID.Player2));
		
		handler.addObject(new Box(32 * 2, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 3, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 5, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 4, ID.Box));
		
		for(int i = 0; i < 6; i++){
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 7, ID.Wall));
		}
		for(int i = 1; i < 7; i++){
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 5, 32 * i, ID.Wall));
		}
		for(int i = 1; i < 4 ; i++){
			handler.addObject(new Wall(32 * 4, 32 * i, ID.Wall));
		}
		
		handler.addObject(new Wall(32 * 1, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 4, ID.Wall));
		handler.addObject(new Box(32 * 3, 32 * 6, ID.Box));
	}
}
