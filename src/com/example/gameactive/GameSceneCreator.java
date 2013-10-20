package com.example.gameactive;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.util.GLState;

import android.opengl.GLES20;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameSceneCreator {
	private GameScene sceneHolder;
	
	public GameSceneCreator(final GameScene scene)
	{
		sceneHolder=scene;
	}

	public void createBulet(LinkedList<Bulet> buletQueue, Enemy enemy)
	{
		for(int i=0;i<buletQueue.size();i++)
		{
   		
			Bulet temp=buletQueue.get(i);
			
			float manaK=enemy.getMana(enemy.playerMagic.buletCost);
			if(manaK>0.3)
			{
				sceneHolder.bulet.add(new Bulet(temp.damage*manaK,temp.element));
				sceneHolder.bulet.getLast().init(temp.startX,temp.startY,100);
				sceneHolder.bulet.getLast().loadBulet(sceneHolder.resourcesManager.enemy_bulet_region, sceneHolder.vbom, sceneHolder.wall, sceneHolder.player, sceneHolder.enemyList);
				sceneHolder.bulet.getLast().startFly(temp.finalX,temp.finalY,110);
				sceneHolder.attachChild(sceneHolder.bulet.getLast().player_bulet);
				sceneHolder.bulet.getLast().createShadow(sceneHolder.vbom, sceneHolder.resourcesManager.light_shadow);
				sceneHolder.buletCount++;
			}
        		
    		
		}
	}
	
	public void createWall(LinkedList<Wall> wallQueue, Enemy enemy)
	{
		for(int i=0;i<wallQueue.size();i++)
		{
   		
			float manaK=enemy.getMana(enemy.playerMagic.wallCost);
			
    		if(manaK>0.3)
    		{   
    		//Заменить на EnemyMagic
    			sceneHolder.wall.add(new Wall(wallQueue.get(i).x, wallQueue.get(i).y, enemy.playerMagic.wallHealth*manaK, sceneHolder.resourcesManager.enemy_wall_region, sceneHolder.vbom, wallQueue.get(i).element));             
	        		
        			if((sceneHolder.wall.getLast().element=="water")||(sceneHolder.wall.getLast().element=="earth"))
        			{
        				sceneHolder.FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		        		
		        		Body tempBody;
		        		tempBody = PhysicsFactory.createBoxBody(sceneHolder.physicsWorld, sceneHolder.wall.getLast().wallTexture, BodyType.StaticBody, sceneHolder.FIXTURE_DEF);
		        		tempBody.setUserData("wall");
		        		sceneHolder.wall.getLast().initBody(tempBody);
		        		
		        		sceneHolder.physicsWorld.registerPhysicsConnector(new PhysicsConnector(sceneHolder.wall.getLast().wallTexture, sceneHolder.wall.getLast().wallBody, true, false));
        			}
        			sceneHolder.wall.getLast().wallTexture.setRotation(wallQueue.get(i).rotation); 
        			sceneHolder.attachChild(sceneHolder.wall.getLast().wallTexture);
    		}
        		
    		
		}
	}
	
	public void createStormtrooper(LinkedList<enviromentObject> objectQueue)
	{
		for(int i=0;i<objectQueue.size();i++)
		{
   		
			//float manaK=enemy.getMana(enemyMagic.wallCost);
			
    		//if(manaK>0.3)
    		//{   
    		//Заменить на EnemyMagic
        			//wall.add(new Wall(wallQueue.get(i).x, wallQueue.get(i).y, enemyMagic.wallHealth*manaK, resourcesManager.enemy_wall_region, vbom, wallQueue.get(i).element));             
			sceneHolder.weather.setStormtrooper(sceneHolder.vbom, sceneHolder, objectQueue);			
        			/*if((wall.getLast().element=="water")||(wall.getLast().element=="earth"))
        			{
	        			FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		        		
		        		Body tempBody;
		        		tempBody = PhysicsFactory.createBoxBody(physicsWorld, wall.getLast().wallTexture, BodyType.StaticBody, FIXTURE_DEF);
		        		tempBody.setUserData("wall");
		        		wall.getLast().initBody(tempBody);
		        		
		        		physicsWorld.registerPhysicsConnector(new PhysicsConnector(wall.getLast().wallTexture, wall.getLast().wallBody, true, false));
        			}*/
        			//wall.getLast().wallTexture.setRotation(wallQueue.get(i).rotation); 
	        		//attachChild(weather.objects.getLast().texture);
    		//}
        		
    		
		}
	}
	
	public void createRiver()
    {
    	/*river = new Sprite(300, 400, resourcesManager.river_region, vbom);
    	river.setRotation(90);
    	river.setScale(1.65f,1.8f);
    	FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
    	riverBody = PhysicsFactory.createBoxBody(physicsWorld, river, BodyType.StaticBody, FIXTURE_DEF);
    	riverBody.setUserData("river");
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(river, riverBody, true, false));
    	attachChild(river);
    	*/
    }
    
    public void createBackground()
    {
    	
    	
    	sceneHolder.bckgr = new Sprite(0, 0, sceneHolder.resourcesManager.gamebkg_region, sceneHolder.vbom)
    	{
    	    @Override
    	    protected void preDraw(GLState pGLState, Camera pCamera) 
    	    {
    	       super.preDraw(pGLState, pCamera);
    	       pGLState.enableDither();
    	    }
    	};
    	sceneHolder.bckgr.setRotation(90);        
    	sceneHolder.bckgr.setScale(1.65f,1.8f);
    	sceneHolder.bckgr.setPosition(0, 400);
    	sceneHolder.attachChild(sceneHolder.bckgr);
        /*attachChild(new Sprite(0, 0, resourcesManager.menu_background_region, vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });*/
    }
	
    public void createControllers()
	{
	    
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl (40, 1100, sceneHolder.camera, sceneHolder.resourcesManager.base_region, sceneHolder.resourcesManager.knob_region, 0.1f, 200, sceneHolder.vbom , new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				//player.move(pValueX,pValueY);
				
				if(pValueX!=0)
				{				
					if(pValueX>0)	
						sceneHolder.player.rotate((float)(Math.atan(pValueY/pValueX)*180/3.14)+90);
					else
						sceneHolder.player.rotate((float)(Math.atan(pValueY/pValueX)*180/3.14)-90);
				}
				else
					sceneHolder.player.rotate(0);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				
			}
		});
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();
	    
		sceneHolder.setChildScene(analogOnScreenControl);
	    
	}
	
	public void createMagicBar()
	{
		sceneHolder.magicBar = new MagicBar(10, 850, 50, 20);
		sceneHolder.magicBar.loadImages(sceneHolder.resourcesManager.bulet_icon, sceneHolder.resourcesManager.wall_icon, sceneHolder.resourcesManager.nature_icon,
				sceneHolder.resourcesManager.buf_icon, sceneHolder.resourcesManager.debuf_icon, sceneHolder.vbom);
		
		sceneHolder.gameHUD.registerTouchArea(sceneHolder.magicBar.buletIcon);
		sceneHolder.gameHUD.registerTouchArea(sceneHolder.magicBar.wallIcon);
		sceneHolder.gameHUD.registerTouchArea(sceneHolder.magicBar.natureIcon);
		sceneHolder.gameHUD.registerTouchArea(sceneHolder.magicBar.bufIcon);
		sceneHolder.gameHUD.registerTouchArea(sceneHolder.magicBar.debufIcon);
		
		sceneHolder.gameHUD.attachChild(sceneHolder.magicBar.buletIcon);
		sceneHolder.gameHUD.attachChild(sceneHolder.magicBar.wallIcon);
		sceneHolder.gameHUD.attachChild(sceneHolder.magicBar.natureIcon);
		sceneHolder.gameHUD.attachChild(sceneHolder.magicBar.bufIcon);
		sceneHolder.gameHUD.attachChild(sceneHolder.magicBar.debufIcon);
		
	}
	
	public void createHUD()
	{
		sceneHolder.gameHUD = new HUD();
		sceneHolder.camera.setHUD(sceneHolder.gameHUD);
	}
	
	public void createPhysics()
	{
		sceneHolder.physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false); 
		sceneHolder.registerUpdateHandler(sceneHolder.physicsWorld);
	}


}
