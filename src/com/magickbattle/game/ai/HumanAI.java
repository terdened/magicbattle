package com.magickbattle.game.ai;

import com.magickbattle.game.GameScene;
import com.magickbattle.game.character.BatEnemy;
import com.magickbattle.game.character.HumanEnemy;

public class HumanAI extends BasicAI{

	public HumanAI(final GameScene pScene, final HumanEnemy pThisEmeny) 
	{
		super(pScene, pThisEmeny);
		addWaitState();
		addPanicState();
		addDieState();
	}
	
	private void addWaitState()
	{
		AIState tempState = new AIState(){

			@Override
			public void initState() 
			{				
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(100);
					}};
				
				this.mActionsList.add(waitAction);
				
				
				AITrigger panicTrigger = new AITrigger("panic"){

					@Override
					public Boolean getState() 
					{
						if(mThisEnemy.health!=mThisEnemy.maxHealth)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(panicTrigger);
				
				AITrigger dieTrigger = new AITrigger("die"){

					@Override
					public Boolean getState() 
					{
						if(mThisEnemy.health<=0)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(dieTrigger);
			}
		};
		
		tempState.mStateTitle="wait";
		this.mStatesList.add(tempState);
		this.mCurrentState = tempState;
		
	}
	
	private void addPanicState()
	{
		AIState tempState = new AIState(){

			@Override
			public void initState() 
			{
				AIAction goToPlayerAction = new AIAction(){

					@Override
					public void implementAction() {
						final HumanEnemy tempEnemy = (HumanEnemy)mThisEnemy;
						tempEnemy.goWhileWaiting();
					}};
				
				this.mActionsList.add(goToPlayerAction);
				
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(5);
					}};
				
				this.mActionsList.add(waitAction);
				
				
				AITrigger dieTrigger = new AITrigger("die"){

					@Override
					public Boolean getState() 
					{
						if(mThisEnemy.health<=0)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(dieTrigger);
			}
		};
		
		tempState.mStateTitle="panic";
		this.mStatesList.add(tempState);
	}
	
	private void addDieState()
	{
		AIState tempState = new AIState(){

			@Override
			public void initState() 
			{
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(0);
					}};
				
				this.mActionsList.add(waitAction);
			}
		};
		
		tempState.mStateTitle="die";
		this.mStatesList.add(tempState);
	}

	
}
