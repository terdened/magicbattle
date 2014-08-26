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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/*
 * Low-lever AI
 * @author Denis Terehin
 */
public class Mob extends AnimatedSprite 
{
	protected float mHealth;
	protected float mSpeed;
	protected float mAttackPower;
	protected float mAttackSpeed;
	protected int mTicker;
	protected int mState;
	protected Body mBody;
	protected boolean mHaveBody;
	protected GameScene mScene;
	
	public Mob(final GameScene scene,float pX, float pY, VertexBufferObjectManager vbo,
			Camera camera, PhysicsWorld physicsWorld,  ITiledTextureRegion player_region)
	{
		super(pX, pY, player_region, vbo);
		createPhysics(camera,physicsWorld);
		mHaveBody=true;
		this.mScene=scene;
	}
	
	public Mob(final GameScene scene,float pX, float pY, VertexBufferObjectManager vbo,
			ITiledTextureRegion player_region)
	{
		super(pX, pY, player_region, vbo);
		mHaveBody=false;
		this.mScene=scene;
	}
	
	public void init(float health, float speed, float attackPower, float attackSpeed)
	{
		this.mHealth=health;
		this.mSpeed=speed;
		this.mAttackPower=attackPower;
		this.mAttackSpeed=attackSpeed;
		mTicker=0;
		Arise(0);
	}
	
	 public void goAnimation()
	 {

			 final long[] PLAYER_ANIMATE = new long[] { 0, 0, 0, 0,
					 									0, 0, 0, 0,
					 									200, 200, 200, 200,	
														0, 0, 0, 0,
														0, 0, 0, 0													
														};
			 animate(PLAYER_ANIMATE, 0, 19, true);
		     
		 
	 }
	 
	 public void ariseAnimation()
	 {

			 final long[] PLAYER_ANIMATE = new long[] { 200, 200, 200, 200,	
					 									0, 0, 0, 0,
					 									0, 0, 0, 0,	
														0, 0, 0, 0,
														0, 0, 0, 0													
														};
			 animate(PLAYER_ANIMATE, 0, 19, true);
		     
		 
	 }
	 
	 public void dieAnimation()
	 {
			 final long[] PLAYER_ANIMATE = new long[] {	0, 0, 0, 0,
					 									0, 0, 0, 0,
														0, 0, 0, 0,
														0, 0, 0, 0,
														200, 200, 200, 200														
														};
			 animate(PLAYER_ANIMATE, 0, 19, true);
	 }
	 
	 public void stayAnimation()
	 {
		 final long[] PLAYER_ANIMATE = new long[] { 0, 0, 0, 0,
				 									200, 200, 200, 200,	
													0, 0, 0, 0,
													0, 0, 0, 0,
													0, 0, 0, 0													
													};
		 animate(PLAYER_ANIMATE, 0, 19, true);
	 }
	 
	 public void attackAnimation()
	 {
		 final long[] PLAYER_ANIMATE = new long[] { 0, 0, 0, 0,
				 									0, 0, 0, 0,	
													0, 0, 0, 0,
													200, 200, 200, 200,
													0, 0, 0, 0													
													};
		 animate(PLAYER_ANIMATE, 0, 19, true);
	 }
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	 {        
	     mBody = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody,
	    		 PhysicsFactory.createFixtureDef(0, 0, 0));
	     mBody.setUserData("mob");
	     mBody.setFixedRotation(true);  
	     physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, mBody, true, false)
	     {
	         @Override
	         public void onUpdate(float pSecondsElapsed)
	         {
	             super.onUpdate(pSecondsElapsed);
	             camera.onUpdate(0.1f);                   
	         }
	     });
	 }
	
	public void Arise(int ticker)
	{
		ariseAnimation();
		this.mTicker=ticker;
		mState=0;
	}
	
	public void Wait(int ticker)
	{
		stayAnimation();
		this.mTicker=ticker;
		mState=1;
	}
	
	public void Go(int ticker)
	{
		goAnimation();
		this.mTicker=ticker;
		mState=2;
	}
	
	public void Attack(int ticker)
	{
		attackAnimation();
		this.mTicker=ticker;
		mState=3;
	}
	
	public void Die(int ticker)
	{
		dieAnimation();
		this.mTicker=ticker;
		mState=4;
	}
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
    {
        super.onManagedUpdate(pSecondsElapsed);
        if(mTicker>0)
			mTicker--;
    }
	
	
	public void Attcked(float damage)
	{
		mHealth-=damage;
	}
}
