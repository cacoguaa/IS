package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;
import java.util.Stack;

import unalcol.agents.simulate.util.SimpleLanguage;

public class SimpleAgentSCS extends AgentSCS {
	private Stack<Node> nodes;
	private Stack<Node> parents;
	private ArrayList<Integer> states;
	private int head;
	private int posX;
	private int posY;
	private boolean change;
	private int steps;

	Node old;
	
	public SimpleAgentSCS(SimpleLanguage _lenguage) {
		super(_lenguage);
		//initialize all
		nodes = new Stack<>();
		parents = new Stack<>();
		states = new ArrayList<>();
		steps = 0;
		change = false;
		head = 0;
		posX = 0;
		posY = 0;
		//Create root node
		Node root =new Node(posX,posY,null, 0);
		old = root;
		nodes.add(root);
		states.add(0);
	};

	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, // Moves
			boolean MT, boolean FAIL // Finish, Die
	) {
		//System.out.println("-------------------------------");
		if( MT || nodes.isEmpty()){
			nodes.clear();
			return -1;
		}
		/*
		for( Node n: nodes){
			System.out.println("n X: " + n.pos[0] + " Y " + n.pos[1]);
		}*/
		if(!nodes.isEmpty()){
			boolean[] walls = new boolean[]{PF,PD,PA,PI};
			Node actual = nodes.peek();
			//Existe un camino consecutivo

			if( canMove( actual, walls ) ){
				
				if( change ){
					int movX = -1, movY = -1;
					movX = (actual.pos[0] - posX);
					movY = (actual.pos[1] - posY);
					change = false;
					return movement(movX, movY);
				}
				old = nodes.pop();
				posX = actual.pos[0];
				posY = actual.pos[1];
				if( createChildren( walls, actual ) ){ 
					parents.add(actual);
					int movX = -1, movY = -1;
					movX = (nodes.peek().pos[0] - posX);
					movY = (nodes.peek().pos[1] - posY);
					return movement(movX, movY); //k
				} else return -1;
			
			} else {
				//Existe un camino lejano
				return searchPath( actual );
			}

		} else {
			return -1;
		}
	}
	
	public boolean canMove(Node actual, boolean[] walls){
		if( steps > 0){
			boolean can = false;
			can = (Math.abs(posX - actual.pos[0]) + Math.abs(posY - actual.pos[1])) < 2;
			if( can && (actual.depth < old.depth) ) can = false;
			return can;
		} else return true;
	}
	
	public int searchPath(Node objetive){
		int k = -1,movX = 0, movY = 0;
		change = true;


			Node parent = old.parent;
			movX = parent.pos[0] - posX;
			movY = parent.pos[1] - posY;
			//System.out.println( movX + " " +  movY);
			old = parent;
			posX = old.pos[0];
			posY = old.pos[1];
			k = movement(movX, movY);
		
		return k;
	}
	
	public int movement(int movX, int movY) {
		int k = -1;
		
		// Move in +X
		if (movX == 1) {
			switch (head) {
			case 0:
				k = 1;
				break;
			case 1:
				k = 0;
				break;
			case 2:
				k = 3;
				break;
			case 3:
				k = 2;
				break;
			}
		}
		// Move in -X
		if (movX == -1) {
			switch (head) {
			case 0:
				k = 3;
				break;
			case 1:
				k = 2;
				break;
			case 2:
				k = 1;
				break;
			case 3:
				k = 0;
				break;
			}
		}

		// Move in +Y
		if (movY == 1) {
			switch (head) {
			case 0:
				k = 0;
				break;
			case 1:
				k = 3;
				break;
			case 2:
				k = 2;
				break;
			case 3:
				k = 1;
				break;
			}
		}
		// Move in -Y
		if (movY == -1) {
			switch (head) {
			case 0:
				k = 2;
				break;
			case 1:
				k = 1;
				break;
			case 2:
				k = 0;
				break;
			case 3:
				k = 3;
				break;
			}
		}
		steps++;
		head = (head + k) % 4;
		return k;
	}

	public int movement2(int movX, int movY) {
		int k = -1;
		
		// Move in +X
		if (movX == 1) {
			switch (head) {
			case 0:
				k = 1;
				break;
			case 1:
				k = 0;
				break;
			case 2:
				k = 3;
				break;
			case 3:
				k = 2;
				break;
			}
		}
		// Move in -X
		if (movX == -1) {
			switch (head) {
			case 0:
				k = 3;
				break;
			case 1:
				k = 2;
				break;
			case 2:
				k = 1;
				break;
			case 3:
				k = 0;
				break;
			}
		}

		// Move in +Y
		if (movY == 1) {
			switch (head) {
			case 0:
				k = 0;
				break;
			case 1:
				k = 3;
				break;
			case 2:
				k = 2;
				break;
			case 3:
				k = 1;
				break;
			}
		}
		// Move in -Y
		if (movY == -1) {
			switch (head) {
			case 0:
				k = 2;
				break;
			case 1:
				k = 1;
				break;
			case 2:
				k = 0;
				break;
			case 3:
				k = 3;
				break;
			}
		}


		return k;
	}

	public boolean createChildren(boolean[] walls, Node actual) {
		int nChilds = 0;
		boolean success = false;
		for (int i = 0; i <= 3; i++) {
			if (!walls[i]) {
				nChilds += createState( i, actual );
			}
		}
		if( nChilds > 0) success = true;
		return success;
	}

	public int createState(int i, Node node) {
		int success = 0;
		int newX = 0;
		int newY = 0;
		int newHead = ( i + head ) % 4;
		int state = 0;
		switch( newHead ){
		case 0:
			newY =  1;
			break;
		case 1:
			newX =  1;
			break;
		case 2:
			newY = -1;
			break;
		case 3:
			newX = -1;
			break;
		}
		
		newX += posX;
		newY += posY;
		
		//build the state
		if( newX >= 0 ) state = newX*10000;
		else {
			state = 1000;
			state += (-newX)*10000;
		}
		if( newY >= 0 ) state += newY*10;
		else {
			state += 1;
			state += (-newY)*10;
		}
		//Verify if the position isn't visited yet
		if ( states.indexOf( state ) == -1 ){
			/*for(Node n:nodes){
				System.out.println(n.pos[0] + " " + n.pos[1]);
			}*/
			//System.out.println(newX + " " + newY);
			Node child = new Node(newX,newY, node, node.depth+1);
			nodes.add(child);
			states.add(state);
			node.childs.add(child);
			success = 1;
		}	
		
		return success;
	}
	
}