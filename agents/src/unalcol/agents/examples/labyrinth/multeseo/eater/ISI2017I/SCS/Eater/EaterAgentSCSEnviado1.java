package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.Eater;

import java.util.ArrayList;
import java.util.Stack;

//import com.sun.corba.se.impl.orbutil.graph.Node;

import unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.Eater.AgentSCSEater;
import unalcol.agents.simulate.util.SimpleLanguage;

public class EaterAgentSCSEnviado1 extends AgentSCSEater {
	
	public static final int MAXSTEPS = 2;
	public static final int MAXDEVIATE = 2;
	private Stack<Node> nodes;
	private Stack<Node> parents;
	
	private Stack<Node> ndeviate;
	
	private ArrayList<Integer> states;
	private ArrayList<Byte> foods;
	private ArrayList<Byte> goodFood;
	private ArrayList<Node> toAdd;
	//private ArrayList<Byte> moveRandom;
	
	boolean[] walls;
	boolean[] enemy;
	boolean[] foodChar;	
	
	private int head;
	private int posX;
	private int posY;
	private int steps;
	private int maxEL;
	private int oldEL;
	private int limit;
	private int waitSteps;
	
	private int deviate;

	private byte idFood;
	private byte eatStep;
	
	private boolean change;
	private boolean start;
	private boolean e;

	Node old;
	Node oldDeviate;

	public EaterAgentSCSEnviado1(SimpleLanguage _lenguage) {
		super(_lenguage);
		goodFood = new ArrayList<Byte>(16);
		walls = new boolean[4];
		enemy = new boolean[4];
		foodChar = new boolean[4];
		//moveRandom = new ArrayList<Byte>(4);
		waitSteps = MAXSTEPS;
		maxEL = 0;
		eatStep = -2;
		start = true;
		// initialize all
		initialize();
	};
	
	//Initialize
	public void initialize() {
		if( !start ){
			nodes.clear();
			parents.clear();
			states.clear();
			toAdd.clear();
		}
		nodes = new Stack<>();
		parents = new Stack<>();
		ndeviate= new Stack<>();
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
		oldDeviate = null;
		nodes.add(root);
		states.add(0);
		
		deviate= 0;
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
			//Food Chars
			readBooleans(foodChar, RC, RSh, RS, RW );
			idFood = generateIdFood();
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
			else if( goodFood.indexOf(idFood) != -1 && EL < (3*limit)){
				return 4;
			}
		}
		
		//Update ActualEnergy
		oldEL = EL;
		//Check If Agent's Energy Is Enough
		e =  haveEnergy(EL);
		e = true; //TODO
		//Update Walls
		readBooleans( walls, PF, PD, PA, PI );
		
		//Enemy detected
		boolean enemydetected = false;
		if( AF || AD || AA || AI ) enemydetected = true;
		//Is diverting
		if (deviate > 0){
			
			if( enemydetected ){
				readBooleans(enemy,AF,AD,AA,AI);
				return reactToAgent();
			}
			else return toReturn();
		}
		
		//Enemy Agent Detected
		if( enemydetected ){
			readBooleans(enemy,AF,AD,AA,AI);
			return reactToAgent();
		}
		
