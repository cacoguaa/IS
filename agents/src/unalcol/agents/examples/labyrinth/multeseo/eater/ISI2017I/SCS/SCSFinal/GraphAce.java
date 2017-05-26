package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.SCSFinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
 

public class GraphAce{

    private  Map<Nod, ArrayList<Nod>> graph;

    private ArrayList<Integer> visited;
    private ArrayList<Nod> rst;
    private int length;
    private int[] limitIte;
    boolean found;
    
    public GraphAce(){
    	graph = new HashMap<Nod, ArrayList<Nod>>();
    	length = 0;
    	visited = new ArrayList<Integer>();
    	rst = new ArrayList<Nod>();
    	limitIte = new int[2];
    }
    
    void clear(){
    	graph.clear();
    	length = 0;
    }
    
 // Busca en la lista nodes el nodo mas cercano a root
    public int findExpandDistanceFood(Nod root, ArrayList<Nod> nodes){
    	if(root.getState() != nodes.get(0).getState()){
    		ArrayList<Nod> parents = new ArrayList<>();
        	ArrayList<Nod> sons = new ArrayList<>();
        	ArrayList<Integer> states = new ArrayList<>();
        	int rstLocal = 0;
        	root.setParent(null);
        	sons.add(root);
        	Nod parent = null;
        	states.add(root.getState());
        	found = false;
        	while(!sons.isEmpty() && !found){
        		parent = sons.remove(0);
        		parents = graph.get(parent);
        		if(parents != null ){
        			for(Nod x: parents){
    					if(nodes.contains(x)){
	    					found = true;
	    					x.setParent(parent);
	    					parents = returnParents(x);
	    					rstLocal = parents.size();
	    					break;
	    				} else if(states.indexOf(x.getState())== -1){
	    					x.setParent(parent);
	    					sons.add(x);
		    				states.add(x.getState());
	    				}				
        			}
        		}
        	}
        	return rstLocal;	
    	} else
    	return 0;
    	
    }
    
 // Busca en la lista nodes el nodo mas cercano a root
    public Nod findExpandVisit(Nod root, ArrayList<Nod> nodes){
    	if(root.getState() != nodes.get(0).getState()){
    		ArrayList<Nod> parents = new ArrayList<>();
        	ArrayList<Nod> sons = new ArrayList<>();
        	ArrayList<Integer> states = new ArrayList<>();
        	Nod rstLocal = null;
        	root.setParent(null);
        	sons.add(root);
        	Nod parent = null;
        	states.add(root.getState());
        	found = false;
        	while(!sons.isEmpty() && !found){
        		parent = sons.remove(0);
        		parents = graph.get(parent);
        		if(parents != null ){
        			for(Nod x: parents){
        					if(nodes.contains(x)){
    	    					found = true;
    	    					rstLocal = x;
    	    					break;
    	    				} else if(states.indexOf(x.getState())== -1){
    		    				sons.add(x);
    		    				states.add(x.getState());
    	    				}
    	    				
        				
        			}
        		}
        	}
        	return rstLocal;	
    	} else
    	return null;
    	
    }

    // Busca en la lista nodes el nodo mas cercano a root
    public Nod findExpandFood(Nod root, ArrayList<Nod> nodes){
    	if(root.getState() != nodes.get(0).getState()){
    		ArrayList<Nod> parents = new ArrayList<>();
        	ArrayList<Nod> sons = new ArrayList<>();
        	ArrayList<Integer> states = new ArrayList<>();
        	Nod rstLocal = null;
        	root.setParent(null);
        	sons.add(root);
        	Nod parent = null;
        	states.add(root.getState());
        	found = false;
        	while(!sons.isEmpty() && !found){
        		parent = sons.remove(0);
        		parents = graph.get(parent);
        		if(parents != null ){
        			for(Nod x: parents){
        					if(nodes.contains(x)){
    	    					found = true;
    	    					rstLocal = x;
    	    					break;
    	    				} else if(states.indexOf(x.getState())== -1){
    		    				sons.add(x);
    		    				states.add(x.getState());
    	    				}
    	    				
        				
        			}
        		}
        	}
        	return rstLocal;	
    	} else
    	return null;
    	
    }
    
    // Buscar el camino mas corto de root a desti
    public ArrayList<Nod> findExpand(Nod root, Nod desti){
    	if(root.getState() == desti.getState()) return null;
    	ArrayList<Nod> parents = new ArrayList<>();
    	ArrayList<Nod> sons = new ArrayList<>();
    	ArrayList<Integer> states = new ArrayList<>();
    	root.setParent(null);
    	sons.add(root);
    	Nod parent = null;
    	states.add(root.getState());
    	found = false;
    	while(!sons.isEmpty() && !found){
    		parent = sons.remove(0);
    		parents = graph.get(parent);
    		if(parents != null ){
    			for(Nod x: parents){
    					if(x.getState() == desti.getState()){
	    					found = true;
	    					x.setParent(parent);
	    					parents = returnParents(x);
	    					break;
	    				} else if(states.indexOf(x.getState())== -1){
	    					x.setParent(parent);
		    				sons.add(x);
		    				states.add(x.getState());
	    				}
	    				
    				
    			}
    		}
    	}
    	parents.add(root);
    	parents = invest(parents);
    	return parents;
    }
    
    public ArrayList<Nod> returnParents(Nod son){
    	ArrayList<Nod> parents = new ArrayList<>(); 
    	Nod actual = son;
    	while( actual.getParent() != null){
    		parents.add(actual);
    		actual = actual.getParent();
    	}
    	return parents;
    }
    
