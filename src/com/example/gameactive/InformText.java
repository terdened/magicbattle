package com.example.gameactive;

import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class InformText extends Text
{
	
	int time;
	int remain;
	
	public InformText(float pX, float pY, IFont pFont, CharSequence pText,
			int pCharactersMaximum,
			VertexBufferObjectManager pVertexBufferObjectManager, int time) {
		super(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager);
		this.time=time;
		remain=time;
				
	}

	public InformText(float x, float y, Font font, String text,
			VertexBufferObjectManager vbom, int time) {
		
		super(x, y, font, text, vbom);
		
		this.time=time;
		remain=time;

	}

	void update()
	{
		remain--;
		this.setAlpha((float)((float)remain/(float)time));
		this.setY(this.getY()-0.5f);
	}
	
	
}
