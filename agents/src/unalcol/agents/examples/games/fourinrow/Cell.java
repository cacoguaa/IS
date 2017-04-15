package unalcol.agents.examples.games.fourinrow;

public class Cell {
	String color;
	int state;
	int[] priorities;
	int id;

	public Cell(){
		this.color = "empty";
		this.state = 0;
		priorities = new int[]{0,0,0,0};
		id = -1;
	}

	public Cell(String color, int state){
		this.color = color;
		this.state = 0;
		priorities = new int[]{0,0,0,0};
		id = 0;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int[] getPriorities() {
		return priorities;
	}

	public void setPriorities(int[] priorities) {
		this.priorities = priorities;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	
}
