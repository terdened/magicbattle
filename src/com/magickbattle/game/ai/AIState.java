package com.magickbattle.game.ai;

import java.util.LinkedList;

public abstract class AIState 
{
	protected LinkedList<AIAction> mActionsList;
	protected LinkedList<AITrigger> mTriggerList;
	protected int mCurrentAction;
	protected String mStateTitle;
	
	public AIState()
	{
		mActionsList = new LinkedList<AIAction>();
		mTriggerList = new LinkedList<AITrigger>();
		mCurrentAction=0;
		initState();
	}
	
	public String getTriggerState()
	{
		String result="none";
		
		for(int i=0;i<mTriggerList.size();i++)
		{
			if(mTriggerList.get(i).getState())
				result = mTriggerList.get(i).getStateTitle();
		}
		
		return result;
	}
	
	abstract public void initState();
	
	public Boolean isStateEqual(String pStateTitle)
	{
		if(pStateTitle==mStateTitle)
			return true;
		else 
			return false;
	}
	
	public void implementAction()
	{
		mActionsList.get(mCurrentAction).implementAction();
		mCurrentAction++;
		
		if(mCurrentAction>=mActionsList.size())
			mCurrentAction=0;
	}

}
