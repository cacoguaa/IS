package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import java.util.ArrayList;
import java.util.Stack;

import unalcol.agents.simulate.util.SimpleLanguage;

public class SimpleAgentCarlos extends AgentSCS {
	private Stack<Node> nodes;
	private ArrayList<Short> states;
	private int head;
	private int x;
	private int y;

	public SimpleAgentCarlos(SimpleLanguage _lenguage) {
		super(_lenguage);
		nodes = new Stack<>();
		Node node = new Node(0, 0, 0);
		nodes.add(node);
		head = 0;
		states = new ArrayList<>();
		states.add((short) 0);
		Short state = 0;
		states.add(state);
		x = 0;
		y = 0;
	};

	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, // Moves
			boolean MT, boolean FAIL // Finish, Die
	) {
		if (MT) {
			return -1;
		}
		int k = -1;
		boolean walls[] = new boolean[]{PF,PD,PA,PI};	
		if(!nodes.isEmpty()){
			Node actual = nodes.pop();
			createChildren(walls, actual);
			if (!nodes.isEmpty()) {
				k = nodes.peek().pos[2];
			}
		}
		System.out.println(k);
		return k;

	}

	public int createChildren(boolean[] walls, Node node) {
		for (int i = 0; i <= 3; i++) {
			if (!walls[i]) {
				createState(i, node);
			}
		}
		return 0;
	}

	public void createState(int i, Node node) {
		int state = 0;
		int newX = node.pos[0];
		int newY = node.pos[1];
		int newHead = (head + i) % 4;
		switch (newHead) {
		case 0:
			newY += 1;
			break;
		case 1:
			newX += 1;
			break;
		case 2:
			newY -= 1;
			break;
		case 3:
			newX -= 1;
			break;
		}
		if (newX >= 0)
			state += newX * 10;
		else {
			state += 1;
			state += (-newX) * 10;
		}
		if (newY >= 0)
			state += newY * 1000;
		else {
			state += 100;
			state += (-newY) * 1000;
		}

		if (states.indexOf((short) state) == -1) {
			System.out.println(state);
			Node child = new Node(newX, newY, newHead);
			states.add((short) state);
			nodes.add(child);
		}
	}

}
