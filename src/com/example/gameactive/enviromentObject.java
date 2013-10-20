package com.example.gameactive;
import java.util.LinkedList;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;


public class enviromentObject {
	
	public AnimatedSprite texture;
	public long lifeTime;
	public Boolean windCollision;
	public Boolean isDestroy;
	public float x;
	public float y;
	public String type;
	public float rotation;
	
	public int period=0;
	
	public LinkedList<Bulet> buletQueue; //Некрасиво
	
	public enviromentObject(float x, float y, Boolean windCollision, long lifeTime, String type, float rotation)
	{
		this.x=x;
		this.y=y;
		this.windCollision=windCollision;
		this.lifeTime=lifeTime;
		this.type=type;
		this.rotation=rotation;
	}
	
	public enviromentObject(float x, float y, Boolean windCollision, ITiledTextureRegion texture, long lifeTime, VertexBufferObjectManager vbo,final Entity scene,long[] PLAYER_ANIMATE,String type)
	{
		
		this.x=x;
		this.y=y;
		this.windCollision=windCollision;
		this.texture=new AnimatedSprite(x, y, texture, vbo);
		this.lifeTime=lifeTime;
		isDestroy=false;
		if(lifeTime<=0)
			isDestroy=true;
		playAnimation(PLAYER_ANIMATE);
		scene.attachChild(this.texture);
		this.type=type;
		buletQueue=new LinkedList<Bulet>();
	}
	
	public void setCenter(float x, float y)
	{
		this.texture.setX(texture.getX()-x);
		this.texture.setY(texture.getY()-y);
	}
	
	public void Update(Vector2 wind)
	{
		if(windCollision)
		{
			texture.setX(texture.getX()-wind.x);
			texture.setY(texture.getY()-wind.y);
		}
		if(type=="stormtrooper")
		{
			if(period==0)
			{
				 LinkedList<Bulet> temp=new LinkedList<Bulet>();
				 
				 float tempRotation=(float) ((texture.getRotation()-90)*3.14/180);
			 	 temp.add(new Bulet(10,"wind"));
			 	 float x1=texture.getX()+32+100*(float)Math.cos(tempRotation);
			 	 float x2=texture.getX()+32+120*(float)Math.cos(tempRotation);
			 	 float y1=texture.getY()+32+100*(float)Math.sin(tempRotation);
			 	 float y2=texture.getY()+32+120*(float)Math.sin(tempRotation);
			 	 temp.getLast().init(x1,y1,100);
			 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
			 	 temp.getLast().lastInit(x2,y2,110);
			 	 
			 	setBuletList(temp);
			 	
			 	period=20;
			}
			
			period--;
			
		}
		
		lifeTime--;
		
		if(lifeTime<=0)
			isDestroy=true;
	}
	
	public Boolean isDestroy()
	{
		return this.isDestroy;
	}
	
	public void playAnimation(long[] PLAYER_ANIMATE)
	 {
			 //final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100, 100, 100, 100, 100, 100 };
			 texture.animate(PLAYER_ANIMATE, 0, 7, true);
			 int random=(int)(Math.random()*7);
			 texture.setCurrentTileIndex(random);
	 }
	 
	
	 public void setBuletList(LinkedList<Bulet> temp)
	 {
		buletQueue=new LinkedList<Bulet>();
		buletQueue=temp;
	 }
	
	 public LinkedList<Bulet> getBuletList()
	 {
		LinkedList<Bulet> temp=buletQueue;
		return temp;
	 }
	

}
