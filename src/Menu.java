import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter{

	private Game game;
	private Handler handler;
	
	public Menu(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(game.IsgameStateMenu()){
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				game.setgameState("Game");
				game.removeKeyListener(game.getKeyinput());
				game.loadLevel2(handler);
				
				game.setKeyinput(new KeyInput(handler));
				game.addKeyListener(game.getKeyinput());
			}
			
			//help button
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				game.setgameState("Help");
			}
			
			// quit button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				System.exit(1);
			}
		}
		
		//back button for help
		if(game.IsgameStateHelp()){
			if(mouseOver(mx, my, 210, 350, 200, 64)){
				game.setgameState("Menu");
				return;
			}
		}
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if (game.IsgameStateMenu()) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 240, 70);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 270, 190);

			g.drawRect(210, 250, 200, 64);
			g.drawString("Help", 270, 290);

			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 270, 390);
		} else if (game.IsgameStateHelp()) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Help", 240, 70);
			
			g.setFont(fnt3);
			g.drawString("i need help too", 270, 290);
			g.drawString("use WASD keys to move player and dodge enemies", 50, 200);
			
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		} 
	}
}
