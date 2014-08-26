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
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * Artificial intelligence class.
 * It's look like an expert system. Content Receptors and Reflectors for estimating the best action in geven situation.
 * Receptors works like detectors and determine inner states of world.
 * Reflectors is an action of AI.
 * @author Denis Terehin
 */
public class Ai {

	private Path mPath;
	private Memory mMemory;
	private ArrayList<Receptor> mReceptors;
	private LinkedList<Reflector> mReflectors;
	
	public Ai()
	{
		mReceptors=new ArrayList<Receptor>();
		mReflectors=new LinkedList<Reflector>();
		setPath(new Path());
	}	

	/*
	 * This method estimate the best action
	 * @return action name
	 */
	public String getAction(GameState gameState)
	{
		for(int i=0; i<mReceptors.size(); i++)
		{
			mReceptors.get(i).getState(gameState, mMemory);
		}
				
		for(int i=0; i<mReflectors.size(); i++)
		{
			mReflectors.get(i).estimateK(mReceptors);
		}
		
		
		String result=mReflectors.getFirst().getAction();
		int resultIndex=0;
		
		for(int i=1; i<mReflectors.size();i++)
		{
			if(mReflectors.get(i).getK()>mReflectors.get(resultIndex).getK())
			{
				result=mReflectors.get(i).getAction();
				resultIndex=i;
			}
		}
		
		if(!result.equals("stay"))
		{
			gameState.setAction(result);
			mMemory.setState(gameState);  //Запоминаем последнее состояние и реакцию на него
		}
		
		mMemory.update();
		
		return result;
	}
	
	public Path getPath() {
		return mPath;
	}

	public void setPath(Path mPath) {
		this.mPath = mPath;
	}
	
	
	public Memory getMemory() {
		return mMemory;
	}

	public void setMemory(Memory mMemory) {
		this.mMemory = mMemory;
	}
	
	public ArrayList<Receptor> getReceptors() {
		return mReceptors;
	}

	public void setReceptors(ArrayList<Receptor> mReceptors) {
		this.mReceptors = mReceptors;
	}

	public LinkedList<Reflector> getReflectors() {
		return mReflectors;
	}

	public void setReflectors(LinkedList<Reflector> mReflectors) {
		this.mReflectors = mReflectors;
	}
}
