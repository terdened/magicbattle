package com.example.gameactive;

import java.util.LinkedList;

public class ReceptorAnotherAttacked extends Receptor
{

	public ReceptorAnotherAttacked(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public int getState(GameState gameState, Memory memory)
	{
		
		if(gameState.enemyList.get(1).isAttackted)
			state=4;
		else
			state=0;
		
		return state;
	}
	
}
