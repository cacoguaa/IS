package unalcol.agents.examples.labyrinth.multeseo.eater.ISI2017I.SCS.Eater;

public class Food {
	
	private int state;
	private byte factor;
	
	Food(int state){
		this.state = state;
		factor = 0; //Neutral
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public byte getFactor() {
		return factor;
	}

	public void setFactor(byte factor) {
		this.factor = factor;
	}
	
}
