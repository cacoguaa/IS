package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;

public class Node {

	Node parent;
	boolean visited;
	int[] pos; 
	ArrayList<Node> childs;
	int state;
	short depth;
	public Node(int x, int y, Node parent, int depth) {
		visited = false;
		this.parent = parent;		
		pos = new int[2];
		pos[0] = x; pos[1] = y;
		childs = new ArrayList<Node>();
		this.depth = (short)depth;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	@Override
	public String toString(){
		String ret = String.valueOf(pos[0]) + " " + String.valueOf(pos[1]);
		return 	ret;
	}
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}