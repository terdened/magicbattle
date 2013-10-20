package com.example.gameactive;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Effect {
	float x,y;
	float power;
	long time;
	String type;
	AnimatedSprite effectAnimation;
	
	
	public Sprite effectTexture;
	
	public Effect(float x, float y, float power, long time, ITextureRegion effect_Texture, VertexBufferObjectManager vbom, String type)
	{
		this.x=x;
		this.y=y;
		this.power=power;
		this.time=time;
		this.type=type;
		effectTexture = new Sprite(x, y, effect_Texture, vbom);        
	}
	
	public void setAnimation(VertexBufferObjectManager vbo, ITiledTextureRegion effect_region)
	{
		effectAnimation=new AnimatedSprite(0,0,effect_region,vbo);		
	}
	
	
	public void update()
	{
		time=time-1;
	}
	
	
}
