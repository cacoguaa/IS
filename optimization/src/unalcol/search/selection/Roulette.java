package unalcol.search.selection;

import unalcol.random.integer.IntRoulette;
import unalcol.sort.Order;

public class Roulette implements QualityBasedSelection<Double>{
	
	protected boolean non_negative_values( Double[] x ){
		int i=0; 
		while( i<x.length && x[i] >= 0.0 ) i++;
		return i==x.length;
	}
	
	protected boolean non_positive_values( Double[] x ){
		int i=0; 
		while( i<x.length && x[i] <= 0.0 ) i++;
		return i==x.length;
	}
	
	protected boolean reversed_order( Order<Double> order ){
		return (order.compare(0.0, 1.0) > 0);
	}
	
	protected double[] get( Double[] x, Order<Double> order ){
		double[] y = new double[x.length];
		boolean reversed = reversed_order(order);
		boolean non_negative = non_negative_values(x);
		boolean non_positive = non_positive_values(x);
		if( non_negative ){
			if( !reversed ){
				double MIN = x[0];
				double MAX = x[0];
				for( int i=1; i<x.length; i++ ){
					if( x[i] < MIN ) MIN = x[i];
					else if( MAX < x[i] ) MAX = x[i];
				}
				double LENGTH = MAX+MIN;
				for( int i=0; i<x.length; i++ )
					y[i] = LENGTH - x[i];
			}else{
				for( int i=0; i<x.length; i++ ) y[i] = x[i];
			}
		}else{
			if( non_positive ){
				if( !reversed ){
					double MIN = x[0];
					double MAX = x[0];
					for( int i=1; i<x.length; i++ ){
						if( x[i] < MIN ) MIN = x[i];
						else if( MAX < x[i] ) MAX = x[i];
					}
					double LENGTH = MAX+MIN;
					for( int i=0; i<x.length; i++ )
						y[i] = x[i] - LENGTH;
				}else{
					for( int i=0; i<x.length; i++ ) y[i] = -x[i];
				}
			}else{
				
			}			
		}
		return y;
	}

	@Override
	public int[] apply(int n, Double[] x, Order<Double> order) {
		IntRoulette roulette = new IntRoulette(n);
		return null;
	}

	@Override
	public int choose_one(Double[] x, Order<Double> order) {
		// TODO Auto-generated method stub
		return 0;
	}

}
