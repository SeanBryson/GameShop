package com.cognixia.jump.model;

import java.util.Comparator;

public class NameCompare implements Comparator<Game>
{
    public int compare(Game g1, Game g2)
    {
    	 return g1.getName().compareTo(g2.getName());
    }
}