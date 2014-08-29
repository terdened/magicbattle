package com.magickbattle.game;

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
import com.magickbattle.game.character.Enemy;
import com.magickbattle.game.gui.MagicBar;
import com.magickbattle.game.magick.Bulet;
import com.magickbattle.game.magick.Wall;

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
				sceneHolder.bulet.add(new Bulet(sceneHolder.resourcesManager.player_bulet_region,temp.damage,temp.element, sceneHolder));
				sceneHolder.bulet.getLast().init(temp.mStartX,temp.mStartY,100);
				sceneHolder.bulet.getLast().startFly(temp.mFinalX,temp.mFinalY,110);
				sceneHolder.attachChild(sceneHolder.bulet.getLast());
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
    			sceneHolder.wall.add(new Wall(wallQueue.get(i).getX(), wallQueue.get(i).getY(), enemy.playerMagic.wallHealth*manaK, sceneHolder.resourcesManager.enemy_wall_region, sceneHolder.vbom, wallQueue.get(i).getElement()));             
	        		
        			if((sceneHolder.wall.getLast().getElement()=="water")||(sceneHolder.wall.getLast().getElement()=="earth"))
        			{
        				sceneHolder.FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		        		
		        		Body tempBody;
		        		tempBody = PhysicsFactory.createBoxBody(sceneHolder.physicsWorld, sceneHolder.wall.getLast(), BodyType.StaticBody, sceneHolder.FIXTURE_DEF);
		        		tempBody.setUserData("wall");
		        		sceneHolder.wall.getLast().initBody(tempBody);
		        		
		        		sceneHolder.physicsWorld.registerPhysicsConnector(new PhysicsConnector(sceneHolder.wall.getLast(), sceneHolder.wall.getLast().getWallBody(), true, false));
        			}
        			sceneHolder.wall.getLast().setRotation(wallQueue.get(i).getRotation()); 
        			sceneHolder.attachChild(sceneHolder.wall.getLast());
    		}
        		
    		
		}
	}
	
	public void createStormtrooper(LinkedList<EnviromentObject> objectQueue)
	{
		for(int i=0;i<objectQueue.size();i++)
		{
			sceneHolder.weather.setStormtrooper(objectQueue);			
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
	
    public void createControllers()
	{
	    
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl (40, 1100, sceneHolder.camera, sceneHolder.resourcesManager.base_region, sceneHolder.resourcesManager.knob_region, 0.1f, 200, sceneHolder.vbom , new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				//player.move(pValueX,pValueY);
				
				if(pValueX!=0)
				{				
					if(pValueX>0)	
						sceneHolder.player.setRotation((float)(Math.atan(pValueY/pValueX)*180/3.14)+90);
					else
						sceneHolder.player.setRotation((float)(Math.atan(pValueY/pValueX)*180/3.14)-90);
				}
				else
					sceneHolder.player.setRotation(0);
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

	public void createMob(String name,int i)
	{
		
	}
	
}
