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
 * Receptor control repeat of action
 * @author Denis Terehin
 */
public class ReceptorRepeatControl extends Receptor
{	
	
	private String mControlObject;
	private float mTimeList[];
	
	public ReceptorRepeatControl(int id,String controlObject, float timeList[]) {
		super(id);
		this.mControlObject=controlObject;
		this.mTimeList=timeList;
	}

	@Override
	public int getState(GameState gameState, Memory memory)
	{
		for(int i=0;i<memory.getMemoryList().size();i++)
		{
			if(memory.getMemoryList().get(i).action.equals(mControlObject))
			{
				if(memory.getMemoryList().get(i).time+memory.getLastTime()<mTimeList[0])
					mState=4;
				else
				if(memory.getMemoryList().get(i).time+memory.getLastTime()<mTimeList[1])
					mState=3;
				else
				if(memory.getMemoryList().get(i).time+memory.getLastTime()<mTimeList[2])
					mState=2;
				else
				if(memory.getMemoryList().get(i).time+memory.getLastTime()<mTimeList[3])
					mState=1;
				else
					mState=0;
			}
		}
			
		return mState;
	}
	
}
