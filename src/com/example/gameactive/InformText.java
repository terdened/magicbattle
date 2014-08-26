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
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/*
 * Information text
 * After creating text play animation moving up ang disappearing
 * @author Denis Terehin
 */
public class InformText extends Text
{
	
	private int mTime;
	private int mRemain;
	
	public InformText(float pX, float pY, IFont pFont, CharSequence pText,
			int pCharactersMaximum,
			VertexBufferObjectManager pVertexBufferObjectManager, int time) {
		super(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager);
		this.mTime=time;
		mRemain=time;
				
	}

	public InformText(float x, float y, Font font, String text,
			VertexBufferObjectManager vbom, int time) {
		
		super(x, y, font, text, vbom);
		
		this.mTime=time;
		mRemain=time;

	}

	@Override
    protected void onManagedUpdate(float pSecondsElapsed) 
    {
        super.onManagedUpdate(pSecondsElapsed);
        mRemain--;
		this.setAlpha((float)((float)mRemain/(float)mTime));
		this.setY(this.getY()-0.5f);
    }
	
	public int getTime() {
		return mTime;
	}

	public void setTime(int mTime) {
		this.mTime = mTime;
	}

	public int getRemain() {
		return mRemain;
	}

	public void setRemain(int mRemain) {
		this.mRemain = mRemain;
	}
	
	
}
