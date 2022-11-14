package com.cognixia.jump.model;

import java.util.Comparator;

public class QtyCompare implements Comparator<Game>
{
    public int compare(Game g1, Game g2)
    {
    	if (g1.getQty() < g2.getQty()) return -1;
        if (g1.getQty() > g2.getQty()) return 1;
        else {
        	
        	g1.getName().compareTo(g2.getName());
        
        }
        return 0;
    }
}