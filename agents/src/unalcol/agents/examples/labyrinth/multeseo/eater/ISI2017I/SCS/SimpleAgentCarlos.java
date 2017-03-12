package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;
import java.util.Stack;

import unalcol.agents.simulate.util.SimpleLanguage;

public class SimpleAgentCarlos extends AgentSCS {
	private Stack<Node> nodes;
	private ArrayList<Integer> states;
	private int head;
	private int posX;
	private int posY;
	private int cn;
	private int mov;
	private int lastMov;
	public SimpleAgentCarlos(SimpleLanguage _lenguage) {
		super(_lenguage);
		//initialize all
		nodes = new Stack<>();
		states = new ArrayList<>();
		head = 0;
		posX = 0;
		posY = 0;
		
		//Ayuda para verificar movimiento
		cn = 7;
		//Create root node
		Node root =new Node(posX,posY,head);
		nodes.add(root);
		states.add(0);
		lastMov = 0;
	};

	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, // Moves
			boolean MT, boolean FAIL // Finish, Die
	) {
		if(!nodes.isEmpty()){
			boolean[] walls = new boolean[]{PF,PD,PA,PI};
			Node actual = nodes.pop();
			posX = actual.pos[0];
			posY = actual.pos[1];
			if( createChildren( walls, actual ) ){
				int k = -1;
				int movX = -1, movY = -1;
				movX = (nodes.peek().pos[0] - posX);
				movY = (nodes.peek().pos[1] - posY);
				return movement(movX, movY);
			} else return -1;
		} else {
			return -1;
		}
	}
	
	public int movement(int movX, int movY){
		int k = -1;
		System.out.print("movX: " + movX + " movY: " + movY );
		//Move in +X
		if( movX == 1){
			switch( head ){
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
		//Move in -X
		if( movX == -1){
			switch( head ){
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
		
		//Move in +Y
		if( movY == 1){
			switch( head ){
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
		
		//Move in -Y		
		if( movY == -1){
			switch( head ){
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
	
		System.out.println( " lm: " + lastMov);
		head = (head+k)%4;
		return k;
	}
	
	public void setPosition(){
		switch( head ){
		case 0:
			posY += 1;
			break;
		case 1:
			posX += 1;
			break;
		case 2:
			posY -= 1;
			break;
		case 3:
			posX -= 1;
			break;
		}
		System.out.println("position change to X:" + posX + " Y: " + posY);
	};
	
	public void setHead(int k){
		head = (head + k) % 4;
		System.out.println("head: " + head + " k " + k);
	};
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
			Node child = new Node(newX,newY,head);
			nodes.add(child);
			states.add(state);
			success = 1;
		}	
		
		return success;
	}
	
}