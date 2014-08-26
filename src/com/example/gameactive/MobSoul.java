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
import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/*
 * Mob soul is used by Haron 
 * @author Denis Terehin
 */
public class MobSoul extends Mob
{

	public MobSoul(final GameScene scene,float pX, float pY, VertexBufferObjectManager vbo,
			Camera camera, PhysicsWorld physicsWorld,  ITiledTextureRegion player_region) {
		super(  scene, pX,  pY,  vbo,  camera,  physicsWorld,   player_region);
	}
	
	@Override
	public void Attack(int ticker)
	{
		attackAnimation();
		this.mTicker=ticker;
		mState=3;
		mScene.player.attacked(mAttackPower);
	}
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
    {
        super.onManagedUpdate(pSecondsElapsed);
        
        if(mHealth<=0)
			Die(600);
		
		if(this.mState==0)
		{
			mBody.setTransform(mScene.enemyList.getFirst().body.getPosition().x+5*mScene.enemyList.getFirst().speedX,
					mScene.enemyList.getFirst().body.getPosition().y, 0);
		}else
		if(this.mState==1)
		{
			if(mTicker==0)
				Go(0);
		}else
		if(this.mState==2)
		{
			if(this.collidesWith(mScene.player))
				Attack(40);
			else
			{
				float x=0;
				float y=0;
				float xSpeed=0;
				float ySpeed=0;
				if(this.mX>mScene.player.getX())
				{	
					xSpeed=-mSpeed;
				}
				if(this.mX<mScene.player.getX())
				{
					xSpeed=mSpeed;
				}
				if(this.mY>mScene.player.getY())
				{
					ySpeed=-mSpeed;
				}
				if(this.mY<mScene.player.getY())
				{
					ySpeed=mSpeed;
				}
				float rot=90;
				
				if(xSpeed>0)
					rot+=(float)(Math.atan(ySpeed/(xSpeed))*180/3.14);
				else
					rot+=(float)(Math.atan(ySpeed/(xSpeed+0.0001f))*180/3.14)+180f;

					
				this.setRotation(rot);
				x=mBody.getPosition().x+xSpeed;
				y=mBody.getPosition().y+ySpeed;
				mBody.setTransform(x, y, (float)(Math.atan(y/(x+0.0001))*180/3.14));
			}
		}else
		if(this.mState==3)
		{
			if(mTicker==0)
				Wait(80);
		}else
		if(this.mState==4)
		{
			if(mTicker==0)
				this.detachSelf();
		}
    }

}
