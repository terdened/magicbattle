/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.gameactive;
import java.util.LinkedList;
import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;

/*
 * Eniroment effects controller 
 * Add and update effects
 * @author Denis Terehin
 */
//TODO: Rename class in effects
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
	public LinkedList<EnviromentObject> objects;
	public ITextureRegion wall_region;
	private final GameScene mScene;
	
	/*
	 * @return strength and direction of wind
	 */
	public Vector2 getWind()
	{
		Vector2 result=new Vector2(wind.x,wind.y);
		result.add(windMagic);
		return result;
	}
	 
	public Weather(Boolean rain, int rainTimer, Boolean snow, int snowTimer,  Boolean shock, int shockTimer, Vector2 wind, ITextureRegion wall_region, ResourcesManager resourcesManager, final GameScene scene)
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
		objects=new LinkedList<EnviromentObject>();
		windMagic=new Vector2(0,0);
		this.resourcesManager=resourcesManager;
		this.scene=new Entity();
		mScene=scene;
	}
	
	/*
	 * Updating of weather effects
	 */
	//TODO: add final vbo in class
	public void updateWeather(VertexBufferObjectManager vbo)
	{
		for(int i=0;i<objects.size();i++)
		{
			if(objects.get(i).isDestroy())
			{
				scene.detachChild(objects.get(i));
				objects.remove(i);
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
				
				//objects.add(new EnviromentObject((float)Math.random()*800, (float)Math.random()*1280, true, resourcesManager.rain , 30, vbo, scene,new long[] { 100, 100, 100, 100, 100, 100, 100, 100 },"rain"));
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
	
	/*
	 * Wind initialization 
	 * @param windMagic is a wind direction
	 * @param time is a wind duration
	 */
	public void setWind(Vector2 windMagic, int time)
	{
		this.windMagic=windMagic;
		windMagicTimer=time;
	}
	
	/*
	 * Rain initialization
	 * @param time is a rain duration
	 */
	public void setRain(int time)
	{
		rain=true;
		rainTimer=time;
	}
	
	/*
	 * Create bulet tail effect 
	 * @param element is a element of bullet(fire, wind, water, earth)
	 * @param x is a X position of tail effect
	 * @param x is a Y position of tail effect
	 * @param playerElement is a element of player
	 * @param enemyElement is a element of enemy
	 */
	public void setTail(String element, float x, float y)
	{				
		if(element.equals(mScene.playerMagic.element))
			objects.add(new EnviromentObject(x, y, false, mScene.resourcesManager.playerBuletTail , 10,scene, 
					 mScene,new long[] { 20, 20, 20, 20, 20, 20, 20, 20 },"tail"));
		else
			objects.add(new EnviromentObject(x, y, false, mScene.resourcesManager.enemyBuletTail , 10, scene,
					 mScene,new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"tail"));
		
		objects.getLast().setCenter(15, 15);
	}
	
	/*
	 * Create finger tail effect 
	 * @param element is a element of player(fire, wind, water, earth)
	 * @param x is a X position of finger tail effect
	 * @param x is a Y position of finger tail effect
	 */
	public void setFingerMagic(float x, float y)
	{					
		objects.add(new EnviromentObject(x, y, false, mScene.resourcesManager.playerBuletTail 
				, 30, scene, mScene,new long[] { 20, 20, 20, 20, 20, 20, 20, 20 },"tail"));
		objects.getLast().setCenter(objects.getLast().getWidth()/2, objects.getLast().getHeight()/2);
	}
	
	/*
	 * Create object shadow
	 * @param x is a X position of finger tail effect
	 * @param x is a Y position of finger tail effect
	 */
	public void setShadow(float x, float y)
	{
		objects.add(new EnviromentObject(x, y, false, resourcesManager.playerBuletTail , 10, scene, mScene,
				new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"shadow"));
	}
	
	
	/*
	 * Create snow effect
	 * @param time is a snow duration
	 */
	public void setSnow(int time)
	{
		snow=true;
		snowTimer=time;
	}
	
	/*
	 * Activate apocalipsys mode(Fire spell) 
	 * @param time is a apocalipsys mode
	 */
	//TODO: Add local gamescene
	public void setApocalipsys(float time, VertexBufferObjectManager vbom, final GameScene scene)
	{
		apocalipsys=true;
		
		for(int i=0;i<20;i++)
		{
			walls.add(new Wall((float)Math.random()*800, (float)Math.random()*1280, time, resourcesManager.wall_region, vbom, "fire"));
			scene.attachChild(walls.getLast());
		}
		
	}
	
	/*
	 * Create stormtroopers (Darth Vaider spell)
	 * @param time is a apocalipsys mode
	 */
	public void setStormtrooper(LinkedList<EnviromentObject> objectList)
	{
		for(int i=0;i<objectList.size();i++)
		{
			objects.add(new EnviromentObject(objectList.get(i).getX(), objectList.get(i).getY(), false,
					resourcesManager.enemyElementMagic , objectList.get(i).getLifeTime(), mScene,
					new long[] { 5, 5, 5, 5, 5, 5, 5, 5 },"stormtrooper"));
			objects.getLast().setRotation(objectList.get(i).getRotation());
		}
		
	}
	

}
