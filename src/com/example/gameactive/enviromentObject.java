/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gameactive;
import java.util.LinkedList;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

/*
 * It's an object on scene
 * @author Denis Terehin
 */
public class EnviromentObject extends AnimatedSprite{
	
	private long mLifeTime;
	private Boolean mWindCollision;
	private Boolean mIsDestroy;
	private String mType;
	private int mPeriod;
	private final GameScene mScene;

	public EnviromentObject(float x, float y, Boolean windCollision, ITiledTextureRegion texture,
			long lifeTime, final GameScene scene,long[] PLAYER_ANIMATE,String type)
	{
		super(x, y, texture, scene.vbom);
		mScene=scene;
		mScene.attachChild(this);
		mPeriod=0;
		mWindCollision=windCollision;
		mLifeTime=lifeTime;
		mIsDestroy=false;
		
		if(lifeTime<=0)
			mIsDestroy=true;
		playAnimation(PLAYER_ANIMATE);
		this.mType=type;
	}
	
	public EnviromentObject(float x, float y, Boolean windCollision, ITiledTextureRegion texture,
			long lifeTime, final Entity scene, final GameScene gameScene,long[] PLAYER_ANIMATE,String type)
	{
		super(x, y, texture, gameScene.vbom);
		mScene=gameScene;
		scene.attachChild(this);
		mPeriod=0;
		mWindCollision=windCollision;
		mLifeTime=lifeTime;
		mIsDestroy=false;
		
		if(lifeTime<=0)
			mIsDestroy=true;
		playAnimation(PLAYER_ANIMATE);
		this.mType=type;
	}
	/*
	 * Moving texture center in given position
	 */
	public void setCenter(float x, float y)
	{
		this.setX(getX()-x);
		this.setY(getY()-y);
	}
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
    {
        super.onManagedUpdate(pSecondsElapsed);
        if(mWindCollision)
		{
			setX(getX()-mScene.weather.wind.x);
			setY(getY()-mScene.weather.wind.y);
		}
		if(mType=="stormtrooper")
		{
			if(mPeriod==0)
			{
				LinkedList<Bulet> temp=new LinkedList<Bulet>();
				 
				float tempRotation=(float) ((getRotation()-90)*3.14/180);
			 	//temp.add(new Bulet(mScene.resourcesManager.player_bulet_region,10,"wind", mScene));
			 	float x1=getX()+32+100*(float)Math.cos(tempRotation);
			 	float x2=getX()+32+120*(float)Math.cos(tempRotation);
			 	float y1=getY()+32+100*(float)Math.sin(tempRotation);
			 	float y2=getY()+32+120*(float)Math.sin(tempRotation);
			 	temp.getLast().init(x1,y1,100);
			 	temp.getLast().lastInit(x2,y2,110);			 	 
			 	mScene.gameSceneCreator.createBulet(temp, mScene.enemyList.getFirst());
			 	mPeriod=20;
			}
			
			mPeriod--;
			
		}
		
		mLifeTime--;
		
		if(mLifeTime<=0)
			mIsDestroy=true;
    }
	
	/*
	 * @return object state
	 */
	public Boolean isDestroy()
	{
		return this.mIsDestroy;
	}
	
	/*
	 * Set playing animation
	 * @param OBJECT_ANIMATE is a frames duration
	 */
	public void playAnimation(long[] OBJECT_ANIMATE)
	 {
			 animate(OBJECT_ANIMATE, 0, 7, true);
			 int random=(int)(Math.random()*7);
			 setCurrentTileIndex(random);
	 }

	public long getLifeTime() {
		return mLifeTime;
	}

	public void setLifeTime(long mLifeTime) {
		this.mLifeTime = mLifeTime;
	}

	public Boolean getWindCollision() {
		return mWindCollision;
	}

	public void setWindCollision(Boolean mWindCollision) {
		this.mWindCollision = mWindCollision;
	}

	public Boolean getIsDestroy() {
		return mIsDestroy;
	}

	public void setIsDestroy(Boolean mIsDestroy) {
		this.mIsDestroy = mIsDestroy;
	}

	public String getType() {
		return mType;
	}

	public void setType(String mType) {
		this.mType = mType;
	}

	public int getPeriod() {
		return mPeriod;
	}

	public void setPeriod(int mPeriod) {
		this.mPeriod = mPeriod;
	}
}
