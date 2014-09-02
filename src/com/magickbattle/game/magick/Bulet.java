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

package com.magickbattle.game.magick;
import java.util.LinkedList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.magickbattle.game.GameScene;
import com.magickbattle.game.level.LevelObject;

//TODO: extend AnimatedSprite
/*
 * Bulet
 * @author Denis Terehin
 */
public class Bulet extends Sprite
{
	public float mStartX;
	public float mStartY;
	public float mFinalX;
	public float mFinalY;
	protected float mSpeedX;
	protected float mSpeedY;
	protected float mSpeedK;
	protected long mStartTime;
	protected Boolean mIsInit;
	public Boolean mIsRemove;
	protected final GameScene mScene;
	
	public float damage;
	public String element;
	
	public Bulet(ITextureRegion pTextureRegion, float damage, String element, GameScene scene) {
		super( 0, 0, pTextureRegion,scene.vbom);
		
		mScene=scene;
		mIsInit=false;
		mIsRemove=false;
		this.damage=damage;
		this.element=element;
		
	}
	
	/*
	 * First initialization of bulet
	 * @param x is a start x
	 * @param y is a start y
	 * @param time is a start tap time
	 */
	public void init(float x, float y, long time)
	{
		mStartX=x;
		mStartY=y;
		mSpeedK=(float) 0.1;
		mIsInit=true;
		mStartTime=time;
	}
	
	public void removeControl()
	{
		if((this.getX()<-20)||(this.getX()>820))
		{
			remove();
		}
		else
		if((this.getY()<-20)||(this.getY()>mScene.mLevel.getHeight()))
		{
			remove();
		}
	}
	
	public void remove()
	{
		mScene.mBuletToRemove.add(this);
	}
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
    {
        super.onManagedUpdate(pSecondsElapsed);
        
        removeControl();
        
        int l=mScene.wall.size();
        for(int i=0; i<l;i++)
        {                
            if (mScene.wall.get(i).collidesWith(this))
            {
            	if(!mScene.wall.get(i).getElement().equals("wind"))
            	{
            		if((mScene.wall.get(i).getElement().equals("fire"))&&(element.equals("fire")))
            		{
            			damage+=0.1f;
                		
            		}else
            		{
            			mScene.wall.get(i).attack(damage);
            			remove();
            			return;
            		}
            	}else
            	{
            		Vector2 vecA= new Vector2(mSpeedX,mSpeedY);
            		Vector2 vecB= new Vector2(-(float)Math.cos((double)mScene.wall.get(i).getRotation()),-(float)Math.sin((double)mScene.wall.get(i).getRotation()));
            		Vector2 vecC=vecA.add(vecB);
            		mSpeedX=vecC.x;
            		mSpeedY=vecC.y;
            	}
            }
        }
        if (mScene.player.collidesWith(this))
        {
        	mScene.player.attacked(damage);
        	remove();
			return;
        }
        
        LinkedList<LevelObject> levelObsacles = mScene.mLevel.getObjectsByType("stone");
        for(int i=0;i<levelObsacles.size();i++)
        {
        	if(levelObsacles.get(i).collidesWith(this))
        	{	
        		remove();
    			return;
        	}
        }
        
        for(int i=0;i<mScene.mEnemyList.size();i++)
	        if (mScene.mEnemyList.get(i).collidesWith(this))
	        {
	        	mScene.mEnemyList.get(i).attacked(damage);
	        	mScene.mEnemyList.get(i).isAttackted=true;
	        	remove();
    			return;
	        }
        
        Vector2 temp=new Vector2(mSpeedX,mSpeedY);
		temp.add(mScene.weather.wind);
		if(mIsInit)
		{
			mFinalX-=temp.x;
			mFinalY-=temp.y;
			
			setX(mFinalX);
			setY(mFinalY);
		}
    }
	
	/*
	 * Geting last x and last y components for estimating speed and direction of bulet
	 * Start bulet fly
	 * @param x is a last x
	 * @param y is a last y
	 * @param time is a stop tap time
	 */
	public void startFly(float x, float y, long time)
	{
		mFinalX=x;
		mFinalY=y;
		
		
		mSpeedK/=(time-mStartTime)*0.01;
		mSpeedX= mSpeedK*(mStartX-mFinalX);
		mSpeedY= mSpeedK*(mStartY-mFinalY);
		
		setX(mFinalX);
		setY(mFinalY);
		
		mFinalX-=getWidth()/2;
		mFinalY-=getHeight()/2;
	}
	
	
	/*
	 * using for creating bulet without adding on scene
	 */
	public void lastInit(float x, float y, long time)
	{
		mFinalX=x;
		mFinalY=y;
	}
	
	/*
	 * Adding shadow of bulet
	 * @param shadow_region is a animated texture of shadow
	 */
	public void createShadow(VertexBufferObjectManager vbo,  ITiledTextureRegion shadow_region)
    {
    	AnimatedSprite shadow=new AnimatedSprite(0,0,shadow_region,vbo);
    	shadow.setSize(getWidth()+30, getHeight()+30);
    	shadow.setX(shadow.getX()-15);
    	shadow.setY(shadow.getY()-15);    	
    	final long[] SHADOW_ANIMATE = new long[] { 100, 100, 100, 100, 100, 100, 100, 100 };
        shadow.animate(SHADOW_ANIMATE, 0, 7, true);
    	shadow.setZIndex(getZIndex()-1);
    	attachChild(shadow);    	
    }
		
	public float getX()
	{
		return mFinalX;
	}
	
	public float getY()
	{
		return mFinalY;
	}
	
	


}
