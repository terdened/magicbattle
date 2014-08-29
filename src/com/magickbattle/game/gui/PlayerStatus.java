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

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.magickbattle.game.GameScene;
import com.magickbattle.game.character.Player;

/*
 * Content player states
 * Provide access to player states
 * Is using by AI 
 * @author Denis Terehin
 */
public class PlayerStatus extends Sprite
{
	private Sprite mHealthLine;
	private Sprite mManaLine;
	private float mMaxWidth;
	private final GameScene mScene; 
	private final Player mPlayer;
	
	public PlayerStatus(float maxWidth, final GameScene scene, final Player player)
	{
		super(0,0,scene.resourcesManager.edge_region,scene.vbom);
		mPlayer=player;
		mHealthLine=new Sprite(player.getX(),player.getY()-20,scene.resourcesManager.healthLine,scene.vbom);
		mManaLine=new Sprite(player.getX(),player.getY()-10,scene.resourcesManager.manaLine,scene.vbom);
		this.attachChild(mHealthLine);
		this.attachChild(mManaLine);
		mMaxWidth=maxWidth;
		mScene=scene;
	}

	/*
	 * Updating player status
	 * @param x is a x position
	 * @param y is a x position
	 * @param hp is a current helth power
	 * @param mp is a current mana power
	 */
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
    {
		mHealthLine.setX(mPlayer.getX());
		mHealthLine.setY(mPlayer.getY()-20);
		mHealthLine.setWidth(mMaxWidth*(mPlayer.health/mPlayer.maxHealth));
		mManaLine.setX(mPlayer.getX());
		mManaLine.setY(mPlayer.getY()-10);
		mManaLine.setWidth(mMaxWidth*(mPlayer.manaPower/mPlayer.maxMana));
	}
	
	public Sprite getHealthLine() {
		return mHealthLine;
	}

	public void setHealthLine(Sprite mHealthLine) {
		this.mHealthLine = mHealthLine;
	}

	public Sprite getManaLine() {
		return mManaLine;
	}

	public void setManaLine(Sprite mManaLine) {
		this.mManaLine = mManaLine;
	}

		
}
