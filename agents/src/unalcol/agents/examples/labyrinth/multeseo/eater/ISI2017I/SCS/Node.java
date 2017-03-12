package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;

public class Node {

	Node parent;
	boolean visited;
	//ArrayList<Node> children;
	int[] pos; 
	
	public Node(int x, int y, int head, Node parent) {
		visited = false;
		//children = new ArrayList<>();
		this.parent = parent;
		pos = new int[3];
		pos[0] = x;
		pos[1] = y;
		pos[2] = head;
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
