package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.SCSFinal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import javax.print.attribute.standard.Destination;
 

public class GraphAce{

    private  Map<Nod, ArrayList<Nod>> graph;
    private int length;
    
    public GraphAce(){
    	graph = new HashMap<Nod, ArrayList<Nod>>();
    	length = 0;
    	visited = new ArrayList<Integer>();
    	rst = new ArrayList<Nod>();
    	stack = new Stack<Nod>();
    }
    
    void clear(){
    	graph.clear();
    	length = 0;
    }
    
    void addEdge(Nod parent, Nod son){
    	if(parent.getState() != son.getState()){
			ArrayList<Nod> sons; 
			sons = graph.get(parent);
	    	if(!graph.isEmpty() && sons != null ){
	    		if(sons.indexOf(son) == -1){
	    			sons.add(son);
	    			graph.put(parent, sons);	
	    			length++;
	    		}
	    	}
	    	else{
	    		sons = new ArrayList<Nod>();
	    		sons.add(son);
	    		graph.put(parent, sons);	
	    		length++;
	    	}
    	}
    }
    
    int getLength(){
    	return length;
    }
    void print(Nod root){
    	System.out.print(root + " -> ");
    	for(int i = 0; i <graph.get(root).size(); i++){
    		System.out.print( graph.get(root).get(i) + ", ");
    	}
    }
    
    void findPath(Nod root, Nod desti){
    	Stack<Nod> dfs = new Stack<>();
    	ArrayList<Nod> mark = new ArrayList<>();
    	boolean found = false;
    	dfs.add(root);
    	while(!dfs.isEmpty() && !found){
    		root = dfs.pop();
    		if(root == desti) found = true;
    		if(!mark.contains(root) && !found){
    			mark.add(root);
    			if(graph.get(root) !=null)
    				for(Nod x: graph.get(root)){
	    				dfs.push(x);
	    			}
    		}
    	}
    }
    
    Stack<Nod> stack;
    ArrayList<Integer> visited;
    ArrayList<Nod> rst;
    boolean found;
    
    ArrayList<Nod> findItePath(Nod root, Nod desti){
    	if(root.getState() == desti.getState()){
    		System.out.println("really?");
    	}
    	found = false;
    	stack.clear();
    	visited.clear();
    	rst.clear();
    	stack = new Stack<Nod>();
    	int[] limit = new int[1];
    	limit[0] = 1;
    	visited.add(root.getState());
    	rst.add(root);
    	byte i = 0;
    	if(graph.get(root) != null){
    		ArrayList<Nod> n = graph.get(root);
    		while( !found && i < n.size()){
				dfsi(n.get(i),desti);
				i++;
			}
    	} else {
    		if(found){}
    		else rst.clear();
    	}
    	return rst;
    }
    
    public boolean sonOf(Nod parent, Nod son){
    	if(graph.get(parent).contains(son) ||graph.get(son).contains(parent) )	return true;
    	else return false;
    }
    
    private boolean dfsi(Nod root,Nod desti) {
    	visited.add(root.getState());
		rst.add(root);
    	if(root.getState() == desti.getState()){
    		return found = true;
    	}
    	if(graph.get(root) != null){
    		ArrayList<Nod> n = graph.get(root);
    		byte i = 0;
    		while( !found && i < n.size()){
				if(visited.indexOf(n.get(i).getState()) ==-1){
					dfsi(n.get(i),desti);
				}
				i++;
			}
    		byte c = 0;
    		if(found){
    			for(Nod x: n){
    				if(rst.contains(x)) c++;
    			}
    		}
    		if(c == 0)rst.remove(root);
    	} else {
    		rst.remove(root);
    	}
    	return false;
	}

    private boolean dfsi(Nod root) {
    	System.out.println(root);
    	visited.add(root.getState());
    	if(graph.get(root) != null){
    		for(Nod n: graph.get(root)){
				if(visited.indexOf(n.getState()) ==-1){
					dfsi(n);
				}
			}
    	}
    	return true;
	}

	public static void main(String[] args){
    	GraphAce ace = new GraphAce();
    	
//    	Nod a = new Nod(0, 0, 1, 0);
//    	Nod b = new Nod(0, 0, 2, 1);
//    	Nod c = new Nod(0, 0, 3, 1);
//    	Nod d = new Nod(0, 0, 4, 1);
//    	Nod e = new Nod(0, 0, 5, 2);
//    	Nod f = new Nod(0, 0, 6, 2);
//    	Nod g = new Nod(0, 0, 7, 2);
//    	Nod h = new Nod(0, 0, 8, 2);
//    	Nod i = new Nod(0, 0, 9, 2);
//    	Nod j = new Nod(0, 0, 10, 3);
    	
    	Nod a = new Nod(0, 0, 1);
    	Nod b = new Nod(0, 0, 2);
    	Nod c = new Nod(0, 0, 3);
    	Nod d = new Nod(0, 0, 4);
    	Nod e = new Nod(0, 0, 5);
    	Nod f = new Nod(0, 0, 6);
    	Nod g = new Nod(0, 0, 7);
    	Nod h = new Nod(0, 0, 8);
    	Nod i = new Nod(0, 0, 9);
    	Nod j = new Nod(0, 0, 10);
    	
    	ace.addEdge(a, b);
    	ace.addEdge(a, c);
    	ace.addEdge(a, d);
    	ace.addEdge(b, e);
    	ace.addEdge(b, f);
    	ace.addEdge(c, g);
    	ace.addEdge(d, h);
    	ace.addEdge(d, i);
    	ace.addEdge(g, j);
    	System.out.println(ace.sonOf(b, a));
    	ace.findItePath(a, j);
    	ace.getNode(3);
    }

	public Nod getNode(int state) {

		Set<Nod> temp = graph.keySet();
		System.out.println( temp + " keys");
		for(Nod x:temp){
			if( x.getState() == state) return x;
		}
		return null;

	}
    
}

