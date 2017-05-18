import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
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
		
		//this.generateMap(10);
		//this.generateMap(10);
	}
	
	public Map newMap(int width) {
		this.path = "";
		
		String soultion = null;
		
		if (this.generateMap(width)) soultion = this.findSoultion();

		
		while(soultion == null || soultion.length() <5) {

			if (this.generateMap(width)) {
				this.printMap();
				soultion = this.findSoultion();
				System.out.println(soultion);
			} else soultion = null;
		}
		
		return this;

	}
	
//	private void reset() {
//		this.movesToSolve = 0;
//		this.puzzel = null;
//		this.path = "";
//	}

	public void turnToHackMap() {
		this.puzzel = new Square[][] {
			{W(),W(),W(),W(),W(),W(),W()},
			{W(),E(),P(),W(),W(),G(),W()},
			{W(),E(),B(),W(),W(),G(),W()},
			{W(),E(),E(),W(),W(),E(),W()},
			{W(),E(),B(),W(),E(),E(),W()},
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
//		this.printMap();
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
		



		while ( !q.isEmpty() && !currentMap.isDone()) {
			currentMap = q.poll();

			
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

		}
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

	public boolean generateMap(int width) {
		this.puzzel = new Square[width][width];
		//this.printMap();
		

			
		for (int y =0; y< this.puzzel.length; y++) {
			this.puzzel[y][0] = W();
			this.puzzel[y][this.puzzel[y].length-1] = W();
		}
		
		for (int x =0; x< this.puzzel.length; x++) {
			this.puzzel[0][x] = W();
			this.puzzel[this.puzzel.length-1][x] = W();
		}
		
		int addfactor = 0;
		if (this.puzzel.length%2 == 1) addfactor = 1;

		for (int d=1; d<this.puzzel.length/2 + addfactor; d++) {
			for (int y =d; y< this.puzzel.length-d; y++) {
				this.popSquare(y,d);
				this.popSquare(y,this.puzzel[y].length-1 -d);
			}
			
			for (int x =d; x< this.puzzel.length-d; x++) {
				this.popSquare(d,x);
				this.popSquare(this.puzzel.length-1-d,x);
			}
		}
		
		this.makeOneChamber();	
		
		return this.placeKeyObjectives(width);
		
	}
	
	private boolean placeKeyObjectives(int width) {
		int noBoxes = howManyBoxes(width);
		
		for (int i =0; i < noBoxes; i++) {
			if (!this.placeBox()) return false;
		}
		for (int i =0; i < noBoxes; i++) {
			if (!this.placeGoals()) return false;
		}
		
		if (!this.placePlayer()) return false;
		
		return true;
	}
	
	private boolean placeGoals() {
		Random r = new Random();
		int noEmptySquares =0;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()) noEmptySquares++;
			}
		}
		
		if (noEmptySquares == 0) return false;
		
		boolean flag = false;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty() && flag == false) {
					boolean val = r.nextInt(noEmptySquares)==0;
					if (val) {
						this.puzzel[y][x].addGoal();
						flag = true;
					}
					noEmptySquares--;
				}
			}
		}
		
		
		return true;
	}
	
	
	private boolean placePlayer() {
		int noEmptySquares = 0;
		Random r = new Random();
		
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()) noEmptySquares+= 1;
			}
		}
		if (noEmptySquares == 0) return false;
		boolean flag = false;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty() && flag == false) {
					boolean val = r.nextInt(noEmptySquares)==0;
					if (val) {
						this.puzzel[y][x].addPlayer();
						flag = true;
					}
					noEmptySquares--;
				}
			}
		}
		
		
		
		return true;
	}
	

	
	private boolean placeBox() {
		int weightEmptySquares = 0;
		Random r = new Random();
		
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()) {
					weightEmptySquares+= this.findWeightEmptySquare(y, x);
				}
			}
		}		
		
		if (weightEmptySquares == 0) return false;
		boolean flag = false;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()){
					
					
					float numer = (float) this.findWeightEmptySquare(y, x);
					float denom = (float) weightEmptySquares;
					
					boolean val = r.nextFloat() < numer/denom;
					
					
					if (flag == false && val) {
						this.puzzel[y][x].addBox();
						flag = true;
						
					}
					weightEmptySquares-=this.findWeightEmptySquare(y, x);
					
				}
			}
		}
		
		//System.out.println(weightEmptySquares);
		return true;
	}
	
	private int findWeightEmptySquare(int y, int x) {
		int noTouchedWalls = 0;
		if (this.puzzel[y+1][x] != null && this.puzzel[y+1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y-1][x] != null && this.puzzel[y-1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x+1] != null && this.puzzel[y][x+1].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x-1] != null && this.puzzel[y][x-1].isWall()) noTouchedWalls++;
				
		if (noTouchedWalls==3) {
			return 0;
		}
		
		if (noTouchedWalls == 2) {
			if ((this.puzzel[y+1][x].isWall() && this.puzzel[y-1][x].isWall())||(this.puzzel[y][x+1].isWall() && this.puzzel[y][x-1].isWall())) return 1;
			else return 0;
		}
		
		if (noTouchedWalls == 1) {
			return 1;
		}
		
		if (noTouchedWalls == 0) {
			return 1;
		}
		
		return 0;
	}
	
	private int howManyBoxes(int width){
		int maxBoxes = width/3 + 1;
		int noBoxes = 1;
		Random r = new Random();
		for (int i=0; i < maxBoxes-1; i++) {
			boolean val = r.nextInt(2)==0;
			if (val) noBoxes++;
		}
		
		return noBoxes;
	}
	
	private void popSquare(int y, int x) {
		int noTouchedWalls =0;
		Random r = new Random();
		if (this.puzzel[y+1][x] != null && this.puzzel[y+1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y-1][x] != null && this.puzzel[y-1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x+1] != null && this.puzzel[y][x+1].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x-1] != null && this.puzzel[y][x-1].isWall()) noTouchedWalls++;
		

		if (noTouchedWalls==4) this.puzzel[y][x] = W();
		
		if (noTouchedWalls==3) {
			boolean val = r.nextInt(1)==0;
			if (val == true) this.puzzel[y][x] = E();
			else this.puzzel[y][x] = W();
		}
		
		if (noTouchedWalls == 2) {
			if ((this.puzzel[y+1][x] != null && this.puzzel[y+1][x].isWall() && this.puzzel[y-1][x] != null && this.puzzel[y-1][x].isWall())||(this.puzzel[y][x+1] != null && this.puzzel[y][x+1].isWall() && this.puzzel[y][x-1] != null && this.puzzel[y][x-1].isWall())) this.puzzel[y][x] =E();
			else {
				boolean val = r.nextInt(1)==0;
				if (val == true) this.puzzel[y][x] = E();
				else this.puzzel[y][x] = W();
			}
		}
		
		if (noTouchedWalls == 1) {
			boolean val = r.nextInt(2)==0;
			if (val == true) this.puzzel[y][x] = E();
			else this.puzzel[y][x] = W();
		}
		
		if (noTouchedWalls == 0) {
			boolean val = r.nextInt(3)==0;
			if (val == true) this.puzzel[y][x] = E();
			else this.puzzel[y][x] = W();
		}
		
		//System.out.println("test");
		//this.puzzel[y][x] = E();
	}
	
	private Map makeOneChamber() {
		ArrayList<Integer> chamberSize = new ArrayList<Integer>();
		int[][] chamberMarkers = new int[this.puzzel.length][this.puzzel[0].length];
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				chamberMarkers[y][x] = -1;
			}
		}
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (!this.puzzel[y][x].isWall() && chamberMarkers[y][x] == -1) this.fillChamber(y,x,chamberSize, chamberMarkers);
			}
		}
		
