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

package com.magickbattle.playermenu;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/*
 * It's an unit of level menu, looks like scrollable page
 * Content levels
 * @author Denis Terehin
 */
public class HellCircle 
{
	private Entity mScene;
	private Sprite mBackground;
	private String mSelectedLevel;

	public HellCircle(ITextureRegion region, VertexBufferObjectManager vbom)
	{
		mScene=new Entity();
		mBackground=new Sprite(0,0,region,vbom);
		mBackground.setScale(1.5f,1.6f);
		mBackground.setPosition(115, 240);
		mSelectedLevel="none";
		mScene.attachChild(mBackground);
		
	}
	
	/*
	 * Adding level on page
	 * @param x is a x button position
	 * @param y is a y button position
	 * @param region is a button texture
	 * @param level is a level name
	 */
	public void addButton(float x, float y, ITextureRegion region, VertexBufferObjectManager 
			vbom, String level, final Scene scene)
	{
		Sprite Button=new LevelHolder(x,y,region,vbom,level)
		{
			
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
            {
                if (pSceneTouchEvent.isActionDown())
                {

                }
                if (pSceneTouchEvent.isActionUp())
                {
                	mSelectedLevel=getLevel();
                }
                return true;
            };
		};
		this.mScene.attachChild(Button);
		scene.registerTouchArea(Button);
	}
	
	public Entity getScene() {
		return mScene;
	}

	public void setScene(Entity mScene) {
		this.mScene = mScene;
	}

	public String getSelectedLevel() {
		return mSelectedLevel;
	}

	public void setSelectedLevel(String mSelectedLevel) {
		this.mSelectedLevel = mSelectedLevel;
	}
}
