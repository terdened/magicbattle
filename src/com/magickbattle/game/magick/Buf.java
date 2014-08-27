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
import org.andengine.opengl.texture.region.ITextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.magickbattle.game.GameScene;

/*
 * Buf spell
 * Don't use in current version
 * @author Denis Terehin
 */
public class Buf extends Bulet 
{
	private float mPower;
	private long mTime;
	private String mType;

	public Buf(ITextureRegion pTextureRegion, GameScene scene, float power,	long time, String type)
	{
		super(pTextureRegion, 0, "", scene);
		this.mPower=power;
		this.mTime=time;
		this.mType=type;
		mIsInit=false;
		mIsRemove=false;
	}
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
    {
        super.onManagedUpdate(pSecondsElapsed);
        int l=mScene.wall.size();
        for(int i=0; i<l;i++)
        {                
            if (mScene.wall.get(i).collidesWith(this))
            {	                	
            	if(mScene.wall.get(i).getElement()!="wind")
            	{
            		mIsRemove=true;
            	}else
            	{
            		Vector2 vecA= new Vector2(mSpeedX,mSpeedY);
            		Vector2 vecB= new Vector2((float)Math.cos((double)mScene.wall.get(i).getRotation()),(float)Math.sin((double)mScene.wall.get(i).getRotation()));
            		Vector2 vecC=vecA.add(vecB);
            		mSpeedX=vecC.x;
            		mSpeedY=vecC.y;
            	}
            }
            
            
        }
        if (mScene.player.collidesWith(this))
        {
        	mScene.player.setBuf(mPower, mTime, mType);
        	mIsRemove=true;
        }
        
        for(int i=0;i<mScene.mEnemyList.size();i++)
        {
            if (mScene.mEnemyList.get(i).collidesWith(this))
            {
            	mScene.mEnemyList.get(i).setBuf(mPower, mTime, mType);
            	mIsRemove=true;
            }
        }
    }
}
