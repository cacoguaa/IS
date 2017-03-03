package test;

import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.agents.simulate.util.SimpleLanguage;

public class AgentTest extends SimpleTeseoAgentProgram{
	
	public AgentTest( SimpleLanguage _language){
		super(_language);
	}

	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {
		// TODO Auto-generated method stub
		if( MT ) return -1;
		int f;
		int k;
		if( !PF && !PA && !PI && !PD ){
			k = (int)(Math.random()*4);
		} else if ( !PF && !PA && PI && PD ){
			k = 0;
		} else if ( PF && !PA && PI && !PD ){
			k = 1;
		} else if ( PF && !PA && !PI && PD ){
			k = 3;
		} else if ( ( !PF && !PA && PI && !PD ) || ( !PF && PA && PI && !PD ) ){
			f = (int)(Math.random()*2);
			if(f == 0){
				k = 0;
			} else {
				k = 1;
			}
		} else if ( ( !PF && !PA && !PI && PD ) || ( !PF && PA && !PI && PD ) ){
			f = (int)(Math.random()*2);
			if(f == 0){
				k = 0;
			} else {
				k = 3;
			}
		} else if ( PF && !PA && PI && PD ){
			k = 2;
		} else if ( PF && !PA && !PI && !PD ){
			k = (int)(Math.random()*3 +1);
		} else if ( PF && PA && !PI && !PD ){
			
			f = (int)(Math.random()*2);
			if(f == 0){
				k = 1;
			} else {
				k = 3;
			}
		} else {
			k = 0;
		}
		
		return k;
	}

}
