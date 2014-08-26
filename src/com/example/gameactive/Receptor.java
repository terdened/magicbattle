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
 * Abstract class of receptor
 * Is using by AI 
 * @author Denis Terehin
 */
public abstract class Receptor {
	
	protected int mId;
	protected int mState;
	
	public Receptor(int id)
	{
		this.mId=id;
	}
	
	/*
	 * Calculate receptor state in fuzzy logic
	 * @param gameState is a set of states in game scene
	 * @param memory is a list of previous gameStates
	 * @return receptor state
	 */
	public abstract int getState(GameState gameState, Memory memory);

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public int getState() {
		return mState;
	}

	public void setState(int mState) {
		this.mState = mState;
	}	
}
