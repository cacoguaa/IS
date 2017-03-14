package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;

public class Node {

	Node parent;
	boolean visited;
	int[] pos; 
	ArrayList<Node> childs;
	int state;

	public Node(int x, int y, Node parent) {
		visited = false;
		this.parent = parent;		
		pos = new int[2];
		pos[0] = x; pos[1] = y;
		childs = new ArrayList<Node>();
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}