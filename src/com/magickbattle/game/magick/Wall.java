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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import com.badlogic.gdx.physics.box2d.Body;

/*
 * Unit of wall spell
 * @author Denis Terehin
 */
public class Wall extends AnimatedSprite{
	private float mHealth;
	private Body mWallBody;
	private Boolean mIsBodyInit;
	private String mElement;
	
	public Wall(float x, float y, float health, ITiledTextureRegion wall_region, VertexBufferObjectManager vbom,String element)
	{
		super(x, y, wall_region, vbom);
		mX=x;
		mY=y;
		mHealth=health;
		mIsBodyInit=false;
        mElement=element;
        long[] PLAYER_ANIMATE = new long[] { 0, 0, 0, 0, 100, 100, 100, 100 };
		playAnimation(PLAYER_ANIMATE);
		setCurrentTileIndex(4);
		mRotation=0;
	}
	
	/*
	 * Initialization of body
	 * @param wallBody is a body of wall
	 */
	public void initBody(Body wallBody)
	{
		mWallBody=wallBody;
		mIsBodyInit=true;
	}
	
	/*
	 * Attack wall
	 * @param damage is a value of damage
	 */
	public void attack(float damage)
	{
		mHealth-=damage;
	}
	
	/*
	 * Checking state on wall
	 * @return true if wall destroyed
	 */
	public Boolean isDestroy()
	{
		if(mHealth<=0)
			return true;
		else 
			return false;
	}
	
	/*
	 * Play of animation
	 * @param PLAYER_ANIMATE is a value of frames duration
	 */
	public void playAnimation(long[] PLAYER_ANIMATE)
	 {
			 animate(PLAYER_ANIMATE, 0, 7, true);
			 int random=(int)(Math.random()*7);
			 setCurrentTileIndex(random);
	 }
	/*
	 * Updating wall
	 */
	public void updateWall()
	{
		if(getCurrentTileIndex()==7)
		{
			long[] PLAYER_ANIMATE = new long[] { 100, 100, 100, 100, 0, 0, 0, 0 };
			playAnimation(PLAYER_ANIMATE);
		}
		if((mElement.equals("wind"))||(mElement.equals("water"))||(mElement.equals("fire")))
		{
			mHealth-=0.1f;
		}
	}

	public float getHealth() {
		return mHealth;
	}

	public void setHealth(float mHealth) {
		this.mHealth = mHealth;
	}
	
	public Body getWallBody() {
		return mWallBody;
	}

	public void setWallBody(Body mWallBody) {
		this.mWallBody = mWallBody;
	}

	public Boolean getIsBodyInit() {
		return mIsBodyInit;
	}

	public void setIsBodyInit(Boolean mIsBodyInit) {
		this.mIsBodyInit = mIsBodyInit;
	}

	public String getElement() {
		return mElement;
	}

	public void setElement(String mElement) {
		this.mElement = mElement;
	}
	
}
