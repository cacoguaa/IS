package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.SCSFinal;

import java.util.ArrayList;

import unalcol.agents.simulate.util.SimpleLanguage;

public class AceV2 extends AgentSCSFinal{
	
	private static final byte WAITSTEPS = 5;
	private GraphAce graph;
	
	private boolean[] walls;
	private boolean[] enemy;
	private boolean[] foodChar;	
	
	private ArrayList<Nod> nodes;
	private ArrayList<Nod> nodesGoodFood;
	private ArrayList<Nod> toAdd;
	private ArrayList<Nod> rst;
	private ArrayList<Integer> states;
	private ArrayList<Byte> foods;
	private ArrayList<Byte> goodFood;
	
	private int head;
	private int posX;
	private int posY;
	private int maxEL;
	private int limit;
	private int steps;
	private int eatStep;
	private int oldEL;
	private int waitStep;

	private byte idFood;
	
	private boolean start;
	private boolean change;
	private boolean test;
	private boolean jump;
	private boolean agent;
	private boolean searchingFood;
	private boolean ee;
	
	Nod actual;
	Nod old;
	
	public AceV2(SimpleLanguage _lenguage) {
		super(_lenguage);
		maxEL = 0;
		start = true;
		walls = new boolean[4];
		foodChar = new boolean[4];
		enemy = new boolean[4];
		foods = new ArrayList<>();
		goodFood = new ArrayList<>();
		rst = new ArrayList<>();
		eatStep = -2;
		initialize();
		test = false;
	};
	
