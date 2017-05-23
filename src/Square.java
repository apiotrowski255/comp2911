/**
 * 
 */

/**
 * @author sturgesj
 *
 */
public class Square {
	private boolean goal;
	private boolean box;
	private boolean player;
	private boolean wall;
	
	/**
	 * @param goal
	 * @param box
	 * @param player
	 * @param wall
	 */
	public Square(boolean goal, boolean box, boolean player, boolean wall) {
		super();
		this.goal = goal;
		this.box = box;
		this.player = player;
		this.wall = wall;
	}
	
	public boolean isPushable() {
		return this.box;
	}
	public boolean isEmpty() {
		if (!this.box && !this.goal && !this.player && !this.wall) return true;
		else return false;	
	}
	
	public boolean canBeMoveTo() {
		if (this.box == false && this.player == false && this.wall == false) return true;
		else return false;
	}
	
	public boolean isComplete() {
		if (this.box == true && this.goal == false) return false;
		else return true;
	}
	
	public boolean isPlayer() {
		if (this.player == true) return true;
		else return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Square [goal=" + goal + ", box=" + box + ", player=" + player + ", wall=" + wall + "]";
	}


	public Square clone() {
		return new Square(this.goal, this.box, this.player, this.wall);
	}
	
	public Square addPlayer() {
		this.player = true;
		return this;
	}
	
	public Square removePlayer() {
		this.player = false;
		return this;
	}
	
	public Square addBox() {
		this.box = true;
		return this;
	}
	
	public Square removeBox() {
		this.box = false;
		return this;
	}
	
	
	public String Printer() {
		String n = "";
		if (this.box == true) n += "B";
		else if (this.goal == true) n += "G";
		else if (this.player == true) n += "P";
		else if (this.wall == true) n += "W";
		else n += " ";
		
		if (this.box == true && this.wall == true) System.out.println("TEST");
		if (this.player == true && this.wall == true) System.out.println("TEST");
		
		return n;
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
		Square other = (Square) obj;
		if (box != other.box)
			return false;
		if (goal != other.goal)
			return false;
		if (player != other.player)
			return false;
		if (wall != other.wall)
			return false;
		return true;
	}
	
	
	public String encode() {
		if (this.box == true && this.goal == true) return "C";
		else if (this.player == true && this.goal == true) return "S";
		else if (this.box == true) return "B";
		else if (this.goal == true) return "G";
		else if (this.player == true) return "P";
		else if (this.wall == true) return "W";
		else return " ";
	}
	
	public boolean isWall() {
		return this.wall;
	}
	
	public void addGoal() {
		this.goal = true;
	}
	public boolean isGoal() {
		return this.goal;
	}
	

}
