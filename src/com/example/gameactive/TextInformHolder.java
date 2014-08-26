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

/*
 * Class present animated text on scene
 * @author Denis Terehin
 */
public class TextInformHolder 
{
	private float mX;
	private float mY;
	private float mTime;
	private String mText;
	private  float mXSize;
	private  float mYSize;
	
	public TextInformHolder(float x, float y, float time, String text)
	{
		this.mX=x;
		this.mY=y;
		this.mTime=time;
		this.mText=text;
		mXSize=0;
		mYSize=0;
	}
	
	public TextInformHolder(float x, float y, float time, String text, float xSize, float ySize)
	{
		this.mX=x;
		this.mY=y;
		this.mTime=time;
		this.mText=text;
		this.mXSize=xSize;
		this.mYSize=ySize;
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

	public float getTime() {
		return mTime;
	}

	public void setTime(float mTime) {
		this.mTime = mTime;
	}

	public String getText() {
		return mText;
	}

	public void setText(String mText) {
		this.mText = mText;
	}

	public float getXSize() {
		return mXSize;
	}

	public void setXSize(float mXSize) {
		this.mXSize = mXSize;
	}

	public float getYSize() {
		return mYSize;
	}

	public void setYSize(float mYSize) {
		this.mYSize = mYSize;
	}

}
