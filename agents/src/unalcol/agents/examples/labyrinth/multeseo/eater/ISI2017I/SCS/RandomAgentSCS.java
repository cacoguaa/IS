package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS;

import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.agents.simulate.util.SimpleLanguage;

public class RandomAgentSCS extends SimpleTeseoAgentProgram {

	public RandomAgentSCS(SimpleLanguage _language) {
		super(_language);
	}
	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {

		if (MT)
			return -1;
		int f;
		int k = 0;
		if( PF && PD && PA && PI){ //Encerrado
			k = -1;
		} else if ( !PF && PD && PA && PI){ //Sin Pared Alfrente
			k = 0;
		} else if ( PF && !PD && PA && PI){ //Sin Pared Derecha
			k = 1;
		} else if ( PF && PD && !PA && PI){ //Sin Pared Abajo
			k = 2;
		} else if ( PF && PD && PA && !PI){ //Sin Pared Izquierda
			k = 3;
		} else if ( PF && !PD && PA && !PI){ //Pared Alfrente Pared Atras
			f = (int)(Math.random()*3);
			if( f == 0 ){
				k = 1;
			} else {
				k = 3;
			}
		} else if ( (!PF && PD && !PA && PI) 	//Pared Izquierda Pared Derecha
				|| (!PF && PD && PA && !PI) 	//Pared Derecha Pared Atras
				|| (!PF && !PD && PA && PI) ){ 	//Pared Izquierda Pared Atras
			k = 0;
		} else if ( PF && !PD && !PA && !PI){ //Pared Alfrente 3 abiertas
			k = (int)(Math.random()*3) + 1;
		} else if ( !PF && !PD && PA && !PI){ //Pared Atras 3 abiertas
			f = (int)(Math.random()*3);
			if( f == 0 ){
				k = 0;
			} else if (f == 1){
				k = 1;
			} else {
				k = 1;
			}
		} else if ( !PF && !PD && !PA && !PI){ //4 abiertas
			k = (int)(Math.random()*4);
		} else if ( !PF && PD && !PA && !PI){ //Pared Derecha 3 abiertas
			f = (int)(Math.random()*2);
			if( f == 0 ){
				k = 0;
			} else {
				k = 3;
			}
		} else if ( !PF && !PD && !PA && PI){ //Pared Izquierda 3 abiertas
			f = (int)(Math.random()*2);
			if( f == 0 ){
				k = 0;
			} else {
				k = 1;
			}
		} else if ( PF && PD && !PA && !PI){ //Pared Alfrente y Derecha
			k = 3;
		} else if ( PF && !PD && !PA && PI){ //Pared Alfrente y Izquierda
			k = 1;
		} else {
			k = -1;
		} 
		return k;
	}

}
