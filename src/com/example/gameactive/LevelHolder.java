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
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/*
 * Content image and name of level
 * @author Denis Terehin
 */
public class LevelHolder extends Sprite
{
	private String mLevel;
	
	public LevelHolder(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vbom, String level) 
	{
		super(pX, pY, pTextureRegion, vbom);
		mLevel=level;
	}

	public String getLevel() {
		return mLevel;
	}

	public void setLevel(String mLevel) {
		this.mLevel = mLevel;
	}
}