	void initialize(){
		graph = new GraphAce();
		nodes = new ArrayList<>();
		states = new ArrayList<>();
		toAdd = new ArrayList<>();
		nodesGoodFood = new ArrayList<>();
		steps = 0;
		head = 0;
		posX = 0;
		posY = 0;
		waitStep = WAITSTEPS;
		Nod root = new Nod(posX, posY, 0);
		old = root;
		nodes.add(root);
		states.add(0);
		jump = false;
		agent = false;
		searchingFood = false;
	}
	
	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL, boolean AF, boolean AD,
			boolean AA, boolean AI, boolean RE, boolean RC, boolean RSh, boolean RS, boolean RW, int EL) {
		
		// Goal reach
		if ( MT ) {
			graph.clear();
			return -1;
		}
		//Read initial energy level
		if(start){
			maxEL = EL;
			limit = maxEL/3;
			start = false;
		}	
		
		//haveEnergy(EL);
		
		//if(ee){
			//Resource Found
			if( RE ){
				//Food Chars
				updateBooleans(foodChar, RC, RSh, RS, RW );
				idFood = generateIdFood();
				if(!jump){
					//Find First Good Food
					if( goodFood.isEmpty() && !foods.contains(idFood) || eatStep < 1){
						switch( eatStep ){
						case -2:
							foods.add(idFood);
							oldEL = EL;
							eatStep = -1;
							return 4;
						case -1:
							if( EL > oldEL){
								goodFood.add(idFood);
								nodesGoodFood.add(old);
								oldEL = EL;
								fMaxEnergy(EL);
								eatStep = 0;
								return -1;
							}
							else{
								if(EL < limit) jump = true;
								eatStep = -2;
							}
							break;
						case 0:
							if(fMaxEnergy(EL))eatStep = 1;
							else return 4;
							break;
						}
					}
					//Taste New Food
					if( !foods.contains(idFood) || eatStep == 2 || eatStep == 3 ){
						switch( eatStep ){
						case 1:
							eatStep = 2;
							oldEL = EL;
							return 4;
						case 2:
							foods.add(idFood);
							if( EL > oldEL){
								goodFood.add(idFood);
								nodesGoodFood.add(old);
								eatStep = 3;
							} else {
								eatStep = 1;
								if(EL < limit) jump = true;
							}
							break;
						case 3:
							if(fMaxEnergy(EL)) eatStep = 1;
							else return 4;
							break;
						}
					}
				} else{
					if(!foods.contains(idFood)) jump = false;
				}
				//Find Good Food And Restore Heal
				if( goodFood.contains(idFood) && EL < maxEL){
					jump = false;
					searchingFood = false;
					return 4;
				}
				
			}
			
			if(AF|AD|AA|AI){
				updateBooleans(enemy,AF,AD,AA,AI);
				byte enemyPos = -1;
				int enemyState = -1;
				for(byte x = 0; x < 4;x++){
					if(enemy[x]){
						enemyPos = x;
						break;
					}
				}
				enemyState = generateState(enemyPos);
				if(enemyState == nodes.get(0).getState() && rst.isEmpty()){
					if(waitStep > 0){
						waitStep--;
						//System.out.println("Run in ... " + waitStep);
						return -1;
					}
					return reactToAgent(enemyState);
				} 
			}
			//Update ActualEnergy
			oldEL = EL;
			//Update walls
			updateBooleans(walls,PF,PD,PA,PI);
			//Normal move	
			if (!nodes.isEmpty()) {
				if(!change && !agent) {
					//Search For A Near Node
					if (!canMove(nodes.get(0))){
						Nod cercano = graph.findExpandVisit(old, nodes);
						if (cercano != null) {
							if (nodes.remove(cercano)) nodes.add(0,cercano);
						}
						
					}			
					actual  = nodes.get(0);
				} else if(agent){
					actual  = nodes.get(0);
				} else{
					change = false;
				}
				return normalMove( actual );
			}
		//}
		//Nothing to do
		return -1;
	}
	
	
	public int reactToAgent(int enemyState){
		if(rst.isEmpty()){
			nodes.add(nodes.remove(0));
			agent = true;
		} else {
			//TODO TODO TODO cuando esta en camino de vuelta
			return -1;
		}
		return -1;
	}
	
	public int generateState(int i){
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
		return state;
	}

	public int normalMove(Nod actual){
		waitStep = WAITSTEPS;
		// A Near Path Exist
		if (canMove(actual) && rst.isEmpty()) {
			test = false;
			old =nodes.remove(0);
			posX = actual.getPos()[0];
			posY = actual.getPos()[1];
			if(createChildren(actual)){
				int movX = -1, movY = -1;
				movX = (nodes.get(0).getPos()[0] - posX);
				movY = (nodes.get(0).getPos()[1] - posY);
				return movement(movX, movY); // k
			}
			return -1;
		} else {
			// A Far Path Exist
			return findPath(actual);
		}
	}
	
	//Check If The Node Have Children
	public boolean canMove(Nod actual) {
		if (steps > 0) {
			boolean can = false;
			can = (Math.abs(posX - actual.getPos()[0]) + Math.abs(posY - actual.getPos()[1])) < 2;
			if (can && (!graph.sonOf(old,actual)) && old.getState() != actual.getState())
				can = false;
			return can;
		} else
			return true;
	}

	//Find A Return Path
	public int findPath(Nod objetive) {
		agent = false;
		if(rst.isEmpty() && !test){
			rst = graph.findExpand(old, objetive);
			test = true;
		}
		int k = -1, movX = 0, movY = 0;
		old = rst.remove(0);
		movX = old.getPos()[0] - posX;
		movY = old.getPos()[1] - posY;
		k = movement(movX, movY);
		posX = old.getPos()[0];
		posY = old.getPos()[1];
		return k;
	}	
	
		
	public boolean createChildren(Nod actual){
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

	//Generate A Node's State
	public int createState(int i, Nod node, boolean IsDiverting) {
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
		if (!states.contains(state)) {
			Nod child = new Nod(newX, newY, state);
			toAdd.add(child);
			states.add(state);
			graph.addLink(node, child);
			success = 1;
		} else if(brothers(state)){
			toAdd.add(removeNod(nodes,state));
			graph.addLink(node, graph.getNode(state));
			success = 1;
		}
		return success;
	}
	
	private Nod removeNod(ArrayList<Nod> nodes2, int state) {
		Nod child = null;
		for(int x = 0; x < nodes2.size(); x++){
			if(nodes2.get(x).getState() == state){
				child = nodes2.remove(x);
			}
		}
		return child;
	}

	public boolean brothers(int state){
		for(Nod x: nodes){
			if( x.getState() == state){
				return true;
			}
		}
		return false;
	}
	
	//Randomize Children Order
	public void shuffle(int nChilds){
		if( nChilds == 1){
			nodes.add(0, toAdd.remove(0) );
		} else {
			int ord = -1;
			while( !toAdd.isEmpty() ){
				ord = (int) (Math.random()*(toAdd.size()));
				nodes.add(0, toAdd.remove(ord) );
			}

		}
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

	//Update A Array Of Booleans Of Size 4
	public void updateBooleans(boolean[] update, boolean B0, boolean B1, boolean B2, boolean B3 ){
		update[0] = B0;
		update[1] = B1;
		update[2] = B2;
		update[3] = B3;
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
		oldEL = EL;
		if( oldEL > maxEL){
			maxEL = oldEL;
			limit = maxEL/2;
			return false;
		}
		else return true;
	}
	
}
