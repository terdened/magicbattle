package com.magickbattle.game.level;


import java.util.LinkedList;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import com.magickbattle.game.GameScene;

public class Level extends Entity{
	
	private final GameScene mGameScene;
	private int mWidth;
	private int mHeight;
	
	private Entity mBackLayer;
	private Entity mMiddleLayer;
	private Entity mFrontLayer;
	
	public LinkedList<LevelObject> mObjectsList;
	
	public Level(int pWidth, int pHeight, final GameScene pGameScene)
	{
		mGameScene=pGameScene;
		mWidth=pWidth;
		mHeight=pHeight;
		mObjectsList = new LinkedList<LevelObject>();
		//this.set(pWidth, pHeight);
		
		mBackLayer = new Entity();
		mMiddleLayer = new Entity();
		mFrontLayer = new Entity();

		this.attachChild(mBackLayer);
		this.attachChild(mMiddleLayer);
		mGameScene.mTopLayer.attachChild(mFrontLayer);
		
		InitCamera();
	}
	
	private void InitCamera()
	{
		mGameScene.camera.setBounds(0, 0, mWidth*80, mHeight*80); // here we set camera bounds
		mGameScene.camera.setBoundsEnabled(true);
	}
	
	public void attachBackground(Sprite pBackground)
	{
		float width=mWidth*80;
		float height=mHeight*80;
		
		pBackground.setSize(width,height);

		
		mBackLayer.attachChild(pBackground);
	}

	
	public void attachObject(LevelObject pObject)
	{
		mObjectsList.add(pObject);
		mMiddleLayer.attachChild(pObject);
	}
	
	public void attachObjectOnTop(LevelObject pObject)
	{
		mObjectsList.add(pObject);
		mFrontLayer.attachChild(pObject);
	}
	
	public float getWidth()
	{
		return mWidth*80;
	}
	
	public float getHeight()
	{
		return mHeight*80;
	}
	
	public LinkedList<LevelObject> getObjectsByType(String pType)
	{
		LinkedList<LevelObject> result = new LinkedList<LevelObject>();
		
		for(int i=0; i<mObjectsList.size();i++)
			if(mObjectsList.get(i).mType.equals(pType))
				result.add(mObjectsList.get(i));
		
		return result;
	}

}
