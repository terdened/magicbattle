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
import com.magickbattle.level.Level;
import com.magickbattle.level.StoneLevelObject;
import com.magickbattle.level.WholeLevelObject;

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
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_STONE = "stone";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_OBJECT = "object";
	
	public GameSceneLoader(final GameScene scene)
	{
		sceneHolder=scene;
	}
	
	public void loadLevel(int levelID)
	{
	    final LevelLoader levelLoader = new LevelLoader();
	    
	    levelLoader.registerEntityLoader(LevelConstants.TAG_LEVEL,new IEntityLoader()
	    {
	        public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) 
	        {
	            final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
	            final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
	            
	            Level level = new Level(width, height, sceneHolder);
	            Sprite background = new Sprite(0,0,
	            		sceneHolder.resourcesManager.gamebkg_region,sceneHolder.vbom);
	            level.attachBackground(background);
	            sceneHolder.attachLevelHolder(level);

	            return sceneHolder;
	        }
	    });
	    
	    levelLoader.registerEntityLoader("object",new IEntityLoader()
	    {
	        public IEntity onLoadEntity(final String pEntityName, final Attributes pAttributes) 
	        {
	        	final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
	            final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
	            final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
	            final String value = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_VALUE);
	            
	            
	            if(type.equals("stone"))
	            {
	            	StoneLevelObject object = new StoneLevelObject(x, y,
	            			sceneHolder.resourcesManager.stone_region, sceneHolder.vbom);
	            	
	            	sceneHolder.mLevel.attachObject(object);
	            	
	                FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
                	FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
                	Body objectBody;
                	objectBody = PhysicsFactory.createBoxBody(sceneHolder.physicsWorld, object, 
                			BodyType.StaticBody, FIXTURE_DEF);
                	objectBody.setUserData("object");
                	sceneHolder.physicsWorld.registerPhysicsConnector(new PhysicsConnector(object, 
                			objectBody, true, false));
	            }
	            else
            	if(type.equals("whole"))
	            {
	            	WholeLevelObject object = new WholeLevelObject(x, y, 
	            			sceneHolder.resourcesManager.whole_region, sceneHolder.vbom);
	            	
	            	object.InitWhole(value);
	            	sceneHolder.mLevel.attachObject(object);
	            	
	                FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
                	FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
                	Body objectBody;
                	objectBody = PhysicsFactory.createBoxBody(sceneHolder.physicsWorld, object, 
                			BodyType.StaticBody, FIXTURE_DEF);
                	objectBody.setUserData("object");
                	sceneHolder.physicsWorld.registerPhysicsConnector(new PhysicsConnector(object, 
                			objectBody, true, false));
	            }

	            return null;
	        }
	    });
	       
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

	            	sceneHolder.player = new Player(x*80, y*80, sceneHolder.vbom, sceneHolder.camera, sceneHolder.physicsWorld, player_region)
	                {
	                    @Override
	                    public void onDie()
	                    {
	                    	                  	
	                    }
	                    
	                    @Override
	                    public void setBuf(float power, long time, String type)
	               	 	{
	                    	ITextureRegion effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
	                    	ITiledTextureRegion tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	
	                    	if((type.equals("speed"))&&(power<0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	}
	                    	else
	                    	if((type.equals("speed"))&&(power>=0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedUpBuf;
	                    	}
	                    	else
	                    	if((type.equals("health"))&&(power<0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.healthDownBuf;
	                    	}
	                    	else
	                    	if((type.equals("health"))&&(power>=0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.healthUpBuf;
	                    	}
	                    	else
	                    	if((type.equals("damage"))&&(power<0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	}
	                    	else
	                    	if((type.equals("damage"))&&(power>=0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	}
	                    	
	                    	if(effects.size()==0)
		               			 effects.add(new Effect(10,  140,  power,  time, 
		               					 effect_Texture, sceneHolder.vbom,tempEffectAnimation, type));
		               		 else
		               			 effects.add(new Effect(effects.getLast().getX()+40,  140,  power,  
		               					 time, effect_Texture, sceneHolder.vbom,tempEffectAnimation, type));
	                    	
	                    	if((type.equals("speed"))&&(power<0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
	                    		effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
	                    	}
	                    	if((type.equals("speed"))&&(power>=0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
	                    		effect_Texture = sceneHolder.resourcesManager.speedPlusIcon;
	                    	}
	                    	
	                    	if((type.equals("health"))&&(power<0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
	                    		effect_Texture = sceneHolder.resourcesManager.healthMinusIcon;
	                    	}
	                    	if((type.equals("health"))&&(power>=0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
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
	                        		this.setBuf(this.playerMagic.bufPower, 
	                        				this.playerMagic.bufTime, this.playerMagic.bufType);
	                        	}
	                        	sceneHolder.freePlayer=true;
	                        	sceneHolder.touchPlayer=false;
	                        }
	                        return true;
	                    };
	                    
	                };
	                sceneHolder.player.createShadow(sceneHolder.vbom, 
	                		sceneHolder.resourcesManager.dark_shadow);
	                levelObject = sceneHolder.player;
	                sceneHolder.playerStatus=new PlayerStatus(sceneHolder.player.getWidth(),
	                		sceneHolder, sceneHolder.player);
	                sceneHolder.player.stayAnimation();
	                sceneHolder.attachChild(sceneHolder.playerStatus);
	            }
	            else
            	if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ENEMY))
	            {
	            	ITiledTextureRegion player_region=sceneHolder.resourcesManager.enemys_regions[0];
	            	
		            final String element = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_ELEMENT);
		            final String name = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_NAME);	
	            	
	                Enemy enemy = new Enemy(sceneHolder,x*80, y*80, sceneHolder.vbom, 
	                		sceneHolder.camera, sceneHolder.physicsWorld, player_region )
	                {
	                    @Override
	                    public void onDie()
	                    {
	                    	                  	
	                    }
	                    
	                    @Override
	                    public void setBuf(float power, long time, String type)
	               	 	{
	                    	ITextureRegion effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
	                    	
	                    	ITiledTextureRegion tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	
	                    	if((type.equals("speed"))&&(power<0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	}
	                    	else
	                    	if((type.equals("speed"))&&(power>=0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedUpBuf;
	                    	}
	                    	else
	                    	if((type.equals("health"))&&(power<0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.healthDownBuf;
	                    	}
	                    	else
	                    	if((type.equals("health"))&&(power>=0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.healthUpBuf;
	                    	}
	                    	else
	                    	if((type.equals("damage"))&&(power<0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	}
	                    	else
	                    	if((type.equals("damage"))&&(power>=0))
	                    	{
	                    		tempEffectAnimation=sceneHolder.resourcesManager.speedDownBuf;
	                    	}
	                    	
	                    	if(effects.size()==0)
		               			 effects.add(new Effect(10,  140,  power,  time, effect_Texture, sceneHolder.vbom,tempEffectAnimation, type));
		               		 else
		               			 effects.add(new Effect(effects.getLast().getX()+40,  140,  power,  time, effect_Texture, sceneHolder.vbom,tempEffectAnimation, type));
	                    	
	                    	if((type.equals("speed"))&&(power<0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
	                    		effect_Texture = sceneHolder.resourcesManager.speedMinusIcon;
	                    	}
	                    	if((type.equals("speed"))&&(power>=0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
	                    		effect_Texture = sceneHolder.resourcesManager.speedPlusIcon;
	                    	}
	                    	
	                    	if((type.equals("health"))&&(power<0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
	                    		effect_Texture = sceneHolder.resourcesManager.healthMinusIcon;
	                    	}
	                    	if((type.equals("health"))&&(power>=0))
	                    	{
	                    		this.addBufAnimation(effects.getLast());
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
	                enemy.setName(name);
	                
	                try
	        	    {
	        		    SAXParserFactory factory = SAXParserFactory.newInstance();
	        			SAXParser saxParser = factory.newSAXParser();
	        			
	        		    MagicParser tempPlayerMagic=new MagicParser();

	        		    InputStream curDir = sceneHolder.activity.getAssets().open("level/"+name+"Magic.lvl");
	        		    saxParser.parse(curDir, tempPlayerMagic);
	        		    enemy.playerMagic=tempPlayerMagic.getResult();
	        		    
	        		    
	        		    if(!tempPlayerMagic.getTempHP().equals("none"))
	        		    {
		        		    enemy.health=Float.parseFloat(tempPlayerMagic.getTempHP());
		        		    enemy.maxHealth=Float.parseFloat(tempPlayerMagic.getTempHP());
		        		    enemy.maxMana=Float.parseFloat(tempPlayerMagic.getTempMP());
		        		    enemy.manaPower=Float.parseFloat(tempPlayerMagic.getTempMP());
		        		    enemy.manaSpeed=Float.parseFloat(tempPlayerMagic.getTempMPS());
	        		    }
	        	    }
	        	    catch (Exception e) 
	        	    {
	        	        e.printStackTrace();
	        	    }
	      
	                enemy.setElement(enemy.playerMagic.element);
	                enemy.createShadow(sceneHolder.vbom, sceneHolder.resourcesManager.dark_shadow);
	                enemy.initAI();
	                

	        	    try
	        	    {
	        		    SAXParserFactory factory = SAXParserFactory.newInstance();
	        			SAXParser saxParser = factory.newSAXParser();
	        			
	        		    AiParser tempAI=new AiParser();
	        		    tempAI.init(sceneHolder);
	        		    InputStream curDir = sceneHolder.activity.getAssets().open("level/"+name+"AI.lvl");
		        		    saxParser.parse(curDir, tempAI);
		        		    enemy.setAi(tempAI.getResult());
		        	    }
		        	    catch (Exception e) 
		        	    {
		        	        e.printStackTrace();
		        	    }
		        	    
		        	    enemy.stayAnimation();
		        	    sceneHolder.enemyList.add(enemy);
		                levelObject = enemy;
		                sceneHolder.enemyStatus.add(new PlayerStatus(enemy.getWidth(), sceneHolder,
		                		sceneHolder.enemyList.getLast()));
		                sceneHolder.attachChild(sceneHolder.enemyStatus.getLast());
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
			e.printStackTrace();
		}
	    
	}
}
