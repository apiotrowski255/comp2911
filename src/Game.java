
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
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

	public Game() {
		this.handler = new Handler();
		
		r = new Random();

		// handler.addObject(new Player(0, 0, ID.Player));
		handler.addObject(new GoalSquare(128, 128, ID.GoalSquare, handler));
		
		handler.addObject(new Player(0, 0, ID.Player2));
		
		handler.addObject(new Box(64, 64, ID.Box));
		handler.addObject(new Box(32 * 10, 64, ID.Box));
		handler.addObject(new Wall(32 * 8, 32 * 8, ID.Wall));
		handler.addObject(new Wall(32 * 10, 32 * 8, ID.Wall));
		
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
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		handler.tick();
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
}
