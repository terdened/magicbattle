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
 * Contain path nodes list
 * Provide access to nodes
 * @author Denis Terehin
 */
public class Path 
{
	private LinkedList<PathNode> mNodeList=new LinkedList<PathNode>();
	private int mCurrentNodeIndex=0;
	
	Path()
	{
		mNodeList=new LinkedList<PathNode>();
		mCurrentNodeIndex=0;
	}
	
	/*
	 * Return x coordinate of current node
	 */
	public float getNodeX()
	{
		return mNodeList.get(mCurrentNodeIndex).getX();
	}
	
	/*
	 * Return y coordinate of current node
	 */
	public float getNodeY()
	{
		return mNodeList.get(mCurrentNodeIndex).getY();
	}
	
	
	/*
	 * Add new node in path
	 */
	public void addNode(PathNode node)
	{
		mNodeList.add(node);
	}
	
	
	/*
	 * Move node pointer on next node
	 * If current node is last then first node is current
	 */
	public void nextNode()
	{
		mCurrentNodeIndex++;
		if(mCurrentNodeIndex==mNodeList.size())
			mCurrentNodeIndex=0;
	}
}
