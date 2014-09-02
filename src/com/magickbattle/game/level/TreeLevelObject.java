package com.magickbattle.game.level;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class TreeLevelObject extends LevelObject {
	
	public TreeLevelObject(int pX, int pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObject) 
	{
		super(pX-1, pY-1, pTiledTextureRegion, pVertexBufferObject);
		this.mType="tree";
	}

}
