package com.example.gameactive;

import java.util.LinkedList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameSceneEventsController {
	private GameScene sceneHolder;
	
	public GameSceneEventsController(final GameScene scene)
	{
		sceneHolder=scene;
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
    			sceneHolder.weather.setFingerMagic(sceneHolder.playerMagic.element, pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), sceneHolder.vbom);
    		}
    		else
        	{
    			
	        		if((Math.abs(sceneHolder.xLastWall-pSceneTouchEvent.getX())>50)||(Math.abs(sceneHolder.yLastWall-pSceneTouchEvent.getY())>50))
	        		{
		        		
		            	
		        		float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.wallCost);
		        		if(manaK>0.3)
		        		{
		        			sceneHolder.wall.add(new Wall(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), sceneHolder.playerMagic.wallHealth*manaK, sceneHolder.resourcesManager.wall_region, sceneHolder.vbom, sceneHolder.playerMagic.element));             
			        		
		        			if((sceneHolder.playerMagic.element=="water")||(sceneHolder.playerMagic.element=="earth"))
		        			{
		        				sceneHolder.FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
				        		
				        		Body tempBody;
				        		tempBody = PhysicsFactory.createBoxBody(sceneHolder.physicsWorld, sceneHolder.wall.getLast().wallTexture, BodyType.StaticBody, sceneHolder.FIXTURE_DEF);
				        		tempBody.setUserData("wall");
				        		sceneHolder.wall.getLast().initBody(tempBody);
				        		
				        		sceneHolder.physicsWorld.registerPhysicsConnector(new PhysicsConnector(sceneHolder.wall.getLast().wallTexture, sceneHolder.wall.getLast().wallBody, true, false));
		        			}
		        			
		        			if((sceneHolder.xLastWall-pSceneTouchEvent.getX())!=0)
			       			 {
			       				 
			       				 if(pSceneTouchEvent.getX()-sceneHolder.xLastWall>0)	
			       					sceneHolder.wall.getLast().wallTexture.setRotation((float)(Math.atan((pSceneTouchEvent.getY()-sceneHolder.yLastWall)/(pSceneTouchEvent.getX()-sceneHolder.xLastWall))*180/3.14));
			       				 else
			       					sceneHolder.wall.getLast().wallTexture.setRotation((float)(Math.atan((pSceneTouchEvent.getY()-sceneHolder.yLastWall)/(pSceneTouchEvent.getX()-sceneHolder.xLastWall))*180/3.14));
			       			 }	
			                
		        			sceneHolder.attachChild(sceneHolder.wall.getLast().wallTexture);
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
        		sceneHolder.player.setDist(sceneHolder.startX, sceneHolder.startY);
        	}
        	else
        	if(((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))&&(choosenMagic==1))
        	{
        		float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.buletCost);
        		if(manaK>0.3)
        		{
        			sceneHolder.bulet.add(new Bulet(sceneHolder.player.getDamage()*manaK,sceneHolder.playerMagic.element));
        			sceneHolder.bulet.getLast().init(sceneHolder.lastX,sceneHolder.lastY,sceneHolder.lastTapTime);
        			sceneHolder.bulet.getLast().loadBulet(sceneHolder.resourcesManager.player_bulet_region, sceneHolder.vbom, sceneHolder.wall, sceneHolder.player, sceneHolder.enemyList);
        			sceneHolder.bulet.getLast().startFly(pSceneTouchEvent.getX(),pSceneTouchEvent.getY(),pSceneTouchEvent.getMotionEvent().getEventTime()+10);
        			sceneHolder.attachChild(sceneHolder.bulet.getLast().player_bulet);
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
            		if(sceneHolder.playerMagic.element.equals("fire"))
            			sceneHolder.weather.setApocalipsys((int)(sceneHolder.playerMagic.elementPower*manaK), sceneHolder.vbom, sceneHolder);
            		if(sceneHolder.playerMagic.element.equals("water"))
            			sceneHolder.weather.setRain((int)(sceneHolder.playerMagic.elementPower*manaK));
            		if(sceneHolder.playerMagic.element.equals("wind"))
            		{
            			Vector2 tempVector=new Vector2((sceneHolder.startX-pSceneTouchEvent.getX())/10,(sceneHolder.startY-pSceneTouchEvent.getY())/10);
            			sceneHolder.weather.setWind(tempVector, (int)(sceneHolder.playerMagic.elementPower*manaK));
            		}
            		if(sceneHolder.playerMagic.element.equals("earth"))
            			sceneHolder.weather.setApocalipsys(manaK*tapLen, sceneHolder.vbom, sceneHolder);
            		
            	}
            }
           else
           if(((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))&&(sceneHolder.freePlayer))
           {
        	   	float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.bufCost);
	       		if(manaK>0.3)
	       		{
	       			float tempTime=sceneHolder.playerMagic.bufTime*manaK;
	       			sceneHolder.buf.add(new Buf(sceneHolder.playerMagic.bufPower*manaK, (long)tempTime, sceneHolder.playerMagic.bufType));
	       			sceneHolder.buf.getLast().init(sceneHolder.startX,sceneHolder.startY,sceneHolder.startTapTime);
	       			sceneHolder.buf.getLast().loadBulet(sceneHolder.resourcesManager.player_buf_region, sceneHolder.vbom, sceneHolder.wall, sceneHolder.player, sceneHolder.enemyList);
	       			sceneHolder.buf.getLast().startFly(pSceneTouchEvent.getX(),pSceneTouchEvent.getY(),pSceneTouchEvent.getMotionEvent().getEventTime());
	       			sceneHolder.attachChild(sceneHolder.buf.getLast().player_bulet);
	       			sceneHolder.bufCount++;
	       		}
           }else
        	if(((sceneHolder.touchPlayer)||(sceneHolder.player.touchPlayerShadow))&&(sceneHolder.freeEnemy))
            {
        		float manaK=sceneHolder.player.getMana(sceneHolder.playerMagic.debufCost);
	       		if(manaK>0.3)
	       		{
	       			float tempTime=sceneHolder.playerMagic.debufTime*manaK;
	       			sceneHolder.buf.add(new Buf(sceneHolder.playerMagic.debufPower*manaK, (long)tempTime, sceneHolder.playerMagic.debufType));
	       			sceneHolder.buf.getLast().init(sceneHolder.startX,sceneHolder.startY,sceneHolder.startTapTime);
	       			sceneHolder.buf.getLast().loadBulet(sceneHolder.resourcesManager.player_debuf_region, sceneHolder.vbom, sceneHolder.wall, sceneHolder.player, sceneHolder.enemyList);
	       			sceneHolder.buf.getLast().startFly(pSceneTouchEvent.getX(),pSceneTouchEvent.getY(),pSceneTouchEvent.getMotionEvent().getEventTime());
	       			sceneHolder.attachChild(sceneHolder.buf.getLast().player_bulet);
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
            	
            		
            		for(int i=0;i<sceneHolder.textList.size();i++)
            		{
            			sceneHolder.textList.get(i).update();
            			if(sceneHolder.textList.get(i).remain<=0)
            			{
            				sceneHolder.detachChild(sceneHolder.textList.get(i));
            				sceneHolder.textList.remove(i);
            			}
            		}
            	
            		for(int i=0;i<sceneHolder.weather.walls.size();i++)
            		{
            			if(sceneHolder.weather.walls.get(i).isDestroy())
            				sceneHolder.detachChild(sceneHolder.weather.walls.get(i).wallTexture);
            		}

            		sceneHolder.weather.updateWeather(sceneHolder.vbom);
            		
            		//playerHealthText.setText("Health: " + player.health);
            		//playerManaText.setText("Mana: " + player.manaPower);

            	    //enemyHealthText.setText("Health: " + enemyList.get(0).health);
            	    //enemyManaText.setText("Mana: " + enemyList.get(0).manaPower);
            	   
            		if(!sceneHolder.player.getIsDead())
            		{
            			if(sceneHolder.player.health>0)
            				sceneHolder.playerStatus.update(sceneHolder.player.getX(), sceneHolder.player.getY(), sceneHolder.player.health, sceneHolder.player.manaPower);
            			else
            				sceneHolder.playerStatus.update(sceneHolder.player.getX(), sceneHolder.player.getY(), 0, sceneHolder.player.manaPower);
            			
            			if(sceneHolder.player.tempText.size()>0)
            			{
            				sceneHolder.addTextList(sceneHolder.player.tempText,sceneHolder.player.getWidth());
            				sceneHolder.player.tempText=new LinkedList<TextInformHolder>();
            			}
            			
            			
            			sceneHolder.player.fillMana();
            			sceneHolder.player.move();
            			sceneHolder.player.updateEffects(sceneHolder.gameHUD);
            		
            		
            		for(int i=0;i<sceneHolder.enemyList.size();i++)
            		{
            			sceneHolder.enemyStatus.get(i).update(sceneHolder.enemyList.get(i).getX(), sceneHolder.enemyList.get(i).getY(), sceneHolder.enemyList.get(i).health, sceneHolder.enemyList.get(i).manaPower);
            			
            			if(!sceneHolder.enemyList.get(i).isDead)
            			{
            				sceneHolder.enemyList.get(i).fillMana();
            				sceneHolder.enemyList.get(i).move();
            				sceneHolder.enemyList.get(i).updateEffects(sceneHolder.gameHUD);
            			}
            		}
            	    
            	    
            	    for(int i=0;i<sceneHolder.enemyList.size();i++)
            	    {
            	    	if(!sceneHolder.enemyList.get(i).isDead)
            			{
            	    		
            	    		if(sceneHolder.enemyList.get(i).tempText.size()>0)
                			{
            	    			sceneHolder.addTextList(sceneHolder.enemyList.get(i).tempText,sceneHolder.enemyList.get(i).getWidth());
                				sceneHolder.enemyList.get(i).tempText=new LinkedList<TextInformHolder>();
                			}
            	    		
	            	    	GameState gameState=new GameState(sceneHolder.playerMagic,sceneHolder.enemyList.get(i),sceneHolder.bulet,sceneHolder.wall,sceneHolder.player,sceneHolder.enemyList);
		            		String action=sceneHolder.enemyList.get(i).update(gameState);
		            		if(action.equals("createWall"))
		            			sceneHolder.gameSceneCreator.createWall(sceneHolder.enemyList.get(i).ai.getWallList(),sceneHolder.enemyList.get(i));
		            		if(action.equals("createBulet"))
		            			sceneHolder.gameSceneCreator.createBulet(sceneHolder.enemyList.get(i).ai.getBuletList(),sceneHolder.enemyList.get(i));
		            		if(action.equals("attackPlayer"))
		            			sceneHolder.gameSceneCreator.createBulet(sceneHolder.enemyList.get(i).ai.getBuletList(),sceneHolder.enemyList.get(i));
		            		if(action.equals("createStormtrooper"))
		            			sceneHolder.gameSceneCreator.createStormtrooper(sceneHolder.enemyList.get(i).ai.getObjectList());
            			}
            	    	
	            		if(sceneHolder.enemyList.get(i).getIsDead())
	            		{
	            			sceneHolder.detachChild(sceneHolder.enemyStatus.get(i).healthLine);
	            			sceneHolder.detachChild(sceneHolder.enemyStatus.get(i).manaLine);
	            			sceneHolder.enemyStatus.remove(i);
	            			
	            			sceneHolder.detachChild(sceneHolder.enemyList.get(i));
	            			sceneHolder.enemyList.remove(i);
	            		}            		
            	    }
            		
            		for(int i=0;i<sceneHolder.weather.objects.size();i++)
            		{
            			if(sceneHolder.weather.objects.get(i).type.equals("stormtrooper"))
            				if(sceneHolder.weather.objects.get(i).period==19)
            					sceneHolder.gameSceneCreator.createBulet(sceneHolder.weather.objects.get(i).getBuletList(),sceneHolder.enemyList.getFirst());
            		}
            		
	            	for(int i=0; i < sceneHolder.buletCount; i++)
	            	{
	            		if(sceneHolder.bulet.get(i).isRemove)
	            		{
	            			sceneHolder.detachChild(sceneHolder.bulet.get(i).player_bulet);
	            			sceneHolder.bulet.remove(i);
	            			sceneHolder.buletCount--;
	            		}else
	            		{
	            			//weather.setTail(bulet.get(i).element, bulet.get(i).getX(), bulet.get(i).getY(), vbom, playerMagic.element, enemyMagic.element);
	            			sceneHolder.bulet.get(i).update(sceneHolder.weather.getWind());
		            		if((sceneHolder.bulet.get(i).getX()<-20)||(sceneHolder.bulet.get(i).getX()>820))
		            		{
		            			sceneHolder.detachChild(sceneHolder.bulet.get(i).player_bulet);
		            			sceneHolder.bulet.remove(i);
		            			sceneHolder.buletCount--;
		            		}
		            		else
		            		if((sceneHolder.bulet.get(i).getY()<-20)||(sceneHolder.bulet.get(i).getY()>1300))
		            		{
		            			sceneHolder.detachChild(sceneHolder.bulet.get(i).player_bulet);
		            			sceneHolder.bulet.remove(i);
		            			sceneHolder.buletCount--;
		            		}
	            		}
	            	}
	            	
	            	for(int i=0; i < sceneHolder.bufCount; i++)
	            	{
	            		if(sceneHolder.buf.get(i).isRemove)
	            		{
	            			sceneHolder.detachChild(sceneHolder.buf.get(i).player_bulet);
	            			sceneHolder.buf.remove(i);
	            			sceneHolder.bufCount--;
	            		}else
	            		{
	            			
	            			sceneHolder.buf.get(i).update(sceneHolder.weather.getWind());
		            		if((sceneHolder.buf.get(i).getX()<-20)||(sceneHolder.buf.get(i).getX()>820))
		            		{
		            			sceneHolder.detachChild(sceneHolder.buf.get(i).player_bulet);
		            			sceneHolder.buf.remove(i);
		            			sceneHolder.bufCount--;
		            		}
		            		else
		            		if((sceneHolder.buf.get(i).getY()<-20)||(sceneHolder.buf.get(i).getY()>1300))
		            		{
		            			sceneHolder.detachChild(sceneHolder.buf.get(i).player_bulet);
		            			sceneHolder.buf.remove(i);
		            			sceneHolder.bufCount--;
		            		}
	            		}
	            	}
	            	
            	
	            	for(int i=0; i < sceneHolder.wall.size(); i++)
	            	{
	            		sceneHolder.wall.get(i).updateWall();
	            		
	            		if(sceneHolder.wall.get(i).isDestroy())
	            		{
	            			if(sceneHolder.wall.get(i).isBodyInit)
	            			{
	            				PhysicsConnector tempConnector= sceneHolder.physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(sceneHolder.wall.get(i).wallTexture);
	            				sceneHolder.physicsWorld.unregisterPhysicsConnector(tempConnector);
	            				sceneHolder.physicsWorld.destroyBody(sceneHolder.wall.get(i).wallBody);
	            			}
	            			
	            			sceneHolder.detachChild(sceneHolder.wall.get(i).wallTexture);
	            			sceneHolder.wall.remove(i);
	            		}
	            	}
            		}
            	
            }
        });
		
        return false;
    }
}
