
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;


public class Player extends GameObject {

	Random r = new Random();
	private Handler handler;
	public boolean playerHit;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.playerHit = false;
	}

	public void tick() {
		//x += velX;
		//y += velY;
		
		//x = Game.clamp(x, Game.WIDTH - 44, 0);
		//y = Game.clamp(y, Game.HEIGHT - 72, 0);
		collision();
	}
	
	private void collision(){
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getID() == ID.EnemyBullet) {
				if (getBounds().intersects(tempObject.getBounds())) {
					playerHit = true;
				}
			}
		}
	}

	/**
	 * draws the player
	 */
	public void render(Graphics g) {
		if (id == ID.Player) {
			g.setColor(Color.white);
		} else if (id == ID.Player2) {
			g.setColor(Color.blue);
		}
		
		Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("player1.png");
        g.drawImage(i, x, y, this);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}
	


}
