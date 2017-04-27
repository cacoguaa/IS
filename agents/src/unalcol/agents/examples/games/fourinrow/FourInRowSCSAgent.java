package unalcol.agents.examples.games.fourinrow;

import java.util.ArrayList;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;

public class FourInRowSCSAgent implements AgentProgram {

    protected String color;
    protected String colorOp;
    ArrayList<Integer> states;
    boolean start;
    Cell[][] board;
    public FourInRowSCSAgent( String color ){
        this.color = color;  
        
		if(color.equals("black"))
			colorOp = (String)FourInRow.WHITE;
		else
			colorOp = (String)FourInRow.BLACK;
        states = new ArrayList<Integer>();
        start = true;
    }
    
    @Override
    public Action compute(Percept p) {        
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.getAttribute(FourInRow.TURN).equals(color) ){
        	int n = Integer.parseInt((String)p.getAttribute(FourInRow.SIZE)); //Board Size
        	int state = 0;
        	int i = 0;
            int j = 0;
        	if(start){
        		board = new Cell[n][n];
        		for(int k = 0;k < n; k++)
        			for(int l = 0; l < n; l++)
        				board[k][l] = new Cell();
        		for(int c=0; c < n; c++)
    			board[n-1][c].setId(0);
        		start = false;
        		
        		for( int x = 0; x < n; x++){
    				if(p.getAttribute((n-1)+":"+x).equals(colorOp)){
    					state = n-1;
    					state += x*1000;
    					states.add(state);
    					board[n-1][x].setColor(colorOp);
    					board[n-1][x].setState(state);
    					board[n-1][x].setId(1);
    					board[n-2][x].setId(0);
    					j = x;
    				}
    				else if(p.getAttribute((n-1)+":"+x).equals(FourInRow.SPACE)){
    					//board[n-1][x] = new Cell();
    					
    				} 
        		}
        		for( int m = 0; m < n; m++){
                	for( int l = 0; l < n; l++){
                		System.out.print(board[m][l].id + " ");
                	}
                	System.out.println();
            	}
        		
    			i = n-1;
        		
    			if(j != (n/2))
    				j = n/2;
    			else{
    				int r = (int)(2*Math.random());
        			if(r == 1){
        				j = n/2 + 2;
        			}else
        				j = n/2 - 2;
    			}
    			/*if( r == 1 && j != 0){
        			j = 0;
        		} else if(r == 0 && j != (n/2) ) {
        			j = (n/2);
        		} else if(j != n-1){
        			j = n-1;
        		}*/
				state = n-1;
				state += j*1000;
				states.add(state);
				board[i][j].setColor(color);
				board[i][j].setState(state);
				board[i][j].setId(2);
    			return new Action( i+":"+j+":"+color );
        	}
        	boardRef(n,p);
        	i = (int)(n*Math.random());
            j = (int)(n*Math.random());  
            boolean flag = board[i][j].id != 0;
            while( flag ){
                i = (int)(n*Math.random());
                j = (int)(n*Math.random());
                flag = board[i][j].id != 0;
            }
        	board[i][j].setColor(color);
        	board[i][j].setState(i+j*1000);
        	board[i][j].setId(2);
        	return new Action( i+":"+j+":"+color );
        }
        return new Action(FourInRow.PASS);
    }
    
    public void boardRef(int n,Percept p){
    	int state;

    	for( int i = 0; i < n; i++){
    		for( int j =0; j < n; j++){
    			if(p.getAttribute(i+":"+j).equals(colorOp)){
					state = i;
					state += j*1000;
					states.add(state);
					board[i][j].setColor(colorOp);
					board[i][j].setState(state);
					board[i][j].setId(1);
					board[i-1][j].setId(0);
    			}
    		}
    	}
    	System.out.println();
    	for( int i = 0; i < n; i++){
    		for( int j =0; j < n; j++){
    			System.out.print(board[i][j].id + " ");
    		}
    		System.out.println();
    	}
    }

    @Override
    public void init() {
    }
}
