package com.magickbattle.game.level;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class LevelObject extends AnimatedSprite
{

	protected int mX;
	protected int mY;
	protected String mType;
	
	public LevelObject(int pX, int pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObject) 
	{
		super(pX*80, pY*80, pTiledTextureRegion, pVertexBufferObject);
		mX=pX;
		mY=pY;
	}

}
