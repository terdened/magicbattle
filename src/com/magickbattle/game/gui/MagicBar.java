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
package com.magickbattle.game.gui;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/*
 * Magic bar it's bar with spells type
 * Don't use in current version
 * @author Denis Terehin
 */
public class MagicBar 
{
	private float mX;
	private float mY;
	private float mIconHeight;
	private float mIconDistantion;
	private int mChoosenIcon;
	
	public AnimatedSprite buletIcon;
	public AnimatedSprite wallIcon;
	public AnimatedSprite natureIcon;
	public AnimatedSprite bufIcon;
	public AnimatedSprite debufIcon;
	
	public MagicBar(float X, float Y, float iconHeight, float iconDistantion)
	{
		this.mX=X;
		this.mY=Y;
		this.mIconHeight=iconHeight;
		this.mIconDistantion=iconDistantion;
		mChoosenIcon=1;
	}
	
	
	public void loadImages(ITiledTextureRegion buletIcon, ITiledTextureRegion wallIcon, ITiledTextureRegion natureIcon,
			ITiledTextureRegion bufIcon, ITiledTextureRegion debufIcon, VertexBufferObjectManager vbom)
	{
		
		this.buletIcon = new AnimatedSprite(mX, mY, buletIcon, vbom)
		{
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(1);
	            }
	            return true;
	        };
	    };
		
		this.wallIcon = new AnimatedSprite(mX, mY+mIconHeight+mIconDistantion, wallIcon, vbom)
		{
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(2);
	            }
	            return true;
	        };
	    };
		this.natureIcon = new AnimatedSprite(mX, mY+2*(mIconHeight+mIconDistantion), natureIcon, vbom){
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(3);
	            }
	            return true;
	        };
	    };
		this.bufIcon = new AnimatedSprite(mX, mY+3*(mIconHeight+mIconDistantion), bufIcon, vbom){
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(4);
	            }
	            return true;
	        };
	    };
		this.debufIcon = new AnimatedSprite(mX, mY+4*(mIconHeight+mIconDistantion), debufIcon, vbom){
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(5);
	            }
	            return true;
	        };
	    };
		
		this.buletIcon.setCurrentTileIndex(1);
		this.wallIcon.setCurrentTileIndex(0);
		this.natureIcon.setCurrentTileIndex(0);
		this.bufIcon.setCurrentTileIndex(0);
		this.debufIcon.setCurrentTileIndex(0);
	}
	
	public void chooseIcon(int index)
	{
		int i1=0;
		int i2=0;
		int i3=0;
		int i4=0;
		int i5=0;
		
		if(index==1)
			i1=1;
		if(index==2)
			i2=1;
		if(index==3)
			i3=1;
		if(index==4)
			i4=1;
		if(index==5)
			i5=1;
		
		this.buletIcon.setCurrentTileIndex(i1);
		this.wallIcon.setCurrentTileIndex(i2);
		this.natureIcon.setCurrentTileIndex(i3);
		this.bufIcon.setCurrentTileIndex(i4);
		this.debufIcon.setCurrentTileIndex(i5);
		
		mChoosenIcon=index;
		
	}
	
	public int getChoosenIcon()
	{
		return mChoosenIcon;
	}
	
}
