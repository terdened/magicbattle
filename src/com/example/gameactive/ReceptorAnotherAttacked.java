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
 * Receptor determines that another player was attacked 
 * @author Denis Terehin
 */
public class ReceptorAnotherAttacked extends Receptor
{

	public ReceptorAnotherAttacked(int id) {
		super(id);
	}

	
	@Override
	public int getState(GameState gameState, Memory memory)
	{
		
		if(gameState.enemyList.get(1).isAttackted)
			mState=4;
		else
			mState=0;
		
		return mState;
	}
	
}
