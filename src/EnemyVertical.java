import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyVertical extends GameObject{

	private Handler handler;
	private int time;
	
	public EnemyVertical(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velY = 1;
		this.time = 0;
		this.s = new Sound("laser.wav", 1);
	}

	@Override
	public void tick() {
		int player_y = handler.getPlayer2().getY();
		
		if(player_y < this.y){
			y -= velY;
		} else if (player_y > y){
			y += velY;
		}
		
		if(time == 100){
			time = 0;
			if(handler.getPlayer2().getX() < x){
				handler.addObject(new EnemyBullet(x + 8,y + 8,ID.EnemyBullet, -4, 0, handler));
			} else {
				handler.addObject(new EnemyBullet(x + 8,y + 8,ID.EnemyBullet, 4, 0, handler));
			}
		} else {
			time++;
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
