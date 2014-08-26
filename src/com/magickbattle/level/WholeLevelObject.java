package com.magickbattle.level;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class WholeLevelObject extends LevelObject {
	
	public WholeLevelObject(int pX, int pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObject) 
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObject);
		this.mType="whole";
	}
	
	public void InitWhole(String type)
	{
    	final long[] WHOLE_TYPE = new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
       
		for(int i=0; i<12; i++)
		{
			if(i==Integer.parseInt(type))
			{
				WHOLE_TYPE[i]=100;
			}
		}
		
		this.animate(WHOLE_TYPE, 0, 11, false);
	}
}
