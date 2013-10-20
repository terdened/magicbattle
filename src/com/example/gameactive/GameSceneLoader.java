package com.example.gameactive;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.LevelLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.xml.sax.Attributes;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameSceneLoader {
	private GameScene sceneHolder;
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	private static final String TAG_ENTITY_ATTRIBUTE_ROTATION = "rotation";
	private static final String TAG_ENTITY_ATTRIBUTE_ELEMENT = "element";
	private static final String TAG_ENTITY_ATTRIBUTE_NAME = "name";	
	private static final String TAG_ENTITY_ATTRIBUTE_VALUE = "value";
	
	private static final String TAG_AI_ATTRIBUTE_TYPE = "type";	
	private static final String TAG_AI_ATTRIBUTE_VALUE_RECEPTOR = "receptor";
	
	private static final String TAG_AI = "ai";
	
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ENEMY = "enemy";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_OBJECT = "object";
	
	public GameSceneLoader(final GameScene scene)
	{
		sceneHolder=scene;
	}
	
	public void loadLevel(int levelID)
	{
	    final LevelLoader levelLoader = new LevelLoader();
	    
	    final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
	    
	    levelLoader.registerEntityLoader(LevelConstants.TAG_LEVEL,new IEntityLoader()
	    {
	        public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) 
	        {
	            final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
	            final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
	            
	            sceneHolder.camera.setBounds(0, 0, width, height); // here we set camera bounds
	            sceneHolder.camera.setBoundsEnabled(true);

	            return sceneHolder;
	        }
	    });
	    
	    
	    
	    
	    
	    /*levelLoader.registerEntityLoader(TAG_AI_ATTRIBUTE_VALUE_RECEPTOR,new IEntityLoader()
	    {
	        public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) 
	        {
	            String temp = SAXUtils.getAttributeOrThrow(pAttributes, TAG_AI_ATTRIBUTE_TYPE);
	            
	            Receptor receptor=new ReceptorDamage(enemy.ai.receptors.size()+1);
	            
	            if(temp=="isDamage")
	            	receptor=new ReceptorDamage(enemy.ai.receptors.size()+1);
	            if(temp=="isWallRepeat")
	            	receptor=new ReceptorRepeatControl(enemy.ai.receptors.size()+1,"createWall");
	            if(temp=="isBuletRepeat")
	            	receptor=new ReceptorRepeatControl(enemy.ai.receptors.size()+1,"createBulet");
	            
	            enemy.ai.receptors.add(receptor);

	            return GameScene.this;
	        }
	    });
	    */
	       
	    levelLoader.registerEntityLoader(TAG_ENTITY,new IEntityLoader()
	    {
	        public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) 
	        {
	            final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
	            final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
	            final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
	            final String rotation = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_ROTATION);
	            final String value = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_VALUE);
	            
	            final Sprite levelObject;
	            
	            if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER))
	            {
	            	ITiledTextureRegion player_region=sceneHolder.resourcesManager.player_region;
	            	

	            	sceneHolder.player = new Player(x, y, sceneHolder.vbom, sceneHolder.camera, sceneHolder.physicsWorld, player_region)
	                {
	                    @Override
	                    public void onDie()
	                    {
	                    	                  	
	                    }
	                    
	                    @Override
	                    public void setBuf(float power, long time, String type)
	               	 	{
	                    	ITextureRegion effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
	                    	
	                    	if(effects.size()==0)
		               			 effects.add(new Effect(10,  140,  power,  time, effect_Texture, sceneHolder.vbom, type));
		               		 else
		               			 effects.add(new Effect(effects.getLast().x+40,  140,  power,  time, effect_Texture, sceneHolder.vbom, type));
	                    	
	                    	if((type.equals("speed"))&&(power<0))
	                    	{
	                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.speedDownBuf);
	                    		this.addBufAnimation(effects.getLast().effectAnimation);
	                    		effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
	                    	}
	                    	if((type.equals("speed"))&&(power>=0))
	                    	{
	                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.speedUpBuf);
	                    		this.addBufAnimation(effects.getLast().effectAnimation);
	                    		effect_Texture = sceneHolder.resourcesManager.speedPlusIcon;
	                    	}
	                    	
	                    	if((type.equals("health"))&&(power<0))
	                    	{
	                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.healthDownBuf);
	                    		this.addBufAnimation(effects.getLast().effectAnimation);
	                    		effect_Texture = sceneHolder.resourcesManager.healthMinusIcon;
	                    	}
	                    	if((type.equals("health"))&&(power>=0))
	                    	{
	                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.healthUpBuf);
	                    		this.addBufAnimation(effects.getLast().effectAnimation);
	                    		effect_Texture = sceneHolder.resourcesManager.healthPlusIcon;
	                    	}
	                    	
	                    	if((type.equals("damage"))&&(power<0))
	                    	{
	                    		effect_Texture = sceneHolder.resourcesManager.healthMinusIcon;
	                    	}
	                    	if((type.equals("damage"))&&(power>=0))
	                    	{
	                    		effect_Texture = sceneHolder.resourcesManager.healthPlusIcon;
	                    	}
	                    	
		               		 
		               		 
	                    	sceneHolder.gameHUD.attachChild(effects.getLast().effectTexture);
	               	 	}
	                    
	                    
	                    @Override
	                    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
	                    {
	                        if (pSceneTouchEvent.isActionDown())
	                        {
	                        	sceneHolder.touchPlayer=true;
	                        }
	                        if (pSceneTouchEvent.isActionUp())
	                        {
	                        	if(sceneHolder.touchPlayer)
	                        	{
	                        		this.setBuf(this.playerMagic.bufPower, this.playerMagic.bufTime, this.playerMagic.bufType);
	                        	}
	                        	sceneHolder.freePlayer=true;
	                        	sceneHolder.touchPlayer=false;
	                        }
	                        return true;
	                    };
	                    
	                };
	                sceneHolder.player.createShadow(sceneHolder.vbom, sceneHolder.resourcesManager.dark_shadow);
	                levelObject = sceneHolder.player;
	                sceneHolder.playerStatus=new PlayerStatus(sceneHolder.player.getX(),sceneHolder.player.getY(),sceneHolder.vbom, sceneHolder.resourcesManager.healthLine, sceneHolder.resourcesManager.manaLine, sceneHolder.player.getWidth(),  sceneHolder.player.maxHealth,  sceneHolder.player.maxMana);
	                sceneHolder.attachChild(sceneHolder.playerStatus.healthLine);
	                sceneHolder.attachChild(sceneHolder.playerStatus.manaLine);
	            }
	            else
	            	if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ENEMY))
		            {
		            	ITiledTextureRegion player_region=sceneHolder.resourcesManager.enemys_regions[sceneHolder.enemyList.size()];
		            	
			            final String element = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_ELEMENT);
			            final String name = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_NAME);	
		            	
		                Enemy enemy = new Enemy(x, y, sceneHolder.vbom, sceneHolder.camera, sceneHolder.physicsWorld, player_region )
		                {
		                    @Override
		                    public void onDie()
		                    {
		                    	                  	
		                    }
		                    
		                    @Override
		                    public void setBuf(float power, long time, String type)
		               	 	{
		                    	ITextureRegion effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
		                    	
		                    	if(effects.size()==0)
			               			 effects.add(new Effect(10,  140,  power,  time, effect_Texture, sceneHolder.vbom, type));
			               		 else
			               			 effects.add(new Effect(effects.getLast().x+40,  140,  power,  time, effect_Texture, sceneHolder.vbom, type));
		                    	
		                    	if((type.equals("speed"))&&(power<0))
		                    	{
		                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.speedDownBuf);
		                    		this.addBufAnimation(effects.getLast().effectAnimation);
		                    		effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
		                    	}
		                    	if((type.equals("speed"))&&(power>=0))
		                    	{
		                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.speedUpBuf);
		                    		this.addBufAnimation(effects.getLast().effectAnimation);
		                    		effect_Texture = sceneHolder.resourcesManager.speedPlusIcon;
		                    	}
		                    	
		                    	if((type.equals("health"))&&(power<0))
		                    	{
		                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.healthDownBuf);
		                    		this.addBufAnimation(effects.getLast().effectAnimation);
		                    		effect_Texture = sceneHolder.resourcesManager.healthMinusIcon;
		                    	}
		                    	if((type.equals("health"))&&(power>=0))
		                    	{
		                    		effects.getLast().setAnimation(sceneHolder.vbom, sceneHolder.resourcesManager.healthUpBuf);
		                    		this.addBufAnimation(effects.getLast().effectAnimation);
		                    		effect_Texture = sceneHolder.resourcesManager.healthPlusIcon;
		                    	}
		                    	
		                    	if((type.equals("damage"))&&(power<0))
		                    	{
		                    		effect_Texture = sceneHolder.resourcesManager.healthMinusIcon;
		                    	}
		                    	if((type.equals("damage"))&&(power>=0))
		                    	{
		                    		effect_Texture = sceneHolder.resourcesManager.healthPlusIcon;
		                    	}
		                    	
			               		 
			               		 
		                    	sceneHolder.gameHUD.attachChild(effects.getLast().effectTexture);
		               	 	}
		                    
		                    @Override
		                    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		                    {
		                    	if (pSceneTouchEvent.isActionDown())
		                        {
		                    		sceneHolder.touchEnemy=true;
		                        }
		                    	
		                        if (pSceneTouchEvent.isActionUp())
		                        {
		                        	if(sceneHolder.touchEnemy)
		                        	{
		                        		this.setBuf(this.oppositeMagic.debufPower, this.oppositeMagic.debufTime, this.oppositeMagic.debufType);
		                        	}
		                        	sceneHolder.freeEnemy=true;
		                        	sceneHolder.touchEnemy=false;
		                        }
		                        return true;
		                    };
		                    
		                };
		                enemy.name=name;
		                

		                try
		        	    {
		        		    SAXParserFactory factory = SAXParserFactory.newInstance();
		        			SAXParser saxParser = factory.newSAXParser();
		        			
		        		    MagicParser tempPlayerMagic=new MagicParser();

		        		    InputStream curDir = sceneHolder.activity.getAssets().open("level/"+name+"Magic.lvl");
		        		    saxParser.parse(curDir, tempPlayerMagic);
		        		    enemy.playerMagic=tempPlayerMagic.getResult();
		        		    
		        		    
		        		    if(!tempPlayerMagic.tempHP.equals("none"))
		        		    {
			        		    enemy.health=Float.parseFloat(tempPlayerMagic.tempHP);
			        		    enemy.maxHealth=Float.parseFloat(tempPlayerMagic.tempHP);
			        		    enemy.maxMana=Float.parseFloat(tempPlayerMagic.tempMP);
			        		    enemy.manaPower=Float.parseFloat(tempPlayerMagic.tempMP);
			        		    enemy.manaSpeed=Float.parseFloat(tempPlayerMagic.tempMPS);
		        		    }
		        	    }
		        	    catch (Exception e) 
		        	    {
		        	        e.printStackTrace();
		        	    }
		                //enemyMagic=enemy.playerMagic;
		                
		                /*enemy.playerMagic=new PlayerMagic();
		                
		                enemy.playerMagic.element=element;
		                enemy.playerMagic.initBuf("health", 0.3f, 100,30);
		                enemy.playerMagic.initDebuf("speed", -2, 300,30);
		        		enemy.playerMagic.initBulet(5,1);
		        		enemy.playerMagic.initWall(2,10);
		        		enemy.playerMagic.initElement(1, 400);
		                */
		                
		                
		                
		                
		                enemy.element=enemy.playerMagic.element;
		                enemy.createShadow(sceneHolder.vbom, sceneHolder.resourcesManager.dark_shadow);
		                enemy.initAI();
		                

		        	    try
		        	    {
		        		    SAXParserFactory factory = SAXParserFactory.newInstance();
		        			SAXParser saxParser = factory.newSAXParser();
		        			
		        		    AIParser tempAI=new AIParser();
		        		    //data/app/com.example.gameactive-1.apk
		        		    // = "/MagicBattle/assets/level/1.lvl";
		        		    InputStream curDir = sceneHolder.activity.getAssets().open("level/"+name+"AI.lvl");
		        		    saxParser.parse(curDir, tempAI);
		        		    enemy.ai=tempAI.ai;
		        	    }
		        	    catch (Exception e) 
		        	    {
		        	        e.printStackTrace();
		        	    }
		                
		        	    sceneHolder.enemyList.add(enemy);
		                levelObject = enemy;
		                sceneHolder.enemyStatus.add(new PlayerStatus(enemy.getX(),enemy.getY(),sceneHolder.vbom,sceneHolder.resourcesManager.healthLine, sceneHolder.resourcesManager.manaLine, enemy.getWidth(), enemy.maxHealth, enemy.maxMana));
		                sceneHolder.attachChild(sceneHolder.enemyStatus.getLast().healthLine);
		                sceneHolder.attachChild(sceneHolder.enemyStatus.getLast().manaLine);
		            }
		            else
		            	if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_OBJECT))
			            {
		            		
		            		ITextureRegion object_region;
		            		
		            		object_region=sceneHolder.resourcesManager.objectsList.get(sceneHolder.objectsCol);
		            		sceneHolder.objectsCol++;
		            		
			                Sprite object = new Sprite(x, y, object_region, sceneHolder.vbom)
			                {
 
			                };
			                if(value.equals("edge.png"))
			                	object.setScaleY(25);
			                
			                object.setRotation(Float.parseFloat(rotation));
			                //object.setScale(1.65f,1.8f);
			                FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		                	FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		                	Body objectBody;
		                	objectBody = PhysicsFactory.createBoxBody(sceneHolder.physicsWorld, object, BodyType.StaticBody, FIXTURE_DEF);
		                	objectBody.setUserData("object");
		                	sceneHolder.physicsWorld.registerPhysicsConnector(new PhysicsConnector(object, objectBody, true, false));
		                	
		                	
		                	
			                levelObject = object;
			            }
	            
		            	else
	            {
	                throw new IllegalArgumentException();
	            }

	            levelObject.setCullingEnabled(true);

	            return levelObject;
	        }
	    });

	    
	    try {
			levelLoader.loadLevelFromAsset(sceneHolder.activity.getAssets(), "level/" + sceneHolder.levelName + ".lvl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}
