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
import java.util.LinkedList;

/*
 * Class content list of last gameStates
 * Provide access to memmor list
 * @author Denis Terehin
 */
public class Memory 
{
	private LinkedList<GameState> mMemoryList;
	private int mMemorySize;
	private int mLastTime;
	
	public Memory(int memorySize)
	{
		this.mMemorySize=memorySize;
		mMemoryList=new LinkedList<GameState>();
		mLastTime=0;
	}
	
	/*
	 * Method add state in list
	 */
	public void setState(GameState gameState)
	{
		for(int i=0;i<mMemoryList.size();i++)
			mMemoryList.get(i).setTime(mLastTime);
		gameState.setTime(0);
		mMemoryList.add(gameState);
		
		mLastTime=0;
		
		if(mMemoryList.size()==mMemorySize)
			mMemoryList.removeFirst();
	}
	
	/*
	 * Method return state
	 */
	public GameState getState(int i)
	{
		return mMemoryList.get(mMemoryList.size()-i-1);		
	}
	
	/*
	 * Method update time
	 */
	public void update()
	{
		mLastTime++;
	}
	
	public LinkedList<GameState> getMemoryList() {
		return mMemoryList;
	}

	public void setMemoryList(LinkedList<GameState> mMemoryList) {
		this.mMemoryList = mMemoryList;
	}

	public int getLastTime() {
		return mLastTime;
	}

	public void setLastTime(int mLastTime) {
		this.mLastTime = mLastTime;
	}
	
}
