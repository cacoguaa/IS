package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;
import java.util.Stack;

import unalcol.agents.simulate.util.SimpleLanguage;

public class SimpleAgentCarlos extends AgentSCS {
	private Stack<Node> nodes;
	private ArrayList<Integer> states;
	private byte head;
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
		cn = 8;
		//Create root node
		Node root =new Node(posX,posY,head);
		nodes.add(root);
		states.add(0);
		lastMov = -1;
	};

	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, // Moves
			boolean MT, boolean FAIL // Finish, Die
	) {
		if ( MT || nodes.isEmpty() ) {
			return -1;
		}
		int k = -1;
		Node actual;
		//Si se mueve hacia adelante se altera la posicion actual
		//Con respecto a la cabeza y se recorre el nodo
		if( lastMov == 0 ){
			actual = nodes.pop();
			setPosition();
		} else {
		//Si solo se gira se toma solo se toma el nuevo
			actual = nodes.peek();
		}
		
		//restringir pasos
		if( cn > 0 ){
			k = 0;
			cn--;
		}
		
		//Que paredes existen
		boolean walls[] = new boolean[]{PF,PD,PA,PI};	

		//revisar y agregar que hijos tiene
		createChildren( walls, actual );
		System.out.println("X: " + posX + " Y: " + posY);
		System.out.println(states);
		for( Node node: nodes){
			System.out.println("hijo X: " + node.pos[0] + " Y: " + node.pos[1]);
		}
		System.out.println("----------------------------------- " + mov);
		mov += 1;
		lastMov = k;
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
	};

	public int createChildren(boolean[] walls, Node actual) {
		for (int i = 0; i <= 3; i++) {
			if (!walls[i]) {
				createState( i, actual );
			}
		}
		return 0;
	}

	public void createState(int i, Node node) {
		int newX = 0;
		int newY = 0;
		int newHead = 0;
		int state = 0;
		switch( i ){
		case 0:
			if( head == 0 ) newY =  1;
			if( head == 1 ) newX =  1;
			if( head == 2 ) newY = -1;
			if( head == 3 ) newX = -1;
			break;
		case 1:
			if( head == 0 ) newX =  1;
			if( head == 1 ) newY = -1;
			if( head == 2 ) newX = -1;
			if( head == 3 ) newY =  1;
			break;
		case 2:
			if( head == 0 ) newY = -1;
			if( head == 1 ) newX = -1;
			if( head == 2 ) newY =  1;
			if( head == 3 ) newX =  1;
			break;
		case 3:
			if( head == 0 ) newX = -1;
			if( head == 1 ) newY =  1;
			if( head == 2 ) newX =  1;
			if( head == 3 ) newY = -1;
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
		}
		//New son with the next position to visit
		
	}

}