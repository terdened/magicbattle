package com.example.gameactive;

import java.util.LinkedList;

public class Memory 
{
	public LinkedList<GameState> memoryList;
	public int memorySize;
	public int lastTime;
	
	public Memory(int memorySize)
	{
		this.memorySize=memorySize;
		memoryList=new LinkedList<GameState>();
		lastTime=0;
	}
	
	
	public void setState(GameState gameState)
	{
		for(int i=0;i<memoryList.size();i++)
			memoryList.get(i).setTime(lastTime);
		gameState.setTime(0);
		memoryList.add(gameState);
		
		lastTime=0;
		
		
		if(memoryList.size()==memorySize)
			memoryList.removeFirst();
		
	}
	
	public GameState getState(int i)
	{
		return memoryList.get(memoryList.size()-i-1);		
	}
	
	public void update()
	{
		lastTime++;
	}
	
}
