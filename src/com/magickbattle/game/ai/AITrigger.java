package com.magickbattle.game.ai;

public abstract class AITrigger 
{
	private String mStateTitle;
	
	public AITrigger(String pStateTitle)
	{
		mStateTitle=pStateTitle;
	}
	
	public String getStateTitle()
	{
		return mStateTitle;
	}
	
	abstract public Boolean getState();
}