//		for (int y =0; y< this.puzzel.length; y++) {
//			for (int x =0; x< this.puzzel[y].length; x++) {
//				System.out.print(chamberMarkers[y][x]);
//			}
//			System.out.println("");
//		}
		
		int largestChamber = 0;
		for(int i=0; i < chamberSize.size(); i++) {
			if (chamberSize.get(i) > chamberSize.get(largestChamber)) largestChamber = i;
		}
		
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {	
				//System.out.println(chamberMarkers[y]);
				if (chamberMarkers[y][x] != largestChamber) this.puzzel[y][x] = W(); 
			}
		}
		
		
		return this;
	}
	
	private void fillChamber(int y, int x, ArrayList<Integer> chamberSize,int[][] chamberMarkers){
		int chamberNumber = chamberSize.size();
		chamberSize.add(1);
		chamberMarkers[y][x] = chamberNumber;
		
		Queue<int[]> q = new LinkedBlockingQueue<int[]>();
		
		int[] currCoord = new int[] {y,x};
		int[] currPush;
		
		currPush = new int[]{currCoord[0]+1,currCoord[1]};

		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		currPush = new int[]{currCoord[0]-1,currCoord[1]};
		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		currPush = new int[]{currCoord[0],currCoord[1]+1};
		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		currPush = new int[]{currCoord[0],currCoord[1]-1};
		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		
		while (!q.isEmpty()) {
			currCoord = q.poll();
			
			currPush = new int[]{currCoord[0]+1,currCoord[1]};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
			currPush = new int[]{currCoord[0]-1,currCoord[1]};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
			currPush = new int[]{currCoord[0],currCoord[1]+1};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
			currPush = new int[]{currCoord[0],currCoord[1]-1};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
		}
	}
}
