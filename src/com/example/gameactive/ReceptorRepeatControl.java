package com.example.gameactive;

import java.util.LinkedList;

public class ReceptorRepeatControl extends Receptor
{	
	
	public String controlObject;
	public float timeList[];
	
	public ReceptorRepeatControl(int id,String controlObject, float timeList[]) {
		super(id);
		this.controlObject=controlObject;
		this.timeList=timeList;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getState(GameState gameState, Memory memory)
	{

		for(int i=0;i<memory.memoryList.size();i++)
		{
			if(memory.memoryList.get(i).action.equals(controlObject))
			{
				if(memory.memoryList.get(i).time+memory.lastTime<timeList[0])
					state=4;
				else
				if(memory.memoryList.get(i).time+memory.lastTime<timeList[1])
					state=3;
				else
				if(memory.memoryList.get(i).time+memory.lastTime<timeList[2])
					state=2;
				else
				if(memory.memoryList.get(i).time+memory.lastTime<timeList[3])
					state=1;
				else
					state=0;
			}
		}
			
		return state;
	}
	
}
