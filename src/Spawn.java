import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Spawn {
	private Handler handler;
	private Game game;
	public int currentLevelNumber, numberOfSteps;

	public Spawn(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		this.currentLevelNumber = 0;
		this.numberOfSteps = 0;
	}

	/**
	 * checks if all boxes are on goal squares
	 */
	public void tick() {
		ArrayList<GameObject> GoalSquares = handler.getGoalSquares();
		ArrayList<GameObject> Boxes = handler.getBoxes();

		int i = 0;
		for (GameObject g1 : Boxes) {
			for (GameObject g : GoalSquares) {
			
				if (g1.getX() == g.getX() && g1.getY() == g.getY()) {
					i++;
					Box temp = (Box) g1;
					temp.setIsOnGoal(true);
					break;
				} else {
					Box temp = (Box) g1;
					temp.setIsOnGoal(false);
				}

			}
		}
		if (i == GoalSquares.size() && i != 0) {
			System.out.println("You Win!");
			
			game.setgameState("LevelInbetween");
			numberOfSteps = game.getKeyinput().getCurrentSteps();
			
			while (handler.object.size() != 0) {
				handler.removeObject(handler.object.get(0));
			}

			if(currentLevelNumber == 13 || currentLevelNumber == 12){
				handler.object.clear();
				game.setgameState("MainMenu");
				game.removeKeyListener(game.getKeyinput());
			} else {
				game.removeKeyListener(game.getKeyinput());
				currentLevelNumber++;
				levelLoader(currentLevelNumber, handler);
	
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
		}
		if (currentLevelNumber == 11 || currentLevelNumber == 12) {
			Player temp = (Player) handler.getPlayer2();
			if (temp.playerHit == true) {
				System.out.println("Hit");
				int currentLevel = game.getSpawner().getCurrentLevelNumber();
				handler.object.clear();
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().levelLoader(currentLevel, handler);
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());

			}
		}

	}

	/**
	 * test level
	 * 
	 * @param handler
	 */
	public void loadtest(Handler handler) {
		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2, handler));

		handler.addObject(new Wall(32 * 8, 32 * 8, ID.Wall));
	}

	/**
	 * loads the corresponding level
	 * 
	 * @param levelNumber
	 *            the level to be loaded
	 * @param handler
	 *            the current handler
	 */
	public void levelLoader(int levelNumber, Handler handler) {

		if (levelNumber == 1) {
			loadLevel1(handler);
		} else if (levelNumber == 2) {
			loadLevel2(handler);
		} else if (levelNumber == 3) {
			loadLevel3(handler);
		} else if (levelNumber == 4) {
			loadLevel4(handler);
		} else if (levelNumber == 5) {
			loadLevel5(handler);
		} else if (levelNumber == 6) {
			loadLevel6(handler);
		} else if (levelNumber == 7) {
			loadLevel7(handler);
		} else if (levelNumber == 8) {
			loadLevel8(handler);
		} else if (levelNumber == 9) {
			loadLevel9(handler);
		} else if (levelNumber == 10) {
			loadLevel10(handler);
		} else if (levelNumber == 11) {
			loadLevel11(handler);
		} else if (levelNumber == 12) {
			loadLevel12(handler);
		} else if (levelNumber == 13) {
			//2 player map
			loadLevel13(handler);
		} else if (levelNumber == 0) {
			loadLevel0(handler);
		}

		if (handler.getTheme() == 0) {
			for (GameObject b : handler.object) {
				if (b.getID() == ID.Box) {
					b.setTheme("Mario");
				} else if (b.getID() == ID.GoalSquare) {
					b.setTheme("Mario");
				} else if (b.getID() == ID.Player2) {
					b.setTheme("Mario");
				} else if (b.getID() == ID.Wall) {
					b.setTheme("Mario");
				}
			}
		} else if (handler.getTheme() == 1) {
			for (GameObject b : handler.object) {
				if (b.getID() == ID.Box) {
					b.setTheme("BombMan");
				} else if (b.getID() == ID.GoalSquare) {
					b.setTheme("BombMan");
				} else if (b.getID() == ID.Player2) {
					b.setTheme("BombMan");
				} else if (b.getID() == ID.Wall) {
					b.setTheme("BombMan");
				}
			}
		}
	}
	
	
	private void loadLevel12(Handler handler) {
		for (int i = 2; i < 17; i++) {
			handler.addObject(new Wall(32 * i, 32 * 1, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 12, ID.Wall));
		}

		for (int i = 1; i < 13; i++) {
			handler.addObject(new Wall(32 * 1, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 17, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 12, ID.Wall));

		for (int i = 3; i < 11; i += 2) {
			handler.addObject(new GoalSquare(32 * 14, 32 * i, ID.GoalSquare, handler));
		}
		for (int i = 3; i < 11; i += 2) {
			handler.addObject(new Box(32 * 4, 32 * i, ID.Box));
		}

		handler.addObject(new Player(32 * 9, 32 * 7, ID.Player2, handler));

		handler.addObject(new EnemyVertical(32 * 18, 32 * 13, ID.Enemy, handler));
		handler.addObject(new EnemyVertical(32 * 0, 32 * 0, ID.Enemy, handler));
		handler.addObject(new EnemyHorizontal(32 * 0, 32 * 13, ID.Enemy, handler));
		handler.addObject(new EnemyHorizontal(32 * 18, 32 * 0, ID.Enemy, handler));
		
		
	}

	/**
	 * hard coded for level 13
	 */

	public void loadLevel13(Handler handler){
		handler.addObject(new Player(32, 32, ID.Player, handler));
		handler.addObject(new Player(32, 32*4, ID.Player2, handler));
		
		for(int i = 0; i < 8; i++){
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			if(i != 4){
				handler.addObject(new Wall(32 * i, 32 * 3, ID.Wall));
			}
			handler.addObject(new Wall(32 * i, 32 * 6, ID.Wall));
		}
		
		for(int i = 1; i < 6; i++){
			if(i != 3){
				handler.addObject(new Wall(32 * 7, 32 * i, ID.Wall));
				handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			}
		}
		
		handler.addObject(new GoalSquare(32 * 6, 32 * 4, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 4, 32 * 5, ID.GoalSquare, handler));
		
		handler.addObject(new Box(32 * 4, 32 * 3, ID.Box));
		handler.addObject(new Box(32 * 4, 32 * 4, ID.Box));
	}
	
	/**
	 * hard coded to load level1
	 * 
	 * @param handler
	 */
	public void loadLevel1(Handler handler) {
		handler.addObject(new GoalSquare(32 * 1, 32 * 5, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 1, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 2, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 4, 32 * 6, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 3, 32 * 6, ID.GoalSquare, handler));

		handler.addObject(new Player(32 * 1, 32 * 2, ID.Player2, handler));

		handler.addObject(new Box(32 * 2, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 3, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 5, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 4, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 6, ID.Box));

		for (int i = 0; i < 6; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 7, ID.Wall));
		}
		for (int i = 1; i < 7; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 5, 32 * i, ID.Wall));
		}
		for (int i = 1; i < 4; i++) {
			handler.addObject(new Wall(32 * 4, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 4, ID.Wall));
	}

	/**
	 * hard coded, loads level two
	 * 
	 * @param handler
	 */
	public void loadLevel2(Handler handler) {

		for (int i = 3; i < 6; i++) {
			handler.addObject(new GoalSquare(32 * 7, 32 * i, ID.GoalSquare, handler));
		}

		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2, handler));

		handler.addObject(new Box(32 * 2, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 3, ID.Box));

		for (int i = 0; i < 5; i++) {
			handler.addObject(new Wall(0, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 1, 32 * 0, ID.Wall));
		for (int i = 4; i < 9; i++) {
			handler.addObject(new Wall(32 * 1, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 2, 32 * 0, ID.Wall));
		for (int i = 4; i < 6; i++) {
			handler.addObject(new Wall(32 * 2, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 2, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 3, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 8, ID.Wall));

		for (int i = 0; i < 5; i++) {
			handler.addObject(new Wall(32 * 4, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 4, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 5, 32 * 4, ID.Wall));
		for (int i = 6; i < 9; i++) {
			handler.addObject(new Wall(32 * 5, 32 * i, ID.Wall));
		}

		for (int i = 2; i < 5; i++) {
			handler.addObject(new Wall(32 * 6, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 6, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 7, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 7, ID.Wall));

		for (int i = 2; i < 8; i++) {
			handler.addObject(new Wall(32 * 8, 32 * i, ID.Wall));
		}

	}

	/**
	 * hard coded, loads level 7
	 * 
	 * @param handler
	 */
	public void loadLevel7(Handler handler) {

		// -- Load Walls
		// Outer Walls
		for (int i = 0; i < 12; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
		}
		for (int i = 1; i < 7; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
		}
		for (int i = 11; i < 14; i++) {
			handler.addObject(new Wall(32 * i, 32 * 1, ID.Wall));
		}
		for (int i = 1; i < 9; i++) {
			handler.addObject(new Wall(32 * 13, 32 * i, ID.Wall));
		}
		for (int i = 2; i < 14; i++) {
			handler.addObject(new Wall(32 * i, 32 * 9, ID.Wall));
		}
		for (int i = 1; i < 6; i++) {
			handler.addObject(new Wall(32 * i, 32 * 6, ID.Wall));
		}

		// Inner Walls
		for (int i = 0; i < 4; i++) {
			handler.addObject(new Wall(32 * 5, 32 * i, ID.Wall));
		}
		for (int i = 6; i < 10; i++) {
			handler.addObject(new Wall(32 * 2, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 5, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 8, ID.Wall));
		handler.addObject(new Wall(32 * 12, 32 * 5, ID.Wall));

		handler.addObject(new Wall(32 * 7, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 6, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 6, ID.Wall));

		for (int i = 7; i < 11; i++) {
			handler.addObject(new Wall(32 * i, 32 * 3, ID.Wall));
		}
		handler.addObject(new Wall(32 * 9, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 10, 32 * 4, ID.Wall));

		// -- Load goals
		for (int i = 1; i < 6; i++) {
			handler.addObject(new GoalSquare(32 * 1, 32 * i, ID.GoalSquare, handler));
		}

		for (int i = 1; i < 6; i++) {
			handler.addObject(new GoalSquare(32 * 2, 32 * i, ID.GoalSquare, handler));
		}

		// -- Load boxes
		handler.addObject(new Box(32 * 7, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 10, 32 * 2, ID.Box));

		handler.addObject(new Box(32 * 6, 32 * 3, ID.Box));

		handler.addObject(new Box(32 * 10, 32 * 5, ID.Box));

		handler.addObject(new Box(32 * 9, 32 * 6, ID.Box));
		handler.addObject(new Box(32 * 11, 32 * 6, ID.Box));

		handler.addObject(new Box(32 * 4, 32 * 7, ID.Box));
		handler.addObject(new Box(32 * 7, 32 * 7, ID.Box));
		handler.addObject(new Box(32 * 9, 32 * 7, ID.Box));
		handler.addObject(new Box(32 * 11, 32 * 7, ID.Box));

		// -- Load player
		handler.addObject(new Player(32 * 7, 32 * 4, ID.Player2, handler));

	}

	public void loadLevel8(Handler handler) {
		// load goals
		handler.addObject(new GoalSquare(32 * 9, 32 * 4, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 9, 32 * 5, ID.GoalSquare, handler));

		for (int i = 2; i < 9; i++) {
			handler.addObject(new Wall(0, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 2, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 2, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 3, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 4, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 7, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 8, ID.Wall));

		for (int i = 0; i < 4; i++) {
			handler.addObject(new Wall(32 * 5, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 5, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 5, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 6, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 7, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 7, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 8, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 9, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 9, 32 * 8, ID.Wall));

		handler.addObject(new Wall(32 * 10, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 10, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 10, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 10, 32 * 8, ID.Wall));

		for (int i = 0; i < 9; i++) {
			handler.addObject(new Wall(32 * 11, 32 * i, ID.Wall));
		}

		// boxes
		handler.addObject(new Box(32 * 2, 32 * 5, ID.Box));
		handler.addObject(new Box(32 * 4, 32 * 6, ID.Box));

		// -- Load Player
		handler.addObject(new Player(32 * 7, 32 * 6, ID.Player2, handler));
	}

	// this one is easy
	public void loadLevel9(Handler handler) {
		for (int i = 4; i < 8; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
		}

		for (int i = 1; i < 5; i++) {
			handler.addObject(new Wall(32 * 1, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 1, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 2, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 2, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 3, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 4, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 6, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 5, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 5, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 5, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 5, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 6, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 7, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 7, 32 * 7, ID.Wall));

		for (int i = 1; i < 8; i++) {
			handler.addObject(new Wall(32 * 8, 32 * i, ID.Wall));
		}

		handler.addObject(new GoalSquare(32 * 6, 32 * 3, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 6, 32 * 4, ID.GoalSquare, handler));

		handler.addObject(new Box(32 * 3, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 3, 32 * 3, ID.Box));

		handler.addObject(new Player(32 * 7, 32 * 6, ID.Player2, handler));

	}

	// Difficulty = Decently hard
	// Best moves / pushes = 89/33
	public void loadLevel10(Handler handler) {

		// -- Walls
		// Outer Walls
		for (int i = 0; i < 6; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
		}
		for (int i = 0; i < 3; i++) {
			handler.addObject(new Wall(32 * 6, 32 * i, ID.Wall));
		}
		for (int i = 2; i < 7; i++) {
			handler.addObject(new Wall(32 * 7, 32 * i, ID.Wall));
		}
		for (int i = 0; i < 4; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
		}
		for (int i = 2; i < 8; i++) {
			handler.addObject(new Wall(32 * i, 32 * 6, ID.Wall));
		}
		for (int i = 0; i < 7; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
		}
		for (int i = 3; i < 7; i++) {
			handler.addObject(new Wall(32 * 1, 32 * i, ID.Wall));
		}

		// Inner Walls
		handler.addObject(new Wall(32 * 3, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 3, ID.Wall));

		handler.addObject(new Wall(32 * 4, 32 * 5, ID.Wall));

		// -- Goals
		for (int i = 1; i < 4; i++) {
			handler.addObject(new GoalSquare(32 * i, 32 * 1, ID.GoalSquare, handler));
		}

		// -- Boxes
		handler.addObject(new Box(32 * 2, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 4, 32 * 2, ID.Box));

		handler.addObject(new Box(32 * 4, 32 * 4, ID.Box));

		// -- Player
		handler.addObject(new Player(32 * 3, 32 * 2, ID.Player2, handler));

	}

	// easy map
	// 37 moves with 5 pushes
	public void loadLevel3(Handler handler) {
		for (int i = 1; i < 6; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
		}
		handler.addObject(new Wall(32 * 1, 32 * 1, ID.Wall));
		for (int i = 5; i < 8; i++) {
			handler.addObject(new Wall(32 * 1, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 2, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 2, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 2, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 2, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 3, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 5, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 4, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 5, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 5, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 5, 32 * 6, ID.Wall));
		handler.addObject(new Wall(32 * 5, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 6, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 2, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 6, ID.Wall));

		for (int i = 2; i < 7; i++) {
			handler.addObject(new Wall(32 * 7, 32 * i, ID.Wall));
		}

		handler.addObject(new GoalSquare(32 * 5, 32 * 3, ID.GoalSquare, handler));

		handler.addObject(new Box(32 * 2, 32 * 2, ID.Box));

		handler.addObject(new Player(32 * 2, 32 * 6, ID.Player2, handler));
	}

	// Can be solved in 71 moves
	public void loadLevel4(Handler handler) {

		// outer walls
		for (int i = 1; i < 9; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 7, 32 * (i - 1), ID.Wall));
		}

		for (int i = 1; i < 5; i++) {
			handler.addObject(new Wall(32 * i, 32 * 1, ID.Wall));
			handler.addObject(new Wall(32 * (i + 2), 32 * 7, ID.Wall));
		}

		for (int i = 1; i < 4; i++) {
			handler.addObject(new Wall(32 * i, 32 * 8, ID.Wall));
			handler.addObject(new Wall(32 * (i + 3), 32 * 0, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 6, ID.Wall));

		handler.addObject(new GoalSquare(32 * 3, 32 * 3, ID.GoalSquare, handler));
		handler.addObject(new Box(32 * 4, 32 * 2, ID.Box));

		handler.addObject(new Player(32 * 3, 32 * 5, ID.Player2, handler));
	}

	// approx 120 moves to solve this one, but this is similar to level 4
	public void loadLevel5(Handler handler) {
		for (int i = 1; i < 9; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 7, 32 * (i - 1), ID.Wall));
		}

		for (int i = 1; i < 5; i++) {
			handler.addObject(new Wall(32 * i, 32 * 1, ID.Wall));
			handler.addObject(new Wall(32 * (i + 2), 32 * 7, ID.Wall));
		}

		for (int i = 1; i < 4; i++) {
			handler.addObject(new Wall(32 * i, 32 * 8, ID.Wall));
			handler.addObject(new Wall(32 * (i + 3), 32 * 0, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 6, ID.Wall));

		handler.addObject(new GoalSquare(32 * 3, 32 * 3, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 2, 32 * 3, ID.GoalSquare, handler));
		handler.addObject(new Box(32 * 4, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 3, ID.Box));

		handler.addObject(new Player(32 * 3, 32 * 5, ID.Player2, handler));
	}

	// can be solved in 131 moves
	public void loadLevel6(Handler handler) {
		for (int i = 1; i < 9; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 7, 32 * (i - 1), ID.Wall));
		}

		for (int i = 1; i < 5; i++) {
			handler.addObject(new Wall(32 * i, 32 * 1, ID.Wall));
			handler.addObject(new Wall(32 * (i + 2), 32 * 7, ID.Wall));
		}

		for (int i = 1; i < 4; i++) {
			handler.addObject(new Wall(32 * i, 32 * 8, ID.Wall));
			handler.addObject(new Wall(32 * (i + 3), 32 * 0, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 4, ID.Wall));
		handler.addObject(new Wall(32 * 4, 32 * 3, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 6, ID.Wall));

		handler.addObject(new GoalSquare(32 * 3, 32 * 3, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 2, 32 * 3, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 4, 32 * 2, ID.GoalSquare, handler));

		handler.addObject(new Box(32 * 4, 32 * 2, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 3, ID.Box));
		handler.addObject(new Box(32 * 2, 32 * 5, ID.Box));

		handler.addObject(new Player(32 * 2, 32 * 6, ID.Player2, handler));
	}

	public void loadLevel11(Handler handler) {
		for (int i = 2; i < 17; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			/*
			 * handler.addObject(new Wall(32*i,32*2, ID.Wall));
			 * handler.addObject(new Wall(32*i,32*4, ID.Wall));
			 * handler.addObject(new Wall(32*i,32*6, ID.Wall));
			 * handler.addObject(new Wall(32*i,32*8, ID.Wall));
			 * handler.addObject(new Wall(32*i,32*10, ID.Wall));
			 */
			handler.addObject(new Wall(32 * i, 32 * 12, ID.Wall));
		}

		for (int i = 0; i < 13; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 17, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 12, ID.Wall));

		for (int i = 1; i < 13; i += 2) {
			handler.addObject(new GoalSquare(32 * 16, 32 * i, ID.GoalSquare, handler));
		}
		for (int i = 1; i < 13; i += 2) {
			handler.addObject(new Box(32 * 2, 32 * i, ID.Box));
		}

		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2, handler));

		handler.addObject(new EnemyVertical(32 * 18, 32 * 1, ID.Enemy, handler));
		handler.addObject(new EnemyHorizontal(32 * 1, 32 * 13, ID.Enemy, handler));
	}
	
	public void loadLevel0(Handler handler) {
		for (int i = 0; i < 12; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 11, ID.Wall));
		}
		for (int i = 0; i < 11; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 11, 32 * i, ID.Wall));
		}
		
		handler.addObject(new Wall(32 * 5, 32 * 7, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 7, ID.Wall));
		
		handler.addObject(new Wall(32 * 3, 32 * 6, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 5, ID.Wall));
		
		handler.addObject(new Wall(32 * 8, 32 * 6, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 5, ID.Wall));
		
		handler.addObject(new GoalSquare(32 * 5, 32 * 9, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 6, 32 * 9, ID.GoalSquare, handler));
		
		handler.addObject(new Box(32 * 5, 32 * 5, ID.Box));
		handler.addObject(new Box(32 * 6, 32 * 5, ID.Box));
		
		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2, handler));
	}

	public int getCurrentLevelNumber() {
		return currentLevelNumber;
	}

	public void setCurrentLevelNumber(int currentLevelNumber) {
		this.currentLevelNumber = currentLevelNumber;
	}

	public int getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}
	
}
