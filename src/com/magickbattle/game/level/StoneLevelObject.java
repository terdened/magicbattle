package com.magickbattle.game.level;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class StoneLevelObject extends LevelObject {
	
	public StoneLevelObject(int pX, int pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObject) 
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObject);
		this.mType="stone";
	}

}
