package com.magickbattle.game.ai;

import com.badlogic.gdx.math.Vector2;
import com.magickbattle.game.GameScene;
import com.magickbattle.game.character.BatEnemy;

public class BatAI extends BasicAI{

	public BatAI(final GameScene pScene, final BatEnemy pThisEmeny) 
	{
		super(pScene, pThisEmeny);
		addWaitState();
		addGoToPlayerState();
		addAttackState();
		addDieState();
	}
	
	private void addWaitState()
	{
		AIState tempState = new AIState(){

			@Override
			public void initState() 
			{
				AIAction goWhileWaitingAction = new AIAction(){

					@Override
					public void implementAction() {
						final BatEnemy tempEnemy = (BatEnemy)mThisEnemy;
						tempEnemy.goWhileWaiting();
					}};
				
				this.mActionsList.add(goWhileWaitingAction);
				
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(100);
					}};
				
				this.mActionsList.add(waitAction);
				
				AITrigger distanceTrigger = new AITrigger("goToPlayer"){

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
				
				AITrigger goToPlayerTrigger = new AITrigger("goToPlayer"){

					@Override
					public Boolean getState() 
					{
						if(mThisEnemy.health!=mThisEnemy.maxHealth)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(goToPlayerTrigger);
				
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
	
	private void addGoToPlayerState()
	{
		AIState tempState = new AIState(){

			@Override
			public void initState() 
			{
				AIAction goToPlayerAction = new AIAction(){

					@Override
					public void implementAction() {
						final BatEnemy tempEnemy = (BatEnemy)mThisEnemy;
						tempEnemy.goToPlayer(75);
					}};
				
				this.mActionsList.add(goToPlayerAction);
				
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(0);
					}};
				
				this.mActionsList.add(waitAction);
				
				AITrigger attackTrigger = new AITrigger("attack"){

					@Override
					public Boolean getState() 
					{
						Vector2 distance = new Vector2();
						
						distance.x = mThisEnemy.getX()- mScene.player.getX();
						distance.y = mThisEnemy.getY()- mScene.player.getY();
						
						if(distance.len()<=100)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(attackTrigger);
				
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
		
		tempState.mStateTitle="goToPlayer";
		this.mStatesList.add(tempState);
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
						final BatEnemy tempEnemy = (BatEnemy)mThisEnemy;
						tempEnemy.attackPlayer();
					}};
				
				this.mActionsList.add(attackAction);
				
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(75);
					}};
				
				this.mActionsList.add(waitAction);
				
				AITrigger goToPlayerTrigger = new AITrigger("goToPlayer"){

					@Override
					public Boolean getState() 
					{
						Vector2 distance = new Vector2();
						
						distance.x = mThisEnemy.getX()- mScene.player.getX();
						distance.y = mThisEnemy.getY()- mScene.player.getY();
						
						if(distance.len()>100)
							return true;
						else
							return false;
					}
				};
				
				this.mTriggerList.add(goToPlayerTrigger);
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
