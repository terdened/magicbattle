package com.magickbattle.game.ai;

import java.util.LinkedList;

import com.magickbattle.game.GameScene;
import com.magickbattle.game.character.Enemy;

public abstract class BasicAI {
	
	protected String mState;
	protected LinkedList<AIState> mStatesList;
	protected AIState mCurrentState;
	
	protected final Enemy mThisEnemy; 
	protected final GameScene mScene;
	
	public BasicAI(final GameScene pScene, final Enemy pThisEmeny)
	{
		mScene=pScene;
		mThisEnemy=pThisEmeny;
		mStatesList= new LinkedList<AIState>();
	}
	
	public void update() 
	{

		String triggerState=mCurrentState.getTriggerState();
		if(triggerState!="none")
		{
			for(int j=0;j<mStatesList.size();j++)
			{
				if(mStatesList.get(j).isStateEqual(triggerState))
				{
					this.mCurrentState=mStatesList.get(j);
					break;
				}
			}
		}
		else
		{
			mCurrentState.implementAction();
		}
	}
}
