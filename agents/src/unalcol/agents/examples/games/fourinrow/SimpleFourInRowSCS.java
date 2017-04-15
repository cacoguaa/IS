
package unalcol.agents.examples.games.fourinrow;

import java.util.ArrayList;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;

public class SimpleFourInRowSCS implements AgentProgram {
    protected String color;
    ArrayList<Integer> states;
    public SimpleFourInRowSCS( String color ){
        this.color = color;        
        states = new ArrayList<Integer>();
    }
    
    @Override
    public Action compute(Percept p) {        
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.getAttribute(FourInRow.TURN).equals(color) ){
        	int n = Integer.parseInt((String)p.getAttribute(FourInRow.SIZE));
        	checkBoard(n,p);
            int i = (int)(n*Math.random());
            int j = (int)(n*Math.random());
            int state = i + j*100;
            boolean flag =(states.indexOf(state) == -1) && (i==n-1);
            if( i+1 < n ) flag = flag || !p.getAttribute((i+1)+":"+j).equals((String)FourInRow.SPACE);
            System.out.println(state);
            while( !flag ){
                i = (int)(n*Math.random());
                j = (int)(n*Math.random());
                flag = (states.indexOf(state) == -1) && (i==n-1);
                if( i+1 < n ) flag = flag || !p.getAttribute((i+1)+":"+j).equals((String)FourInRow.SPACE);
            }
            states.add( state );
            System.out.println(state);
            return new Action( i+":"+j+":"+color );
        }
        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {
    }
    
    public void checkBoard(int n, Percept p){
    	for( int i = 0; i < n; i++){
    		for( int j = 0; j < n; j++){
    			if( !p.getAttribute(i+":"+j).equals((String)FourInRow.SPACE)){
    				states.add(  i + j*100);
    			}
        	}
    	}
    	
    }
    
}