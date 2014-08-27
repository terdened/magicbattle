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

package com.magickbattle.game;

/*
 * Contain path node
 * Provide access to path node
 * There are 2 types nodes: random & random and defined
 * @author Denis Terehin
 */
public class PathNode 
{
	private String mType;
	private float mX;
	private float mY;
	private float mMaxX;
	private float mMaxY;
	private float mMinX;
	private float mMinY;
	
	PathNode(float x, float y)
	{
		mType="define";
		this.mX=x;
		this.mY=y;
	}
	
	PathNode(Boolean temp ,float maxX, float maxY, float minX, float minY)
	{
		mType="random";
		this.mMaxX=maxX;
		this.mMaxY=maxY;
		this.mMinX=minX;
		this.mMinY=minY;
	}
	
	
	/*
	 * Return X value of node
	 */
	public float getX()
	{
		//TODO:Use min value
		if(mType=="define")
			return mX;
		else
			return (float)Math.random()*mMaxX;
	}
	
	
	/*
	 * Return Y value of node
	 */
	public float getY()
	{
		//TODO:Use min value
		if(mType=="define")
			return mY;
		else
			return (float)Math.random()*mMaxY;
	}
}
