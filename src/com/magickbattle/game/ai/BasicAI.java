package com.magickbattle.game.ai;

import com.magickbattle.game.GameScene;

public abstract class BasicAI {
	
	protected String mState;
	protected final GameScene mScene;
	
	public BasicAI(final GameScene pScene)
	{
		mScene=pScene;
	}
	
	abstract public void update();

}
