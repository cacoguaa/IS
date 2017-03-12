package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import unalcol.agents.simulate.util.SimpleLanguage;

public class SimpleAgentCarlos extends AgentSCS {
	private Stack<Node> nodes;
	private ArrayList<Integer> states;
	private int head;
	private int posX;
	private int posY;
	public SimpleAgentCarlos(SimpleLanguage _lenguage) {
		super(_lenguage);
		nodes = new Stack<>();
		states = new ArrayList<>();
		head = 0;
		posX = 0;
		posY = 0;
		
		Node root = new Node(0,0,head,null);
		nodes.add(root);
		states.add(00000);
		
	};

	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, // Moves
			boolean MT, boolean FAIL // Finish, Die
			) {
		
		if ( MT || nodes.isEmpty() ) {
			return -1;
		}
		int k = -1;
		Node actual = nodes.pop();
		posX = actual.pos[0];
		posY = actual.pos[1];
		boolean[] walls = new boolean[]{PF,PD,PA,PI};
		//Verify if actual have sons
		System.out.println( "actual x: " + actual.pos[0] + " y: " + actual.pos[1]);
		verifyChildren( actual, walls);
		if(nodes.size() >0 ){
			if( nodes.peek().parent.equals(actual)){
				int nextX = Math.abs(nodes.peek().pos[0] - posX);
				int nextY = Math.abs(nodes.peek().pos[1] - posY);
				if( nextY != 0){
					if( nextY == 1) k = 0;
					else {
						k = 2;
					}
				} else if( nextX != 0) {
					if( nextX == 1) k = 1;
					else k = 3;
				} else k = -1;
			}
		}
		return k;
	}
	
	public void verifyChildren(Node actual, boolean[] walls){
		for( int i = 0; i < 4;i++){
			if( !walls[i]){
				createChild( i , actual);				
			}
		}
	}
	
	public void createChild( int i, Node actual){
		int x = 0;
		int y = 0;
		int k = 0;
		switch( ( i + head ) % 4 ){
		case 0:
			y = 1;
			break;
		case 1:
			x = 1;
			break;
		case 2:
			y = -1;
			break;
		case 3:
			x = -1;
			break;
		}
		x += posX;
		y += posY;
		int state;
		
		if( x >= 0 ) state = x*10000;
		else{
			state = 1000;
			state = (-x)*10000;		
		}
		
		if( y >= 0 ) state += y*10;
		else{
			state += 1;
			state += (-y)*10;
		}
		if( states.indexOf( state ) == -1 ){
			System.out.println("new state: " + state);
			states.add( state );
			Node child = new Node(x,y,head,actual);
			nodes.add(child);
		}
	}
	
}
