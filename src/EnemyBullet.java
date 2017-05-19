import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyBullet extends GameObject{
	private Handler handler;
	public EnemyBullet(int x, int y, ID id, int velX, int velY, Handler handler) {
		super(x, y, id);
		this.velX = velX;
		this.velY = velY;
		this.handler = handler;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		if(x < 0 || y < 0){
			handler.removeObject(this);
		}
		handler.addObject(new Trail(x,y,ID.Trail, Color.RED, 16,16,0.05f,handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,16,16);
	}

}
