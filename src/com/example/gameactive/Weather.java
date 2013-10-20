package com.example.gameactive;

import java.io.IOException;
import java.util.LinkedList;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;

public class Weather {
	
	public Vector2 wind;
	
	public Vector2 windMagic;
	public int windMagicTimer;
	
	public Boolean rain;
	public int rainTimer;
	
	public Boolean snow;
	public int snowTimer;
	
	public Boolean earthshock;
	public int earthshockTimer;
	
	public ResourcesManager resourcesManager;
	
	public Entity scene;
	
	public Boolean apocalipsys;
	public LinkedList<Wall> walls;
	public LinkedList<enviromentObject> objects;
	public ITextureRegion wall_region;
	
	public Vector2 getWind()
	{
		Vector2 result=new Vector2(wind.x,wind.y);
		result.add(windMagic);
		return result;
	}
	 
	public Weather(Boolean rain, int rainTimer, Boolean snow, int snowTimer,  Boolean shock, int shockTimer, Vector2 wind, ITextureRegion wall_region, ResourcesManager resourcesManager)
	{
		this.rain=rain;
		this.rainTimer=rainTimer;
		this.snow=snow;
		this.snowTimer=snowTimer;
		this.wind=wind;
		windMagicTimer=0;
		earthshock=shock;
		earthshockTimer=shockTimer;
		apocalipsys=false;
		this.wall_region=wall_region;
		walls=new LinkedList<Wall>();
		objects=new LinkedList<enviromentObject>();
		windMagic=new Vector2(0,0);
		this.resourcesManager=resourcesManager;
		scene=new Entity();
	}
	
	public void updateWeather(VertexBufferObjectManager vbo)
	{
		for(int i=0;i<objects.size();i++)
		{
			if(objects.get(i).isDestroy())
			{
				scene.detachChild(objects.get(i).texture);
				objects.remove(i);
			}else
			{
				objects.get(i).Update(getWind());
			}
		}
		
		if(windMagicTimer>0)
		{
			windMagicTimer--;
			if(windMagicTimer<=0)
			{
				windMagic=new Vector2(0,0);
			}
				
		}
		
		if(rain)
		{
			rainTimer--;
			for(int i=0;i<1;i++)
			{
				
				objects.add(new enviromentObject((float)Math.random()*800, (float)Math.random()*1280, true, resourcesManager.rain , 30, vbo, scene,new long[] { 100, 100, 100, 100, 100, 100, 100, 100 },"rain"));
			}
			if(rainTimer==0)
				rain=false;
		}
		
		if(snow)
		{
			snowTimer--;
			if(snowTimer==0)
				snow=false;
		}
		
		if(earthshock)
		{
			earthshockTimer--;
			if(earthshockTimer==0)
				earthshock=false;
		}
		
		if(apocalipsys)
		{
				for(int i=0;i<walls.size();i++)
				{
					if(walls.get(i).isDestroy())
					{	
						walls.remove(i);
					}
					else
					walls.get(i).attack(1);
					
				}
				
				if(walls.size()==0)
					apocalipsys=false;
				
		}
		
		
	}
	
	public void updateShadow(float x, float y)
	{
		
	}
	
	public void setWind(Vector2 windMagic, int time)
	{
		this.windMagic=windMagic;
		windMagicTimer=time;
	}
	
	public void setRain(int time)
	{
		rain=true;
		rainTimer=time;
	}
	
	public void setTail(String element, float x, float y, VertexBufferObjectManager vbo, String playerElement, String enemyElement)
	{				
		if(element==playerElement)
			objects.add(new enviromentObject(x, y, false, resourcesManager.playerBuletTail , 10, vbo, scene,new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"tail"));
		else
			objects.add(new enviromentObject(x, y, false, resourcesManager.enemyBuletTail , 10, vbo, scene,new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"tail"));
		
		objects.getLast().setCenter(15, 15);
	}
	
	public void setFingerMagic(String element, float x, float y, VertexBufferObjectManager vbo)
	{					
		objects.add(new enviromentObject(x, y, false, resourcesManager.playerBuletTail , 10, vbo, scene,new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"tail"));
		objects.getLast().setCenter(objects.getLast().texture.getWidth()/2, objects.getLast().texture.getHeight()/2);
	}
	
	public void setShadow(float x, float y, VertexBufferObjectManager vbo, String type)
	{
		objects.add(new enviromentObject(x, y, false, resourcesManager.playerBuletTail , 10, vbo, scene,new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"shadow"));
	}
	
	public void setSnow(int time)
	{
		snow=true;
		snowTimer=time;
	}

	public void setApocalipsys(float time, VertexBufferObjectManager vbom, final GameScene scene)
	{
		apocalipsys=true;
		
		for(int i=0;i<20;i++)
		{
			walls.add(new Wall((float)Math.random()*800, (float)Math.random()*1280, time, resourcesManager.wall_region, vbom, "fire"));
			scene.attachChild(walls.getLast().wallTexture);
		}
		
	}
	
	public void setStormtrooper(VertexBufferObjectManager vbom, final GameScene scene, LinkedList<enviromentObject> objectList)
	{
		for(int i=0;i<objectList.size();i++)
		{
			//objects.add(new enviromentObject(, , objectList.get(i).lifeTime, resourcesManager.enemyElementMagic, vbom, "stormtrooper"));
			objects.add(new enviromentObject(objectList.get(i).x, objectList.get(i).y, false, resourcesManager.enemyElementMagic , objectList.get(i).lifeTime, vbom, this.scene,new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"stormtrooper"));
			objects.getLast().texture.setRotation(objectList.get(i).rotation);
			//scene.attachChild(objects.getLast().texture);

		}
		
	}
	

}
