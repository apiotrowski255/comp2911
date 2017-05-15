import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Wall extends GameObject{

	public Wall(int x, int y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * loads image to draw
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(Color.darkGray);
//		g.fillRect(x, y, 32, 32);
		Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("wall.png");
        g.drawImage(i, x, y, this);
	}

}
