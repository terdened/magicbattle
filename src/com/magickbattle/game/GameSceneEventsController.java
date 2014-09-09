package com.magickbattle.game;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.magickbattle.game.magick.Buf;
import com.magickbattle.game.magick.Bulet;
import com.magickbattle.game.magick.Wall;

public class GameSceneEventsController {
	private final GameScene sceneHolder;
	
	public GameSceneEventsController(final GameScene scene)
	{
		sceneHolder=scene;
	}
	
	public void removeBodies()
	{
		for(int i=0; i<sceneHolder.mEnemiesToRemove.size();i++)
			sceneHolder.detachEnemy(sceneHolder.mEnemiesToRemove.get(i));
		
		sceneHolder.mEnemiesToRemove.clear();
	}
	
	public void removeWalls()
	{
		for(int i=0; i<sceneHolder.mWallToRemove.size();i++)
			sceneHolder.detachWall(sceneHolder.mWallToRemove.get(i));
		
		sceneHolder.mWallToRemove.clear();
	}
	
	public void removeBulets()
	{
		for(int i=0; i<sceneHolder.mBuletToRemove.size();i++)
			sceneHolder.detachBulet(sceneHolder.mBuletToRemove.get(i));
		
		sceneHolder.mBuletToRemove.clear();
	}
	
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
    {
    	if(pSceneTouchEvent.isActionMove())
    	{
    		sceneHolder.lastX=sceneHolder.nowX;
    		sceneHolder.lastY=sceneHolder.nowY;
    		
    		sceneHolder.nowX=pSceneTouchEvent.getX();
    		sceneHolder.nowY=pSceneTouchEvent.getY();
    		
    		sceneHolder.lastTapTime=pSceneTouchEvent.getMotionEvent().getEventTime();
    		
    		if((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))
    		{
    			sceneHolder.weather.setFingerMagic(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
    		}
    		else
        	{
        		if((Math.abs(sceneHolder.xLastWall-pSceneTouchEvent.getX())>50)||(Math.abs(sceneHolder.yLastWall-pSceneTouchEvent.getY())>50))
        		{
	        		float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.wallCost);
	        		if(manaK>0.3)
	        		{
	        			sceneHolder.wall.add(new Wall(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(),
	        					sceneHolder.playerMagic.wallHealth*manaK, 
	        					sceneHolder.resourcesManager.player_region.mWallRegion, 
	        					sceneHolder.vbom, sceneHolder.playerMagic.element, sceneHolder));             
		        		
	        			if((sceneHolder.player.playerMagic.element.equals("water"))||(sceneHolder.player.playerMagic.element.equals("earth")))
	        			{
	        				sceneHolder.FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
			        		
			        		Body tempBody;
			        		tempBody = PhysicsFactory.createBoxBody(sceneHolder.physicsWorld, sceneHolder.wall.getLast(), BodyType.StaticBody, sceneHolder.FIXTURE_DEF);
			        		tempBody.setUserData("wall");
			        		sceneHolder.wall.getLast().initBody(tempBody);
			        		
			        		sceneHolder.physicsWorld.registerPhysicsConnector(new PhysicsConnector(sceneHolder.wall.getLast(), sceneHolder.wall.getLast().getWallBody(), true, false));
	        			}
	        			
	        			if((sceneHolder.xLastWall-pSceneTouchEvent.getX())!=0)
		       			 {
		       				 
		       				 if(pSceneTouchEvent.getX()-sceneHolder.xLastWall>0)	
		       					sceneHolder.wall.getLast().setRotation((float)(Math.atan((pSceneTouchEvent.getY()-sceneHolder.yLastWall)/(pSceneTouchEvent.getX()-sceneHolder.xLastWall))*180/3.14));
		       				 else
		       					sceneHolder.wall.getLast().setRotation((float)(Math.atan((pSceneTouchEvent.getY()-sceneHolder.yLastWall)/(pSceneTouchEvent.getX()-sceneHolder.xLastWall))*180/3.14));
		       			 }	
		                
	        			sceneHolder.mMagicLayer.attachChild(sceneHolder.wall.getLast());
		        		sceneHolder.xLastWall=pSceneTouchEvent.getX();
		        		sceneHolder.yLastWall=pSceneTouchEvent.getY();
	        		}
        		}
        	}
    	}
        if (pSceneTouchEvent.isActionDown())
        {
        	sceneHolder.startX=pSceneTouchEvent.getX();
        	sceneHolder.startY=pSceneTouchEvent.getY();
        	sceneHolder.xLastWall=sceneHolder.startX;
        	sceneHolder.yLastWall=sceneHolder.startY;
        	sceneHolder.startTapTime=pSceneTouchEvent.getMotionEvent().getEventTime();   	
        }
        
        if (pSceneTouchEvent.isActionUp())
        {
        	int choosenMagic=sceneHolder.magicBar.getChoosenIcon();
        	
        	if((Math.abs(sceneHolder.startX-pSceneTouchEvent.getX())<5)&&(Math.abs(sceneHolder.startY-pSceneTouchEvent.getY())<5))
        	{
        		sceneHolder.player.setDest(sceneHolder.startX, sceneHolder.startY);
        	}
        	else
        	if(((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))&&(choosenMagic==1))
        	{
        		float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.buletCost);
        		if(manaK>0.3)
        		{
        			sceneHolder.bulet.add(new Bulet(sceneHolder.resourcesManager.player_region.mBuletRegion,sceneHolder.player.getDamage(),sceneHolder.playerMagic.element, sceneHolder));
        			sceneHolder.bulet.getLast().init(sceneHolder.lastX,sceneHolder.lastY,sceneHolder.lastTapTime);
        			sceneHolder.bulet.getLast().startFly(pSceneTouchEvent.getX(),pSceneTouchEvent.getY(),pSceneTouchEvent.getMotionEvent().getEventTime()+10);
        			sceneHolder.mMagicLayer.attachChild(sceneHolder.bulet.getLast());
        			sceneHolder.bulet.getLast().createShadow(sceneHolder.vbom, sceneHolder.resourcesManager.light_shadow);
        			sceneHolder.buletCount++;
        		}
        	}
        	else
        	if(((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))&&(choosenMagic==3))
            {
        		float tapLen=(float)Math.sqrt((double)(Math.abs(sceneHolder.startX-pSceneTouchEvent.getX())*Math.abs(sceneHolder.startX-pSceneTouchEvent.getX())+Math.abs(sceneHolder.startY-pSceneTouchEvent.getY())*Math.abs(sceneHolder.startY-pSceneTouchEvent.getY())));
        		
            	float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.elementCost*tapLen);
            	if(manaK>0.3)
            	{
            		
            	}
            }
           else
           if(((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))&&(sceneHolder.freePlayer))
           {
        	   	float manaK=sceneHolder.player.getMana(sceneHolder.player.playerMagic.bufCost);
	       		if(manaK>0.3)
	       		{
	       			float tempTime=sceneHolder.playerMagic.bufTime*manaK;
	       			sceneHolder.buf.add(new Buf(sceneHolder.resourcesManager.player_region.mBufRegion,sceneHolder,sceneHolder.playerMagic.bufPower*manaK, (long)tempTime, sceneHolder.playerMagic.bufType));
	       			sceneHolder.buf.getLast().init(sceneHolder.startX,sceneHolder.startY,sceneHolder.startTapTime);
	       			sceneHolder.buf.getLast().startFly(pSceneTouchEvent.getX(),pSceneTouchEvent.getY(),pSceneTouchEvent.getMotionEvent().getEventTime());
	       			sceneHolder.attachChild(sceneHolder.buf.getLast());
	       			sceneHolder.bufCount++;
	       		}
           }else
        	if(((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))&&(sceneHolder.freeEnemy))
            {
        		float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.debufCost);
	       		if(manaK>0.3)
	       		{
	       			float tempTime=sceneHolder.playerMagic.debufTime*manaK;
	       			sceneHolder.buf.add(new Buf(sceneHolder.resourcesManager.player_region.mBufRegion,sceneHolder,sceneHolder.playerMagic.bufPower*manaK, (long)tempTime, sceneHolder.playerMagic.bufType));
	       			sceneHolder.buf.getLast().init(sceneHolder.startX,sceneHolder.startY,sceneHolder.startTapTime);
	       			sceneHolder.buf.getLast().startFly(pSceneTouchEvent.getX(),pSceneTouchEvent.getY(),pSceneTouchEvent.getMotionEvent().getEventTime());
	       			sceneHolder.attachChild(sceneHolder.buf.getLast());
	       			sceneHolder.bufCount++;
	       		}
            }
        	sceneHolder.player.touchPlayerShadow=false;
        	sceneHolder.touchEnemy=false;
        	sceneHolder.touchPlayer=false;
        	sceneHolder.freePlayer=false;
        	sceneHolder.freeEnemy=false;
        	
        }
        
        return false;
    }
    
    public boolean onSceneUpdateEvent(Scene pScene)
    {
    	pScene.registerUpdateHandler(new IUpdateHandler() {
    		 
            @Override
            public void reset() { }
     
            @Override
            public void onUpdate(final float pSecondsElapsed) 
            {            	
            	removeBodies();
            	removeWalls();
            	removeBulets();
        		for(int i=0;i<sceneHolder.textList.size();i++)
        		{
        			if(sceneHolder.textList.get(i).getRemain()<=0)
        			{
        				sceneHolder.detachChild(sceneHolder.textList.get(i));
        				sceneHolder.textList.remove(i);
        			}
        		}
        		sceneHolder.weather.updateWeather(sceneHolder.vbom);
            }
        });
		
        return false;
    }
}
