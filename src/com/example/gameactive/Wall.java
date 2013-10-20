package com.example.gameactive;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;

public class Wall {

	float x,y;
	float health;
	
	public float rotation=0;
	
	public AnimatedSprite wallTexture;
	public Body wallBody;
	public Boolean isBodyInit;
	public String element;
	
	public Wall(float x, float y, float health, ITiledTextureRegion wall_region, VertexBufferObjectManager vbom,String element)
	{
		this.x=x;
		this.y=y;
		this.health=health;
		wallTexture = new AnimatedSprite(x, y, wall_region, vbom);
		isBodyInit=false;
        this.element=element;
        long[] PLAYER_ANIMATE = new long[] { 0, 0, 0, 0, 100, 100, 100, 100 };
		playAnimation(PLAYER_ANIMATE);
		wallTexture.setCurrentTileIndex(4);
	}
	
	public Wall(float x, float y, String element)
	{
		this.x=x;
		this.y=y;
		this.element=element;
	}
	
	public void initBody(Body wallBody)
	{
		this.wallBody=wallBody;
		isBodyInit=true;
	}
	
	public void attack(float damage)
	{
		health-=damage;
	}
	
	public Boolean isDestroy()
	{
		if(health<=0)
			return true;
		else 
			return false;
	}
	
	public void playAnimation(long[] PLAYER_ANIMATE)
	 {
			 //final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100, 100, 100, 100, 100, 100 };
			 wallTexture.animate(PLAYER_ANIMATE, 0, 7, true);
			 int random=(int)(Math.random()*7);
			 wallTexture.setCurrentTileIndex(random);
	 }
	
	public void updateWall()
	{
		if(wallTexture.getCurrentTileIndex()==7)
		{
			long[] PLAYER_ANIMATE = new long[] { 100, 100, 100, 100, 0, 0, 0, 0 };
			playAnimation(PLAYER_ANIMATE);
		}
		if((element.equals("wind"))||(element.equals("water"))||(element.equals("fire")))
		{
			health-=0.1f;
		}
	}
	
}
