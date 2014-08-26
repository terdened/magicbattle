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

import java.util.LinkedList;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/*
 * Menu holder
 * @author Denis Terehin
 */
public class ScrollableMenu {

	private LinkedList<HellCircle> mCirclesList;
	private Boolean mBlocked=false;
	private float mYSpeed=0;
	private int mSelectedLevel=0;

	public ScrollableMenu()
	{
		mCirclesList=new LinkedList<HellCircle>();
	}
	
	/*
	 * Add new page with levels
	 * @param resourcesManager is a resources manager
	 * @param vbom is a vertex buffer
	 */
	public void addCircle(ResourcesManager resourcesManager, VertexBufferObjectManager vbom)
	{
		mCirclesList.add(new HellCircle(resourcesManager.limb_region,vbom));
		mCirclesList.getLast().getScene().setY((mCirclesList.size()-1)*1280);
	}
	
	/*
	 * Action turn page down
	 */
	public void moveDown()
	{
		if(!mBlocked)
		if(mSelectedLevel>0)
		{
			mYSpeed=32;
			mBlocked=true;
			mSelectedLevel--;
		}		
	}
	
	/*
	 * Action turn page up
	 */
	public void moveUp()
	{
		if(!mBlocked)
		if(mSelectedLevel<mCirclesList.size()-1)
		{
			mYSpeed=-32;
			mBlocked=true;
			mSelectedLevel++;
		}		
	}
	
	/*
	 * Updating menu
	 */
	public void update()
	{
		if((mBlocked)&&(mYSpeed!=0))
		{
			for(int i=0; i<mCirclesList.size();i++)
			{
				mCirclesList.get(i).getScene().setY(mCirclesList.get(i).getScene().getY()+mYSpeed);
			}
			if(mCirclesList.get(mSelectedLevel).getScene().getY()==0)
			{	
				mBlocked=false;
				mYSpeed=0;
			}
		}
	}
	
	/*
	 * clear selected level
	 */
	public void clearSelectedLevel()
	{
		for(int i=0;i<mCirclesList.size();i++)
		{
			mCirclesList.get(i).setSelectedLevel("none");
		}
	}
	
	public LinkedList<HellCircle> getCirclesList() {
		return mCirclesList;
	}

	public void setCirclesList(LinkedList<HellCircle> mCirclesList) {
		this.mCirclesList = mCirclesList;
	}

	public Boolean getBlocked() {
		return mBlocked;
	}

	public void setBlocked(Boolean mBlocked) {
		this.mBlocked = mBlocked;
	}

	public float getYSpeed() {
		return mYSpeed;
	}

	public void setYSpeed(float mYSpeed) {
		this.mYSpeed = mYSpeed;
	}

	public int getSelectedLevel() {
		return mSelectedLevel;
	}

	public void setSelectedLevel(int mSelectedLevel) {
		this.mSelectedLevel = mSelectedLevel;
	}
	 
}
