import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Spawn {
	private Handler handler;
	private Game game;
	public int currentLevelNumber;
	

	public Spawn(Handler handler, Game game){
		this.handler = handler;
		this.game = game;
		this.currentLevelNumber = 1;	
	}
	
	/**
	 * checks if all boxes are on goal squares
	 */
	public void tick(){
		ArrayList<GameObject> GoalSquares = handler.getGoalSquares();
		ArrayList<GameObject> Boxes = handler.getBoxes();

		int i = 0;
		for(GameObject g :  GoalSquares){
			for(GameObject g1 : Boxes){
				if(g1.getX() == g.getX() && g1.getY() == g.getY()){
					i++;
				}
			}
		}
		if(i == GoalSquares.size() && i != 0){
			System.out.println("You Win!");
			while(handler.object.size() != 0){
				handler.removeObject(handler.object.get(0));
			}
			
			game.removeKeyListener(game.getKeyinput());
			currentLevelNumber++;
			levelLoader(currentLevelNumber, handler);
			
			
			game.setKeyinput(new KeyInput(handler));
			game.addKeyListener(game.getKeyinput());
		}
	}
	
	/**
	 * test level
	 * @param handler
	 */
	public void loadtest(Handler handler){
		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2));
		
		handler.addObject(new Wall(32 * 8, 32 * 8, ID.Wall));
	}
	
	/**
	 * loads the corresponding level
	 * @param levelNumber the level to be loaded
	 * @param handler the current handler
	 */
	public void levelLoader (int levelNumber, Handler handler) {
		if (levelNumber == 1) {
			loadLevel1(handler);
		} else if (levelNumber == 2) {
			loadLevel2(handler);
		} else if (levelNumber == 3) {
			loadLevel3(handler);
		}
	}

	/**
	 * hard coded to load level1
	 * @param handler
	 */
	public void loadLevel1(Handler handler){
		handler.addObject(new GoalSquare(32 * 1, 32 * 5, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 1, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 2, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 4, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 3, 32 * 6, ID.GoalSquare, handler));
		
		handler.addObject(new Player(32 * 1, 32 * 2, ID.Player2));
		
		handler.addObject(new Box(32 * 2, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 3, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 5, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 4, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 6, ID.Box));
		
		for(int i = 0; i < 6; i++){
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 7, ID.Wall));
		}
		for(int i = 1; i < 7; i++){
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 5, 32 * i, ID.Wall));
		}
		for(int i = 1; i < 4 ; i++){
			handler.addObject(new Wall(32 * 4, 32 * i, ID.Wall));
		}
		
		handler.addObject(new Wall(32 * 1, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 4, ID.Wall));
	}
	
	/**
	 * hard coded, 
	 * loads level two
	 * @param handler
	 */
	public void loadLevel2(Handler handler){
		
		for(int i = 3; i < 6; i++){
			handler.addObject(new GoalSquare(32 * 7, 32 * i, ID.GoalSquare, handler));
		}
		
		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2));
		
		handler.addObject(new Box(32 * 2, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 3, ID.Box));
		
		for(int i = 0; i < 5; i++){
			handler.addObject(new Wall(0, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 1, 32 * 0, ID.Wall));
		for(int i = 4; i < 9; i++){
			handler.addObject(new Wall(32 * 1, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 2, 32 * 0, ID.Wall));
		for(int i = 4; i < 6; i++){
			handler.addObject(new Wall(32 * 2, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 2, 32 * 8, ID.Wall));
		
		handler.addObject(new Wall(32 * 3, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 8, ID.Wall));
		
		for(int i = 0; i < 5; i++){
			handler.addObject(new Wall(32 * 4, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 4, 32 * 8, ID.Wall));
		
		handler.addObject(new Wall(32 * 5, 32 * 4, ID.Wall));
		for(int i = 6; i < 9; i++){
			handler.addObject(new Wall(32 * 5, 32 * i, ID.Wall));
		}
		
		for(int i = 2; i < 5; i++){
			handler.addObject(new Wall(32 * 6, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 6, 32 * 7, ID.Wall));
		
		handler.addObject(new Wall(32 * 7, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 7, ID.Wall));
		
		for(int i = 2; i < 8; i++){
			handler.addObject(new Wall(32 * 8, 32 * i, ID.Wall));
		}
		
	}
	
	/**
	 * hard coded, 
	 * loads level three
	 * @param handler
	 */
	public void loadLevel3(Handler handler){
		for (int i = 0; i < 12; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
		}
		for (int i = 1; i < 7; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
		}
		for (int i = 1; i < 6; i++) {
			handler.addObject(new Wall(32 * i, 32 * 6, ID.Wall));
		}
		for (int i = 1; i < 4; i++) {
			handler.addObject(new Wall(32 * 2, 32 * (6+i), ID.Wall));
		}
		
		
	}
}
