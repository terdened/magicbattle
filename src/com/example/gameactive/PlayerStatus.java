package com.example.gameactive;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PlayerStatus 
{

	public float health=0;
	public Sprite healthLine;
	public Sprite manaLine;
	float maxWidth;
	float maxHP;
	float maxMP;
	
	public PlayerStatus(float x, float y, VertexBufferObjectManager vbom, ITextureRegion healthLine, ITextureRegion manaLine, float maxWidth, float maxHP, float maxMP)
	{
		this.healthLine=new Sprite(x,y-20,healthLine,vbom);
		this.manaLine=new Sprite(x,y-10,manaLine,vbom);
		this.maxHP=maxHP;
		this.maxWidth=maxWidth;
		this.maxMP=maxMP;
		update(x, y,  maxHP, maxMP);
	}
	
	public void update(float x, float y, float hp, float mp)
	{
		healthLine.setX(x);
		healthLine.setY(y-20);
		healthLine.setWidth(maxWidth*(hp/maxHP));
		manaLine.setX(x);
		manaLine.setY(y-10);
		manaLine.setWidth(maxWidth*(mp/maxMP));
	}
	
}
