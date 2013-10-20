package com.example.gameactive;

import java.util.LinkedList;

public class ReceptorRandom extends Receptor
{

	public ReceptorRandom(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public int getState(GameState gameState, Memory memory)
	{
		int randNumber=(int)(Math.random()*20);
		if(randNumber==5)
			state=0;
		else
			state=1;
		
		return state;
	}
	
}
