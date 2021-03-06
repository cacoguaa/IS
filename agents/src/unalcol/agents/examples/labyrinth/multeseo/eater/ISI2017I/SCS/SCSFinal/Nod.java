package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.SCSFinal;

public class Nod {
	boolean visited;
	private byte[] pos;
	private int state;
	private Nod parent;
	public byte[] getPos() {
		return pos;
	}

	public void setPos(byte[] pos) {
		this.pos = pos;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}


	public Nod(int x,int y,int state) {
		visited = false;	
		pos = new byte[2];
		pos[0] = (byte) x;
		pos[1] = (byte) y;
		this.state = state;
		parent = null;

	}
	
	public void setParent(Nod parent){
		this.parent = parent;
	}
	
	public Nod getParent(){
		return parent;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	@Override
	public String toString(){
		String ret = String.valueOf("State:" + state);
		return 	ret;
	}
}
