package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.SCSFinal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
 

public class GraphAce{

    private  Map<Nod, ArrayList<Nod>> graph;
    private Stack<Nod> stack;
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
    	stack = new Stack<Nod>();
    	limitIte = new int[2];
    }
    
    void clear(){
    	graph.clear();
    	length = 0;
    }
    

    public ArrayList<Nod> returnParents(Nod son){
    	rst.clear();
    	Nod actual = son;
    	while( actual.getParent() != null){
    		rst.add(actual);
    		actual = actual.getParent();
    	}
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
    
    public boolean sonOf(Nod parent, Nod son){
    	boolean s = false;
    	if( graph.containsKey(parent) || graph.containsKey(son)){
    		if( graph.get(parent).contains(son) ||graph.get(son).contains(parent) ){
    			s = true;
    		}
    	}
    	return s;
    }

    /*
     
    ArrayList<Nod> findItePath(Nod root, Nod desti){
    	limitIte[0] = 0;
    	if(root.getState() == desti.getState()){
    		System.out.println("really?");
    	}
    	while( limitIte[0] < length){
    	//
    		limitIte[0]++;
    		//TODO delete
    		//System.out.println(limitIte[0]);
    		limitIte[1] = 0;
	    	found = false;
	    	stack.clear();
	    	visited.clear();
	    	rst.clear();
	    	visited.add(root.getState());
	    	rst.add(root);
	    	byte i = 0;
	    	if(graph.get(root) != null){
	    		ArrayList<Nod> n = graph.get(root);
	    		while( !found && i < n.size() && !found){
					dfsi(n.get(i),desti);
					i++;
				}
	    	} else {
	    		if(found){}
	    		else rst.clear();
	    	}
    	//
    	}
    	return rst;
    } 
    
    private boolean dfsi(Nod root,Nod desti) {
    	System.out.println(root + " " + limitIte[1]);
    	visited.add(root.getState());
		rst.add(root);
    	if(root.getState() == desti.getState()){
    		found = true;
    		System.out.println(found);
    		return true;
    	}
    	if(!found && graph.get(root) != null && limitIte[1] < limitIte[0]){
    		limitIte[1]++;
    		ArrayList<Nod> n = graph.get(root);
    		byte i = 0;
    		while( i < n.size()){
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

    */
    ArrayList<Nod> findItePath(Nod root, Nod desti){
    	if(root.getState() == desti.getState())return null;
    	limitIte[0] = 0;
    	found = false;
    	rst.clear();
    	while( limitIte[0] < length && !found){
    		limitIte[0]++;
    		limitIte[1] = 0;
    		visited.clear();
    		rst.clear();	
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
    
    public ArrayList<Nod> findExpand(Nod root, Nod desti){
    	if(root.getState() == desti.getState()) return null;
    	ArrayList<Nod> parents = new ArrayList<>();
    	Stack<Nod> sons = new Stack<>();
    	ArrayList<Integer> states = new ArrayList<>();
    	root.setParent(null);
    	sons.add(root);
    	Nod parent = null;
    	states.add(root.getState());
    	found = false;
    	while(!sons.isEmpty() && !found){
    		parent = sons.pop();
    		parents = graph.get(parent);
    		if(parents != null ){
    			for(Nod x: parents){
    					if(x.getState() == desti.getState()){
	    					found = true;
	    					//System.out.println("Objetito -> posicion x:" + x.getPos()[0]+ "  posicion y:" + x.getPos()[1]);
	    					x.setParent(parent);
	    					parent = x;
	    					//parents = returnParents(x);
	    					break;
	    				} else if(states.indexOf(x.getState())== -1){
	    					x.setParent(parent);
		    				sons.add(x);
		    				states.add(x.getState());
	    				}
	    				
    				
    			}
    		}
    	}
    	rst.clear();
    	rst = returnParents(parent);
    	rst.add(root);
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
    	ace.addLink(a, c);
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
		//TODO delete
		//System.out.println( temp + " keys");
		for(Nod x:temp){
			if( x.getState() == state) return x;
		}
		return null;

	}

    
}

