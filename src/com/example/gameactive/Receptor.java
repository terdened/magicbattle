package com.example.gameactive;

import java.util.LinkedList;


public abstract class Receptor {
	
	public int id;
	public int state;
	
	public Receptor(int id)
	{
		this.id=id;
	}
	
	public abstract int getState(GameState gameState, Memory memory);	
	
}
