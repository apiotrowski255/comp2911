import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Box extends GameObject {

	private boolean isOnGoal;

	/**
	 * constructor
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param id
	 *            always id.box but it is possible to do id.wall if we want to
	 *            trick players
	 */
	public Box(int x, int y, ID id) {
		super(x, y, id);
		this.theme = null;
		this.isOnGoal = false;
	}

	public void tick() {

	}

	/**
	 * uses the image box.gif to draw the image
	 */
	public void render(Graphics g) {
		// g.setColor(Color.red);
		// g.fillRect(x, y, 32, 32);
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = null;
		
		if (theme.equals("Mario") || theme == null) { // default
			if (!isOnGoal) {
				i = t.getImage("box.gif");
			} else {
				i = t.getImage("box1.png");
			}
		} else if (theme.equals("BombMan")) {
			i = t.getImage("box1.png");
		}

		g.drawImage(i, x, y, this);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean getIsOnGoal() {
		return this.isOnGoal;
	}

	public void setTheme(String Theme) {
		this.theme = Theme;
	}
	
	public void setIsOnGoal(boolean bool) {
		this.isOnGoal = bool;
	}
	
	

}
