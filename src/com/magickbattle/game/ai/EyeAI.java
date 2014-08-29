package com.magickbattle.game.ai;

import com.badlogic.gdx.math.Vector2;
import com.magickbattle.game.GameScene;
import com.magickbattle.game.character.EyeEnemy;

public class EyeAI extends BasicAI{

	public EyeAI(final GameScene pScene, final EyeEnemy pThisEmeny) 
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
				AIAction goWhileWaitingAction = new AIAction(){

					@Override
					public void implementAction() {
						final EyeEnemy tempEnemy = (EyeEnemy)mThisEnemy;
						tempEnemy.goWhileWaiting();
					}};
				
				this.mActionsList.add(goWhileWaitingAction);
				
				AIAction waitAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(100);
					}};
				
				this.mActionsList.add(waitAction);
				
				AITrigger attackTrigger = new AITrigger("attack"){

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
				
				this.mTriggerList.add(attackTrigger);
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
						final EyeEnemy tempEnemy = (EyeEnemy)mThisEnemy;
						tempEnemy.attackPlayer();
					}};
				
				this.mActionsList.add(attackAction);
				
				AIAction goToPlayerAction = new AIAction(){

					@Override
					public void implementAction() {
						mThisEnemy.waitHere(100);
					}};
				
				this.mActionsList.add(goToPlayerAction);
			}
		};
		
		tempState.mStateTitle="attack";
		this.mStatesList.add(tempState);
	}
	
	private void addDieState()
	{
		
	}

	
}
