package com.example.gameactive;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class LevelHolder extends Sprite
{
	public String level;
	
	public LevelHolder(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vbom, String level) 
	{
		
		super(pX, pY, pTextureRegion, vbom);

		this.level=level;
	}
	
}
