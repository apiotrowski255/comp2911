import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter{

	private Game game;
	private Handler handler;
	private String gameStateWord;
	private int bouncer;
	private boolean startFromTop;
	
	public Menu(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
		this.gameStateWord = null;
		this.bouncer = 0;
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(game.IsgameStateMenu()){
			//playButton
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				gameStateWord = "PlayerMenu";
				
			}
			
			//help button, is Mouse on the help button
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				gameStateWord = "Help";
			}
			
			// quit button, is Mouse on the quit button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				System.exit(1);
			}
		}
		
		if(game.IsgameStatePlayerMenu()){
			//single player button
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				gameStateWord = "LevelTypeMenu";
			}
			
			//2 player button
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().levelLoader(game.getSpawner().currentLevelNumber, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			// back button, is Mouse on the button button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				gameStateWord = "MainMenu";
			}
			
		}
		if(game.isgameStateLevelTypeMenu()){
			//Level select button
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				gameStateWord = "LevelSelectMenu";
			}
			
			//Random button
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().levelLoader(game.getSpawner().currentLevelNumber, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			// back button, is Mouse on the button button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				gameStateWord = "PlayerMenu";
			}
		}
		
		if(game.isgameStateLevelSelectMenu()){
			
			if(mouseOver(mx, my, 300, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(1);
				game.getSpawner().levelLoader(1, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 350, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(2);
				game.getSpawner().levelLoader(2, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 400, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(3);
				game.getSpawner().levelLoader(3, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 450, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(4);
				game.getSpawner().levelLoader(4, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			
			//back button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				gameStateWord = "LevelTypeMenu";
			}
		}
		
		//back button for help
		if(game.IsgameStateHelp()){
			if(mouseOver(mx, my, 210, 350, 200, 64)){
				gameStateWord = "MainMenu";
				return;
			}
		}
	}
	
	/**
	 * helper function, determines whether mouse is in box
	 * @param mx, mouse x
	 * @param my, mouse y
	 * @param x , x position of box
	 * @param y, y position of box
	 * @param width of the box
	 * @param height of the box
	 * @return
	 */
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
		if(!(gameStateWord == null)){
			game.setgameState(gameStateWord);
		}
	}
	
	
	
	public void tick(){
		
		if(bouncer < 10 && !startFromTop){
			bouncer++;
		} else {
			startFromTop = true;
			if(bouncer > 0){
				bouncer--;
			} else {
				startFromTop = false;
			}
		}
		
	}
	
	/**
	 * draw the graphics
	 * @param g
	 */
	public void render(Graphics g){
		if (game.IsgameStateMenu()) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);

			g.setFont(fnt2);
			g.drawRect(210 - bouncer/2, 150 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Play", 270, 190);

			g.drawRect(210 - bouncer/2, 250 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Help", 270, 290);

			g.drawRect(210 - bouncer/2, 350 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Quit", 270, 390);
		} else if (game.IsgameStateHelp()) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Help", 240, 70);
			
			g.setFont(fnt3);
			g.drawString("Welcome to the Game Sokoban!", 150, 100);
			g.drawString("Your objective is to move the boxes to the goal Square", 50, 125);
			g.drawString("Sounds easy right? but there are some rules", 90, 150);
			g.drawString("You (and the box) can only move horizontally and vertically", 30, 175);
			g.drawString("Even if all your might, you can only move one box at a time", 30, 200);
			
			g.drawString("i need help too", 240, 300);
			g.drawString("In Single Player mode, use the arrow keys to move", 70, 250);
			g.drawString("In Two Player mode, use the wasd keys to move player 2", 40, 275);
			
			
			g.setFont(fnt2);
			g.drawRect(210 - bouncer/2, 350 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Back", 270, 390);
		} else if (game.IsgameStatePlayerMenu()){
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Single Player", 217, 190);

			g.drawRect(210, 250, 200, 64);
			g.drawString("2 Player", 255, 290);

			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		} else if (game.isgameStateLevelTypeMenu()){
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 25);


			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Select Level", 223, 190);

			g.setFont(fnt3);
			g.drawRect(210, 250, 200, 64);
			g.drawString("Random Level", 225, 290);

			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		} else if (game.isgameStateLevelSelectMenu()){
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 25);
			Font fnt4 = new Font("arial", 1, 25);
			
			g.setFont(fnt4);
			g.setColor(Color.white);
			g.drawString("1", 310, 175);
			g.drawString("2", 360, 175);
			g.drawString("3", 410, 175);
			g.drawString("4", 460, 175);
			
			g.drawString("5", 310, 225);
			g.drawString("6", 360, 225);
			g.drawString("7", 410, 225);
			g.drawString("8", 460, 225);
			
			g.drawString("9", 310, 275);
			g.drawString("10", 353, 275);
			g.drawString("11", 403, 275);
			g.drawString("12", 452, 275);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);
			
			g.setFont(fnt3);
			g.drawString("Levels", 350, 130);
			
			g.drawString("Easy", 200, 175);
			g.drawRect(350, 150, 32, 32);
			g.drawRect(400, 150, 32, 32);
			g.drawRect(450, 150, 32, 32);
			g.drawRect(300, 150, 32, 32);
			
			g.drawString("Medium", 200, 225);
			g.drawRect(350, 200, 32, 32);
			g.drawRect(400, 200, 32, 32);
			g.drawRect(450, 200, 32, 32);
			g.drawRect(300, 200, 32, 32);
			
			g.drawString("Hard", 200, 275);
			g.drawRect(350, 250, 32, 32);
			g.drawRect(400, 250, 32, 32);
			g.drawRect(450, 250, 32, 32);
			g.drawRect(300, 250, 32, 32);
			
			
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		}
	}
}