    public ArrayList<Nod> invest(ArrayList<Nod> temp){
    	ArrayList<Nod> rst = new ArrayList<>(); 
    	for(int x = temp.size() -2; x >= 0; x--) rst.add(temp.get(x));
    	return rst;
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
    
    void addLink(Nod source, Nod desti){
    	addEdge(source, desti);
    	addEdge(desti, source);
    }
    
    int getLength(){
    	return length;
    }
    
    void print(Nod root){
    	System.out.print(root + " -> ");
    	System.out.println(graph.get(root));
    }
    
    public boolean sonOf(Nod parent, Nod son){
    	boolean s = false;
    	if( graph.containsKey(parent) || graph.containsKey(son)){
    		if( graph.get(parent).contains(son) ||graph.get(son).contains(parent) ){
    			s = true;
    		}
    	}
    	return s;
    }

    ArrayList<Nod> findItePath(Nod root, Nod desti){
    	if(root.getState() == desti.getState())return null;
    	limitIte[0] = 0;
    	found = false;
    	rst.removeAll(rst);
    	while( limitIte[0] < length && !found){
    		limitIte[0]++;
    		limitIte[1] = 0;
    		visited.removeAll(visited);
    		rst.removeAll(rst);	
    		visited.add(root.getState());
    		rst.add(root);
    		byte i = 0;
    		if(graph.get(root) != null){
    			ArrayList<Nod> n = graph.get(root);
	    		while(i < n.size() && !found){
	    			if(!visited.contains(n.get(i).getState())){
	    				dfsi(n.get(i),desti);
	    			}
					i++;
				}
    		}
    	}
    	return rst;
    }
    
    private boolean dfsi(Nod current,Nod desti) {
    	if(current.getState() == desti.getState()) {
    		rst.add(current);
    		return found = true;
    	}
    	else{
    		visited.add(current.getState());
    		rst.add(current);
    		if(graph.get(current) != null && limitIte[1] < limitIte[0]){
    			ArrayList<Nod> n = graph.get(current);
    			byte i = 0;
    			while(i < n.size() && !found){
    				limitIte[1]++;
	    			if(!visited.contains(n.get(i).getState())){
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
        		if(c == 0)rst.remove(current);
    		} else {
        		rst.remove(current);
        		}
    		return false;
    	}
    }

	public static void main(String[] args){
    	GraphAce ace = new GraphAce();
    	
    	
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
    	
    	ace.addLink(a, b);
    	ace.addLink(d, j);
    	ace.addLink(a, d);
    	ace.addLink(b, e);
    	ace.addLink(b, f);
    	ace.addLink(c, g);
    	ace.addLink(d, h);
    	ace.addLink(d, i);
    	ace.addLink(g, j);
    	ace.addLink(a, c);
    	System.out.println(ace.sonOf(a, b));
    	ace.print(a);
    	System.out.println( ace.findExpand(a, j) );
    }

	public Nod getNode(int state) {

		Set<Nod> temp = graph.keySet();
		for(Nod x:temp){
			if( x.getState() == state) return x;
		}
		return null;
	}
	
	 // Busca en la lista nodes el nodo mas cercano a root
    public Nod findOtherPath(Nod root, ArrayList<Nod> nodes, int enemyState){
    	if(root.getState() != nodes.get(0).getState()){
    		ArrayList<Nod> parents = new ArrayList<>();
        	ArrayList<Nod> sons = new ArrayList<>();
        	ArrayList<Integer> states = new ArrayList<>();
        	Nod rstLocal = null;
        	root.setParent(null);
        	sons.add(root);
        	Nod parent = null;
        	states.add(root.getState());
        	states.add(enemyState);
        	found = false;
        	while(!sons.isEmpty() && !found){
        		parent = sons.remove(0);
        		parents = graph.get(parent);
        		if(parents != null ){
        			for(Nod x: parents){
    					if(nodes.contains(x) && x.getState()!=enemyState){
	    					found = true;
	    					rstLocal = x;
	    					break;
	    				} else if(states.contains(x.getState())){
		    				sons.add(x);
		    				states.add(x.getState());
	    				}
        			}
        		}
        	}
        	return rstLocal;	
    	} else
    	return null;
    	
    }

	public ArrayList<Nod> findOtherPathExpand(Nod root, Nod desti, int enemyState) {
		if(root.getState() == desti.getState()) return null;
    	ArrayList<Nod> parents = new ArrayList<>();
    	ArrayList<Nod> sons = new ArrayList<>();
    	ArrayList<Integer> states = new ArrayList<>();
    	root.setParent(null);
    	sons.add(root);
    	Nod parent = null;
    	states.add(root.getState());
    	states.add(enemyState);
    	found = false;
    	while(!sons.isEmpty() && !found){
    		parent = sons.remove(0);
    		parents = graph.get(parent);
    		if(parents != null ){
    			for(Nod x: parents){
    					if(x.getState() == desti.getState() && !states.contains(x.getState())){
	    					found = true;
	    					x.setParent(parent);
	    					parents = returnParents(x);
	    					break;
	    				} else if(!states.contains(x.getState())){
	    					x.setParent(parent);
		    				sons.add(x);
		    				states.add(x.getState());
	    				}
	    				
    				
    			}
    		}
    	}
    	parents.add(root);
    	parents = invest(parents);
    	return parents;
	}

}

