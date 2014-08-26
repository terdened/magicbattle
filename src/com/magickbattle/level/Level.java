package com.magickbattle.level;


import java.util.LinkedList;

import org.andengine.entity.Entity;

import com.example.gameactive.GameScene;

public class Level extends Entity{
	
	private final GameScene mGameScene;
	private int mWidth;
	private int mHeight;
	public LinkedList<LevelObject> mObjectsList;
	
	
	public Level(int pWidth, int pHeight, final GameScene pGameScene)
	{
		mGameScene=pGameScene;
		mWidth=pWidth;
		mHeight=pHeight;
		mObjectsList = new LinkedList<LevelObject>();
		this.setScale(pWidth, pHeight);

		InitCamera();
	}
	
	private void InitCamera()
	{
		mGameScene.camera.setBounds(0, 0, mWidth*80, mHeight*80); // here we set camera bounds
		mGameScene.camera.setBoundsEnabled(true);
	}
	
	private void attachObject(LevelObject pObject)
	{
		mObjectsList.add(pObject);
		this.attachChild(pObject);
	}

}
