package com.magickbattle.game.ai;

import com.badlogic.gdx.math.Vector2;
import com.magickbattle.game.GameScene;
import com.magickbattle.game.character.PlantEnemy;

public class PlantAI extends BasicAI{

	public PlantAI(final GameScene pScene, final PlantEnemy pThisEmeny) 
	{
		super(pScene, pThisEmeny);
		addWaitState();
		addAttackState();
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
						mThisEnemy.waitHere(50);
					}};
				
				this.mActionsList.add(waitAction);
				
				AIAction healAction = new AIAction(){

					@Override
					public void implementAction() {
						final PlantEnemy tempEnemy = (PlantEnemy)mThisEnemy;
						tempEnemy.heal(10);
					}};
				
				this.mActionsList.add(healAction);
				
				AITrigger distanceTrigger = new AITrigger("attack"){

					@Override
					public Boolean getState() 
					{
						Vector2 distance = new Vector2();
						
						distance.x = mThisEnemy.getX()- mScene.player.getX();
						distance.y = mThisEnemy.getY()- mScene.player.getY();
						
						if(distance.len()<500)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(distanceTrigger);
				
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
	
	private void addAttackState()
	{
		AIState tempState = new AIState(){

			@Override
			public void initState() 
			{				
				AIAction attackAction = new AIAction(){

					@Override
					public void implementAction() {
						final PlantEnemy tempEnemy = (PlantEnemy)mThisEnemy;
						tempEnemy.attackPlayer();
					}};
				
				this.mActionsList.add(attackAction);
				
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(100);
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
				
				AITrigger distanceTrigger = new AITrigger("wait"){

					@Override
					public Boolean getState() 
					{
						Vector2 distance = new Vector2();
						
						distance.x = mThisEnemy.getX()- mScene.player.getX();
						distance.y = mThisEnemy.getY()- mScene.player.getY();
						
						if(distance.len()>=500)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(distanceTrigger);
			}
		};
		
		tempState.mStateTitle="attack";
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
