package com.example.gameactive;

import java.util.LinkedList;

public class ReceptorAttackted extends Receptor
{

	public ReceptorAttackted(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public int getState(GameState gameState, Memory memory)
	{
		
		if(gameState.enemy.isAttackted)
			state=4;
		else
			state=0;
		
		return state;
	}
	
}
