import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class GoalSquare extends GameObject {

	private Handler handler;
	public boolean BoxOnGoal;

	public GoalSquare(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.BoxOnGoal = false;
	}

	@Override
	public void tick() {
		ArrayList<GameObject> boxes = handler.getBoxes();
		BoxOnGoal = false;
		for(GameObject g : boxes){
			if (g.getX() == x && g.getY() == y) {
				if (!BoxOnGoal) {
					BoxOnGoal = true;
				}
			} else {
				BoxOnGoal = false;
			}
		}
	}

	@Override
	public void render(Graphics g) {
//		g.setColor(Color.green);
//		g.fillRect(x, y, 32, 32);
		
		Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("goal.png");
        g.drawImage(i, x, y, this);

	}

	
	public boolean getBoxOnGoal(){
		return BoxOnGoal;
	}
}