		//Normal Move With Enough Energy
		else if(e ){
			waitSteps = MAXSTEPS;
			//Have Nodes To Visit
			if (!nodes.isEmpty()) {
				Node actual = nodes.peek();
				return normalMove( actual );
			} else {
				//initialize();		//TODO Do the search again
				return -1;
			}
		} else if(!e && !goodFood.isEmpty()){
			// TODO Eater Low Energy
			return moveToFood();
		} else {
			// TODO Find Good Food (Empty Good Food)
			return 0;
		}
	}
	
	//Generate An Unique Id To Specific Food
	public byte generateIdFood(){
		byte idFood = 0;
		for(int i = 0; i < foodChar.length; i++){
			if(foodChar[i]){
				idFood += (byte) Math.pow(2, i);
			}
		}
		return idFood;
	}

	//Update Agent's Max Energy
	public boolean fMaxEnergy(int EL){
		if( EL > maxEL){
			maxEL = EL;
			limit = EL/3;
			return false;
		}
		else return true;
	}
	
	//Find Path To Good Food
	public int moveToFood(){
		waitSteps = MAXSTEPS;
		System.out.println("low energy"); //TODO
		return -1;
	}
	
	//React To A Near Opponent Agent
	public int reactToAgent(){
		//Enough Energy To Wait And Remind Wait Steps
		if(e && waitSteps > 0){
			System.out.println("Arrancar en: " + waitSteps + "...");
			waitSteps--;
			return -1;		
		}
		else{
			//waitSteps = MAXSTEPS;
			//TODO Find other path
			System.out.println("actual - posX" + posX + " posY " + posY);
			//System.out.println("next - posX" + nodes.peek().pos[0] + " posY " + nodes.peek().pos[1]);
			if (deviate == MAXDEVIATE) return -1;
			else return moveDeviate();
		}
	}
	
	// Move To a Empty Space
	public int moveDeviate(){
		
		int cnt = 0;
		int r = -1;
		ArrayList<Byte> moveRandom = new ArrayList<Byte>(4);
		for( byte i = 0; i < enemy.length; i++){
			if( !walls[i] && !enemy[i] && moveRandom.indexOf(i) == -1){
				moveRandom.add(i);
				cnt++;
			}
		}
		if(cnt > 0){
			if (deviate == 0) oldDeviate = new Node(posX,posY,null,0);
			r = (int)(Math.random()*cnt);
			r = moveRandom.get(r);
			createState(r, oldDeviate, true);
			deviate++;
			Node actual = ndeviate.peek();
			return MoveDeviateAction( actual);
		} else {
			return -1;
		}

	}
	
	
	//Move Deviate
	private int MoveDeviateAction(Node actual) {
		
		int movX = -1, movY = -1;
		movX = (actual.pos[0] - posX);
		movY = (actual.pos[1] - posY);
		posX = actual.pos[0];
		posY = actual.pos[1];
		return movement(movX, movY);
	}
	
	// retorna al anterior nodo 
		public int toReturn(){
			
			deviate--;
			ndeviate.pop();
			Node destination;
			if (deviate > 0)destination = ndeviate.peek();
			else destination = oldDeviate;
			return MoveDeviateAction(destination);
		}
		
	//Print Array's Elements
	public void printBooleans(boolean[] print){
		int l = print.length;
		for(int i = 0; i < l; i++){
			System.out.print( print[i] + " " );
		}
		System.out.println();
	}
	
	//Check If There Are Enough Energy
	public boolean haveEnergy(int EL){
		if( EL < (maxEL*1)/3){
			return false; //Se Debe Comer //TODO
		}
		return true;
	}
	
	//Normal Move
	private int normalMove(Node actual) {
		// A Near Path Exist
		if (canMove(actual)) {
			//If Return On a Path
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
			//Verify And Create Childs
			if (createChildren(actual)) {
				parents.add(actual);
				int movX = -1, movY = -1;
				
				movX = (nodes.peek().pos[0] - posX);
				movY = (nodes.peek().pos[1] - posY);
				return movement(movX, movY); // k
			} else
				return -1;
		} else {
			// A Far Path Exist
			return searchPath(actual);
		}
	}
	
	//Check If The Node Have Childs
	public boolean canMove(Node actual) {
		if (steps > 0) {
			boolean can = false;
			can = (Math.abs(posX - actual.pos[0]) + Math.abs(posY - actual.pos[1])) < 2;
			if (can && (actual.depth < old.depth))
				can = false;
			return can;
		} else
			return true;
	}
	
	//Find A Return Path
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

	//Return The Direction Move
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

	//Return Direction Move Without Update The Head
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

	//Create All Node's Childs
	public boolean createChildren( Node actual) {
		int nChilds = 0;
		boolean success = false;
		toAdd = new ArrayList<>();
		for (int i = 0; i <= 3; i++) {
			if (!walls[i]) {
				nChilds += createState(i, actual, false);
			}
		}
		if (nChilds > 0){
			shuffle(nChilds);
			success = true;
		}
		return success;
	}
	
	//Randomize Children Order
	public void shuffle(int nChilds){
		if( nChilds == 1){
			nodes.add( toAdd.remove(0) );
		} else {
			int ord = -1;
			while( !toAdd.isEmpty() ){
				ord = (int) (Math.random()*(toAdd.size()));
				nodes.add( toAdd.remove(ord) );
			}
		}
	}
	
	//Generate A Node's State
	public int createState(int i, Node node, boolean IsDiverting) {
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
		
		//If it is being returned
		if (IsDiverting) {
			Node child = new Node(newX, newY, node, node.depth + 1);
			ndeviate.add(child);
			success = 1;
		}
		
		// Verify if the position isn't visited yet
		if (states.indexOf(state) == -1 && !IsDiverting) {
			Node child = new Node(newX, newY, node, node.depth + 1);
			toAdd.add(child);
			states.add(state);
			node.childs.add(child);
			success = 1;
		}

		return success;
	}

	//Update A Array Of Booleans Of Size 4
	public void readBooleans(boolean[] update, boolean B0, boolean B1, boolean B2, boolean B3 ){
		update[0] = B0;
		update[1] = B1;
		update[2] = B2;
		update[3] = B3;
	}


}