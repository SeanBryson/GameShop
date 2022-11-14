package com.cognixia.jump.model;

import java.util.Comparator;

public class EsrbCompare implements Comparator<Game>
{
    public int compare(Game g1, Game g2)
    {
    	String rate1 = "RP";
    	String rate2 = "RP";
    	
    	if (g1.getEsrb().compareTo("M") == 0) {
    		rate1 = "Z";	
    	} else {
    		rate1 = g1.getEsrb();
    	}
    			
    	if (g2.getEsrb().compareTo("M") == 0) {
    		rate2 = "Z";	
    	} else {
    		rate2 = g2.getEsrb();
    	}
    	
    	// doesn't take into account M being highest
//    	return g1.getEsrb().compareTo(g2.getEsrb());
    	
    	// ESRB could have been ENUM from the start but this method works
    	return rate1.compareTo(rate2);
    }
}