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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/*
 * It's spell effect(speed down, speed up, damage down, damage up, etc.)
 * @author Denis Terehin
 */
//TODO: extend AnimatedSprite
public class Effect extends AnimatedSprite {
	private float mX;
	private float mY;
	private float mPower;
	private long mTime;
	private String mType;
	public Sprite effectTexture;
	
	public Effect(float x, float y, float power, long time, ITextureRegion effect_Texture, 
			VertexBufferObjectManager vbom, ITiledTextureRegion effect_region, String type)
	{
		super(0,0,effect_region,vbom);
		this.setX(x);
		this.setY(y);
		this.setPower(power);
		this.mTime=time;
		this.setType(type);
		effectTexture = new Sprite(x, y, effect_Texture, vbom);        
	}
	
	//TODO: Use onUpdate
	public void update()
	{
		mTime=mTime-1;
	}

	public float getX() {
		return mX;
	}

	public void setX(float mX) {
		this.mX = mX;
	}

	public float getY() {
		return mY;
	}

	public void setY(float mY) {
		this.mY = mY;
	}

	public float getPower() {
		return mPower;
	}

	public void setPower(float mPower) {
		this.mPower = mPower;
	}

	public String getType() {
		return mType;
	}

	public void setType(String mType) {
		this.mType = mType;
	}

	public long getTime() {
		return mTime;
	}

	public void setTime(long mTime) {
		this.mTime = mTime;
	}
	
	
}
