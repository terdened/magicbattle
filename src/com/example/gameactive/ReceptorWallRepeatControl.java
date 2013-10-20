package com.example.gameactive;

import java.util.LinkedList;

public class ReceptorWallRepeatControl extends Receptor
{	
	
	public ReceptorWallRepeatControl(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getState(GameState gameState, Memory memory)
	{

		for(int i=0;i<memory.memoryList.size();i++)
		{
			if(memory.memoryList.get(i).action=="createWall")
			{
				if(memory.memoryList.get(i).time+memory.lastTime<50)
					state=4;
				else
				if(memory.memoryList.get(i).time+memory.lastTime<100)
					state=3;
				else
				if(memory.memoryList.get(i).time+memory.lastTime<150)
					state=2;
				else
				if(memory.memoryList.get(i).time+memory.lastTime<200)
					state=1;
				else
					state=0;
			}
		}
			
		return state;
	}
	
}
