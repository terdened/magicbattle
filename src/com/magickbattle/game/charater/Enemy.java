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
package com.magickbattle.game.charater;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.magickbattle.game.GameScene;
import com.magickbattle.game.GameState;
import com.magickbattle.game.gui.TextInformHolder;


/*
 * Enemy is a Player with AI
 * Use AI parser for loading AI from XML-file
 * Enemy get next action from AI every step 
 * @author Denis Terehin
 */
public abstract class Enemy extends Player 
{
	 private String mElement;
	 private String mName;
	 protected GameScene mScene;
	 
	 public Enemy(final GameScene scene,float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld,  ITiledTextureRegion player_region)
	 {		 
	    	super(pX, pY, vbo, camera, physicsWorld, player_region);
	    	this.mScene=scene;
	    	
	 }
	
	 /*
	  * Initializtion of AI
	  */
	 public void initAI()
	 {
	 }
	 
	 /*
	  * Method return the best action in current situation
	  */
	 public String update(GameState gameState)
	 {
		 String action="stay";
		  
		 return action;
			 
	 }

	public String getElement() {
		return mElement;
	}

	public void setElement(String mElement) {
		this.mElement = mElement;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
	{
        super.onManagedUpdate(pSecondsElapsed);
        
        if(getIsDead())
        {
        	mScene.mEnemiesToRemove.add(this);
        }
        else
        {
        	fillMana();
        	move();
        	updateEffects(mScene.gameHUD);
        	
        	if(tempText.size()>0)
			{
        		mScene.addTextList(tempText,getWidth());
				tempText=new LinkedList<TextInformHolder>();
			}
        }
    }
}
