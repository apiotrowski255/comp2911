import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 */

/**
 * @author sturgesj
 *
 */
public class Map {
	
	//private ArrayList<ArrayList<String>> map;
	private Square puzzel[][];
	private int movesToSolve;
	private String path;
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;
	
	public Map() {
		this.path = "";
		// boolean goal, boolean box, boolean player, boolean wall
		//map = new Square(true, false, false, false);
//		this.puzzel = new Square[][] {
//			{W(),W(),W(),W(),W(),W(),W()},
//			{W(),E(),P(),W(),W(),E(),W()},
//			{W(),E(),B(),W(),W(),E(),W()},
//			{W(),E(),E(),W(),W(),E(),W()},
//			{W(),E(),B(),E(),E(),E(),W()},
//			{W(),W(),W(),W(),E(),E(),W()},
//			{W(),W(),W(),W(),W(),W(),W()}
//		};
//		
//		System.out.println(this.puzzel[1][2].toString());
	}

	public void turnToHackMap() {
		this.puzzel = new Square[][] {
			{W(),W(),W(),W(),W(),W(),W()},
			{W(),E(),P(),W(),W(),G(),W()},
			{W(),E(),B(),W(),W(),G(),W()},
			{W(),E(),E(),W(),W(),E(),W()},
			{W(),E(),B(),E(),E(),E(),W()},
			{W(),W(),W(),W(),E(),E(),W()},
			{W(),W(),W(),W(),W(),W(),W()}
		};
//		this.puzzel = new Square[][] {
//			{W(),W(),W(),W(),W()},
//			{W(),E(),B(),G(),W()},
//			{W(),P(),B(),E(),W()},
//			{W(),E(),E(),G(),W()},
//			{W(),W(),W(),W(),W()}
//		};
		
		this.path = this.findSoultion();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Map [puzzel=" + Arrays.toString(puzzel) + ", movesToSolve=" + movesToSolve + "]";
	}

	private Square W() {
		return new Square(false, false, false, true);
	}
	private Square P() {
		return new Square(false, false, true, false);
	}
	private Square B() {
		return new Square(false, true, false, false);
	}
	private Square G() {
		return new Square(true, false, false, false);
	}
	public Square E() {
		return new Square(false, false, false, false);
	}
	
	



	public boolean isDone() {
		boolean flag = true;
		for(int y =0; y < puzzel.length; y++) {
			for(int x =0; x < puzzel[0].length; x++) {
				if (!puzzel[x][y].isComplete()) flag = false;
			}
		}

		//if (flag == true) System.out.println("NOOT");
		return flag;
	}

	public boolean isValidMove (int direction) {
		
		int[] fromLoc = this.getPlayerLocation();
		int xFromCoord = fromLoc[0];
		int yFromCoord = fromLoc[1];
		
		int[] toLoc = this.getToCoord(direction, xFromCoord, yFromCoord);
		int xToCoord = toLoc[0];
		int yToCoord = toLoc[1];
		
		if (this.puzzel[yToCoord][xToCoord].canBeMoveTo()) return true;
		else if (this.puzzel[yToCoord][xToCoord].isPushable()) {
			return canPushBox(direction, xToCoord, yToCoord);
		}
		
		else return false;
	}
	
	public boolean canPushBox(int direction, int xFromCoord, int yFromCoord) {
		boolean flag;
		int[] toLoc = this.getToCoord(direction, xFromCoord, yFromCoord);
		int xToCoord = toLoc[0];
		int yToCoord = toLoc[1];
		
		if (this.puzzel[yToCoord][xToCoord].canBeMoveTo()) flag = true;
		else flag = false;
		
		return flag;
	}
	
	public int[] getToCoord(int direction, int xFromCoord, int yFromCoord) {
		int[] toCoords = new int[2];
		if (direction == UP) {
			toCoords[0] = xFromCoord;
			toCoords[1] = yFromCoord - 1;
		}
		if (direction == DOWN) {
			toCoords[0] = xFromCoord;
			toCoords[1] = yFromCoord + 1;
		}
		if (direction == LEFT) {
			toCoords[0] = xFromCoord - 1;
			toCoords[1] = yFromCoord;
		}
		if (direction == RIGHT) {
			toCoords[0] = xFromCoord + 1;
			toCoords[1] = yFromCoord;
		}
		
		return toCoords;
	}
	
	public int[] getPlayerLocation() {
		int[] location = new int[2];
		for(int y =0; y < puzzel.length; y++) {
			for(int x =0; x < puzzel[0].length; x++) {
				if (puzzel[y][x].isPlayer()) {
					location[0] = x;
					location[1] = y;
				}
			}
		}
		return location;
	}

	public Map clone() {
		Map newMap = new Map();
		newMap.puzzel = new Square[this.puzzel.length][this.puzzel[0].length];
		newMap.path = this.path;
		
		for (int y = 0; y < this.puzzel.length; y++) {
			for (int x=0; x < this.puzzel[0].length; x++) {
				newMap.puzzel[y][x] = this.puzzel[y][x].clone();
			}
		}
		newMap.movesToSolve = this.movesToSolve;
		return newMap;
	}

