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
 * Refiector is a one of possible action of AI 
 * Get receptors list and estimate possibility of action which reflector present
 * @author Denis Terehin
 */
public class Reflector {
	
	private String mAction;
	private float mK;
	private LinkedList<ReceptorK> mReceptorsK;
		
	public Reflector(String action, LinkedList<ReceptorK> receptorsK)
	{
		mK=0;
		mAction=action;
		mReceptorsK=receptorsK;
	}
	
	/*
	 * Compute possibility of action
	 * @param receptorsList is a list of receptors
	 */
	public void estimateK(ArrayList<Receptor> receptorsList)
	{
		float result=0;
		for(int i=0;i<mReceptorsK.size();i++)
		{
			int tempIndex=-1;

			for(int j=0;j<receptorsList.size();j++)
			{
				if(receptorsList.get(j).mId==mReceptorsK.get(i).getId())
				{
					tempIndex=j;
					break;
				}
			}
			
				float temp=mReceptorsK.get(i).getK()[receptorsList.get(tempIndex).mState]/mReceptorsK.size();
				result+=temp*(1-result);
		}
		
		mK=result;
	}
	
	public String getAction() {
		return mAction;
	}

	public void setAction(String mAction) {
		this.mAction = mAction;
	}

	public float getK() {
		return mK;
	}

	public void setK(float mK) {
		this.mK = mK;
	}

	public LinkedList<ReceptorK> getReceptorsK() {
		return mReceptorsK;
	}

	public void setReceptorsK(LinkedList<ReceptorK> mReceptorsK) {
		this.mReceptorsK = mReceptorsK;
	}
	
}
