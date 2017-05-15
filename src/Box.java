import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Box extends GameObject{

	/**
	 * constructor
	 * @param x position
	 * @param y position
	 * @param id always id.box but it is possible to do id.wall if we want to trick players
	 */
	public Box(int x, int y, ID id) {
		super(x, y, id);

	}

	public void tick() {
		
		
	}

	/**
	 * uses the image box.gif to draw the image
	 */
	public void render(Graphics g) {
//		g.setColor(Color.red);
//		g.fillRect(x, y, 32, 32);
		Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("box.gif");
        g.drawImage(i, x, y, this);
	}

}