	public Map move(int direction) {
		if (this.isValidMove(direction)) {

			int[] fromLoc = this.getPlayerLocation();
			int xFromCoord = fromLoc[0];
			int yFromCoord = fromLoc[1];
			
			int[] toLoc = this.getToCoord(direction, xFromCoord, yFromCoord);
			int xToCoord = toLoc[0];
			int yToCoord = toLoc[1];

			if (!this.puzzel[yToCoord][xToCoord].canBeMoveTo()) {
				this.puzzel[yToCoord][xToCoord].removeBox();
				//this.puzzel[yToCoord + yToCoord - yFromCoord][xToCoord + xToCoord - yFromCoord].addBox();	
				this.puzzel[this.getToCoord(direction, xToCoord, yToCoord)[1]][this.getToCoord(direction, xToCoord, yToCoord)[0]].addBox();
			}
			
			
			this.puzzel[yFromCoord][xFromCoord].removePlayer();
			this.puzzel[yToCoord][xToCoord].addPlayer();

			return this;
		} else return this;
	}
	
	public String findSoultion() {
		Queue<Map> q = new LinkedBlockingQueue<Map>();
		ArrayList<Map> l = new ArrayList<Map>();
		
		//int direction = 0;

		Map currentMap = this;
		Map currentPush = null;
		l.add(currentMap);
		for (int direction=0; direction < 4; direction ++) {
			if (currentMap.isValidMove(direction)) {
				currentPush = currentMap.clone().move(direction);
				currentPush.movesToSolve++;
				currentPush.path = currentPush.path + Integer.toString(direction);
			

				if (!l.contains(currentPush) ) {
					q.add(currentPush);
					l.add(currentPush);
				}
			}
		}
		
//		if (currentMap.isValidMove(UP)) {
//			currentPush = currentMap.clone().move(UP);
//			currentPush.movesToSolve++;
//			if (!l.contains(currentPush) ) {
//				q.add(currentPush);
//				l.add(currentPush);
//			}
//		}
//		direction = 1;
//		if (currentMap.isValidMove(UP)) {
//			currentPush = currentMap.clone().move(UP);
//			currentPush.movesToSolve++;
//			if (!l.contains(currentPush) ) {
//				q.add(currentPush);
//				l.add(currentPush);
//			}
//		}
//		direction = 2;
//		if (currentMap.isValidMove(UP)) {
//			currentPush = currentMap.clone().move(UP);
//			currentPush.movesToSolve++;
//			if (!l.contains(currentPush) ) {
//				q.add(currentPush);
//				l.add(currentPush);
//			}
//		} 
//		direction = 3;
//		if (currentMap.isValidMove(UP)) {
//			currentPush = currentMap.clone().move(UP);
//			currentPush.movesToSolve++;
//			if (!l.contains(currentPush) ) {
//				q.add(currentPush);
//				l.add(currentPush);
//			}
//		}


		while ( !q.isEmpty() && !currentMap.isDone()) {
			currentMap = q.poll();
			System.out.println(" ");
			currentMap.printMap();
			
			for (int direction=0; direction < 4; direction ++) {
				if (currentMap.isValidMove(direction)) {
					currentPush = currentMap.clone().move(direction);
					currentPush.movesToSolve++;
					currentPush.path = currentPush.path + Integer.toString(direction);
					if (!l.contains(currentPush) ) {
						q.add(currentPush);
						l.add(currentPush);
					}
				}
			}
//			if (currentMap.isValidMove(UP) && !l.contains(currentMap.clone().move(UP)) ) {
//				q.add(currentMap.clone().move(UP));
//				l.add(currentMap.clone().move(UP));
//			}
//			if (currentMap.isValidMove(DOWN) && !l.contains(currentMap.clone().move(DOWN))) {
//				q.add(currentMap.clone().move(DOWN));
//				l.add(currentMap.clone().move(DOWN));
//			} 
//			if (currentMap.isValidMove(LEFT) && !l.contains(currentMap.clone().move(LEFT))) {
//				q.add(currentMap.clone().move(LEFT));
//				l.add(currentMap.clone().move(LEFT));
//			} 
//			if (currentMap.isValidMove(RIGHT) && !l.contains(currentMap.clone().move(RIGHT))) {	
//				q.add(currentMap.clone().move(RIGHT));
//				l.add(currentMap.clone().move(RIGHT));
//			}
		}
		//System.out.println(currentMap.path);
		if (currentMap.isDone()) return currentMap.path;
		else return null;
	}
	
	
	
	public void printMap() {
		for(int y =0; y < puzzel.length; y++) {
			for(int x =0; x < puzzel[0].length; x++) {
				System.out.printf(this.puzzel[y][x].Printer() + " ");
				
			}
			System.out.printf("\n");
		}
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Map other = (Map) obj;
		if (movesToSolve != other.movesToSolve)
			return false;
		if (!Arrays.deepEquals(puzzel, other.puzzel))
			return false;
		return true;
	}
	
	public void toTxt(String fileName) {
		try{
		    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		    writer.println(this.getTxtOutputString());
		    writer.println(this.path);
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}
	
	public String getTxtOutputString() {
		String encode = "";
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				encode += this.puzzel[y][x].encode();
			}
		}
		
		
		return encode;
	}

	public void generateMap(int width) {
		this.puzzel = new Square[width][width];
		
		for (int y =0; y< this.puzzel.length; y++) {
			this.puzzel[y][0] = W();
			this.puzzel[y][this.puzzel[y].length-1] = W();
		}
		
		for (int x =0; x< this.puzzel.length; x++) {
			this.puzzel[0][x] = W();
			this.puzzel[this.puzzel.length-1][x] = W();
		}
		
		//this.printMap();
	}
}
