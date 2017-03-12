package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import unalcol.agents.simulate.util.SimpleLanguage;

public class SimpleAgentSCS extends AgentSCS{
	
	private Stack<Node> nodes;
	private ArrayList<Short> states;
	private int head;
	private int x;
	private int y;	
	
	public SimpleAgentSCS(SimpleLanguage _lenguage){
		super( _lenguage);
		nodes = new Stack<>();
		Node node = new Node(0,0,0);
		nodes.add(node);
		head = 0;
		states = new ArrayList<>();
		Short state = 0;
		states.add(state);
	};
		
	public void createChild(boolean[] walls, Node parent){	
		for(int i=0; i<4; i++){
			if(!walls[i]){
				switch (i) {
				case 0:
					createState( 0 );
					break;
				case 1:
					createState( 1 );
					break;
				case 2:
					createState( 2 );
					break;
				case 3:
					createState( 3 );
					break;
				}
			}
		}
	}
	
	public void createState(int caso) {
		Node child = null;
		int rot = (caso + head)%4;
		switch (rot) {
		case 0:
			y+=1;
			child = new Node(x,y,head);
			break;
		case 1:
			x+=1;
			child = new Node(x,y,head);
			break;
		case 2:
			y-=1;
			child = new Node(x,y,head);
			break;
		case 3:
			x-=1;
			child = new Node(x,y,head);
			break;
		}
		nodes.add(child);
	}
	@Override
	public int accion(
			boolean PF, boolean PD, boolean PA, boolean PI,	//Moves
			boolean MT, boolean FAIL						//Finish, Die
			){
		Node node = nodes.pop();
		
		if( MT ){
			return -1;
		}
		
		int f;
		int k = 0;
		boolean[] walls = new boolean[]{PF,PD,PA,PI};
		
		if( PF && PD && PA && PI){ //Encerrado
			k = -1;
		} else if ( !PF && PD && PA && PI){ //Sin Pared Alfrente
			k = 0;
		} else if ( PF && !PD && PA && PI){ //Sin Pared Derecha
			k = 1;
		} else if ( PF && PD && !PA && PI){ //Sin Pared Abajo
			k = 2;
		} else if ( PF && PD && PA && !PI){ //Sin Pared Izquierda
			k = 3;
		} else if ( PF && !PD && PA && !PI){ //Pared Alfrente Pared Atras
			f = (int)(Math.random()*3);
			if( f == 0 ){
				k = 1;
			} else {
				k = 3;
			}
		} else if ( (!PF && PD && !PA && PI) 	//Pared Izquierda Pared Derecha
				|| (!PF && PD && PA && !PI) 	//Pared Derecha Pared Atras
				|| (!PF && !PD && PA && PI) ){ 	//Pared Izquierda Pared Atras
			k = 0;
			
		} else if ( PF && !PD && !PA && !PI){ //Pared Alfrente 3 abiertas
			k = (int)(Math.random()*3) + 1;
		} else if ( !PF && !PD && PA && !PI){ //Pared Atras 3 abiertas
			f = (int)(Math.random()*3);
			if( f == 0 ){
				k = 0;
			} else if (f == 1){
				k = 1;
			} else {
				k = 1;
			}
		} else if ( !PF && !PD && !PA && !PI){ //4 abiertas
			k = (int)(Math.random()*4);
		} else if ( !PF && PD && !PA && !PI){ //Pared Derecha 3 abiertas
			f = (int)(Math.random()*2);
			if( f == 0 ){
				k = 0;
			} else {
				k = 3;
			}
		} else if ( !PF && !PD && !PA && PI){ //Pared Izquierda 3 abiertas
			f = (int)(Math.random()*2);
			if( f == 0 ){
				k = 0;
			} else {
				k = 1;
			}
		} else if ( PF && PD && !PA && !PI){ //Pared Alfrente y Derecha
			k = 3;
		} else if ( PF && !PD && !PA && PI){ //Pared Alfrente y Izquierda
			k = 1;
		} else {
			k = -1;
		}
		head = (head+k)%4;
		createChild(walls,node);
		//System.out.println("Head:  " + head);
		//System.out.println(node.pos[0] + " " + node.pos[1]);
		System.out.println("x: " + x +"y: " + y);
		return k;
	}

}
