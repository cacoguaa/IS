package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.Eater;

import java.util.ArrayList;
import java.util.Stack;

import unalcol.agents.simulate.util.SimpleLanguage;

public class EaterAgentSCS extends AgentSCSEater {
	
	private Stack<Node> nodes;
	private Stack<Node> parents;
	
	private ArrayList<Integer> states;
	private ArrayList<Byte> foods;
	private ArrayList<Byte> goodFood;
	private ArrayList<Node> toAdd;
	private ArrayList<Integer> foodsPos;
	
	private int head;
	private int posX;
	private int posY;
	private int steps;
	private int maxEL;
	private int oldEL;
	private int actualFood;
	private int limit;

	private byte idFood;
	private byte eatStep;
	
	private boolean change;
	private boolean start;
	private boolean e;

	Node old;

	public EaterAgentSCS(SimpleLanguage _lenguage) {
		super(_lenguage);
		// initialize all
		initialize();
		goodFood = new ArrayList<Byte>(16);
		maxEL = 0;
		eatStep = -2;
		start = true;
	};

	public boolean fMaxEnergy(int EL){
		if( EL > maxEL){
			maxEL = EL;
			limit = EL/3;
			return false;
		}
		else return true;
	}
	
	@Override
	public int accion(
			boolean PF, boolean PD, boolean PA, boolean PI, // Moves
			boolean MT, boolean FAIL, // Finish, Die
			boolean AF, boolean AD, boolean AA, boolean AI,	// Agents
			boolean RE, boolean RC, boolean RSh,boolean RS, boolean RW, // Resources
			int EL
			) {
		// Goal reach
		if ( MT ) {
			nodes.clear();
			return -1;
		}
		//Read initial energy level
		if(start){
			maxEL = EL;
			limit = EL/3;
			start = false;
		}		
		//Resource Found
		if( RE ){
			boolean[] foodChar = new boolean[] { RC, RSh, RS, RW };
			idFood = generateIdFood(foodChar);
			//Find First Good Food
			if( goodFood.isEmpty() || eatStep < 1){
				switch( eatStep ){
				case -2:
					foods.add(idFood);
					oldEL = EL;
					eatStep = -1;
					return 4;
				case -1:
					if( EL > oldEL){
						goodFood.add(idFood);
						oldEL = EL;
						if(maxEL < EL){
							maxEL = EL;
							limit = EL/3;
							System.out.println(limit);
						}
						eatStep = 0;
						return -1;
					}
					else eatStep = -2;
					break;
				case 0:
					if(fMaxEnergy(EL)) eatStep = 1;
					else return 4;
					break;
				}
			}
			//Taste New Food
			if(foods.indexOf(idFood) == -1 || eatStep == 2){
				switch( eatStep ){
				case 1:
					foods.add(idFood);
					eatStep = 2;
					oldEL = EL;
					return 4;
				case 2:
					if( EL > oldEL)goodFood.add(idFood);
					eatStep = 1;
					break;
				}
			}
			//Find Good Food And Restore Heal
			else if( goodFood.indexOf(idFood) != -1 && EL < (2*limit)){
				return 4;
			}
		}
		oldEL = EL;
		e =  haveEnergy(EL);
		if( !AF && !AD && !AA && !AI && e){
			if (!nodes.isEmpty()) {
				boolean[] walls = new boolean[] { PF, PD, PA, PI };
				Node actual = nodes.peek();
				return normalMove( actual, walls);
			} else {
				//initialize();		//Do the search again
				return -1;
			}
		} else if(!e && !goodFood.isEmpty()){
			// TODO Eater Low Energy
			return moveToFood();
		} else {
			// TODO Agent detected
			return -1;
		}
	}


	public byte generateIdFood(boolean[] foodChar){
		byte idFood = 0;
		for(int i = 0; i < foodChar.length; i++){
			if(foodChar[i]){
				idFood += (byte) Math.pow(2, i);
			}
		}
		return idFood;
	}
	
	public int moveToFood(){
		System.out.println("low energy");
		return -1;
	}
	
	public boolean haveEnergy(int EL){
		if( EL < (maxEL*1)/3){
			return false; //Se Debe Comer //TODO
		}
		return true;
	}
	
	private int normalMove(Node actual, boolean[] walls) {

		// A near path exist
		if (canMove(actual, walls)) {
			if (change) {
				int movX = -1, movY = -1;
				
				movX = (actual.pos[0] - posX);
				movY = (actual.pos[1] - posY);
				change = false;
				return movement(movX, movY);
			}

			old = nodes.pop();
			posX = actual.pos[0];
			posY = actual.pos[1];
			if (createChildren(walls, actual)) {
				parents.add(actual);
				int movX = -1, movY = -1;
				
				movX = (nodes.peek().pos[0] - posX);
				movY = (nodes.peek().pos[1] - posY);
				return movement(movX, movY); // k
			} else
				return -1;
		} else {
			// A far path exist
			return searchPath(actual);
		}
	}

	public boolean canMove(Node actual, boolean[] walls) {
		if (steps > 0) {
			boolean can = false;
			
			can = (Math.abs(posX - actual.pos[0]) + Math.abs(posY - actual.pos[1])) < 2;
			if (can && (actual.depth < old.depth))
				can = false;
			return can;
		} else
			return true;
	}

	public int searchPath(Node objetive) {
		int k = -1, movX = 0, movY = 0;
		
		change = true;
		Node parent = old.parent;
		movX = parent.pos[0] - posX;
		movY = parent.pos[1] - posY;
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
		toAdd = new ArrayList<>();
		for (int i = 0; i <= 3; i++) {
			if (!walls[i]) {
				nChilds += createState(i, actual);
			}
		}
		if (nChilds > 0){
			shuffle(nChilds);
			success = true;
		}
		return success;
	}
	
	public void shuffle(int nChilds){
		if( nChilds == 1){
			nodes.add( toAdd.remove(0) );
		} else {
			int ord = -1;
			while( !toAdd.isEmpty() ){
				/* TODO remove
				for(int i = 0; i<toAdd.size(); i++){
					System.out.println(toAdd.get(i));
				}*/
				ord = (int) (Math.random()*(toAdd.size()));
				//System.out.println("--------	" + ord );
				nodes.add( toAdd.remove(ord) );
			}
		}
	}
	
	public int createState(int i, Node node) {
		int success = 0;
		int newX = 0;
		int newY = 0;
		int newHead = (i + head) % 4;
		int state = 0;
		switch (newHead) {
		case 0:
			newY = 1;
			break;
		case 1:
			newX = 1;
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

		// build the state
		if (newX >= 0)
			state = newX * 10000;
		else {
			state = 1000;
			state += (-newX) * 10000;
		}
		if (newY >= 0)
			state += newY * 10;
		else {
			state += 1;
			state += (-newY) * 10;
		}
		
		// Verify if the position isn't visited yet
		if (states.indexOf(state) == -1) {
			Node child = new Node(newX, newY, node, node.depth + 1);
			toAdd.add(child);
			//nodes.add(child); TODO remove
			states.add(state);
			node.childs.add(child);
			success = 1;
		}

		return success;
	}

	public void initialize() {
		nodes = new Stack<>();
		parents = new Stack<>();
		states = new ArrayList<>();
		toAdd = new ArrayList<>();
		foods = new ArrayList<>();
		steps = 0;
		head = 0;
		posX = 0;
		posY = 0;
		change = false;
		// Create root node
		Node root = new Node(posX, posY, null, 0);
		old = root;
		nodes.add(root);
		states.add(0);
	}

}