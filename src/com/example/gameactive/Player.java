package com.example.gameactive;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.gameactive.ResourcesManager;

public abstract class Player extends AnimatedSprite
{
    // ---------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------
    
	public Boolean touchPlayerShadow=false;
	public Boolean isAttackted=false;
	
	public int timeOfDying=50;
	
	float lastX=-100;
	float lastY=-100;
	
    public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld,  ITiledTextureRegion player_region)
    {
    	super(pX, pY, player_region, vbo);

    		
        createPhysics(camera, physicsWorld);
        camera.setChaseEntity(this);
        destinationX=-100;
        destinationY=-100;
        health=100;
        maxHealth=100;
        manaPower=1000;
        speedX=0;
   	 	speedY=0;
   	 	manaSpeed=0.5f;
   	 	maxMana=1000;
   	 	playerSpeed=5;
   	 	playerX=pX;
   	 	playerY=pY; 
    }
    
    public AnimatedSprite shadow;
    public void createShadow(VertexBufferObjectManager vbo,  ITiledTextureRegion shadow_region)
    {
    	shadow=new AnimatedSprite(0,0,shadow_region,vbo)
    	{
    		@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
            {
                if (pSceneTouchEvent.isActionDown())
                {
                    touchPlayerShadow=true;
                }
                return true;
            };
    	};
    	shadow.setSize(this.getWidth()+30, this.getHeight()+30);
    	shadow.setX(shadow.getX()-15);
    	shadow.setY(shadow.getY()-15);    	
    	final long[] SHADOW_ANIMATE = new long[] { 300, 0, 0, 0, 0, 0, 0, 0 };
        shadow.animate(SHADOW_ANIMATE, 0, 7, true);
    	shadow.setZIndex(this.getZIndex()-1);
    	this.attachChild(shadow);    	
    }
    
	 // ---------------------------------------------
	 // VARIABLES
	 // ---------------------------------------------
	     
	 private Body body;

	 protected boolean isGo=false;
	 
	 public float destinationX;
	 public float destinationY;
	 
	 public float speedX;
	 public float speedY;
	 
	 public float playerSpeed;
	 public float playerX;
	 public float playerY;
	 public float health;
	 public float manaPower;
	 public float manaSpeed;
	 public float maxMana;
	 public float maxHealth;
	 
	 public PlayerMagic playerMagic;
	 
	 public PlayerMagic oppositeMagic;
	 
	 public float damage=35;
	 
	 public LinkedList<Effect> effects;
	 public LinkedList<TextInformHolder> tempText=new LinkedList<TextInformHolder>();
	 public Boolean isDead=false;

	 //---------------------------------------------
	 //Methods
	 //----------------------------------------------
	 
	 private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	 {        
	     body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
	     this.effects = new LinkedList<Effect>();
	     body.setUserData("player");
	     body.setFixedRotation(true);  
	     physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
	     {
	         @Override
	         public void onUpdate(float pSecondsElapsed)
	         {
	             super.onUpdate(pSecondsElapsed);
	             camera.onUpdate(0.1f);                   
	         }
	     });
	    
	 }
	 
	 public float getMana(float quantity)
	 {
		 float result;
		 if(quantity>manaPower)
		 {
			 result=manaPower/quantity;
			 if(result>0.3)
			 manaPower=0;
			 
			 return result;
			 
		 }
		 else
		 {			 
			 manaPower-=quantity;
			 return 1;
		 }
	 }
	 
	 public void fillMana()
	 {
		 manaPower+=manaSpeed;
		 if(manaPower>maxMana)
			 manaPower=maxMana;
	 }
	 
	 public abstract void onDie();
	 public void rotate(float angle)
	 {
		 this.setRotation(angle);
	 }
	  
	 public void move()
	 {
		 
		 
		 if(!isDead)
		 { 
		 
		 if((speedX==0) && (speedY==0))
		 {
			 body.setLinearVelocity(new Vector2(0, 0));
			 stayAnimation();
		 }
		 else
		 {
			 if((destinationX-playerX)!=0)
			 {
				 if(destinationX-playerX>0)
				 {
					 speedX=(float)Math.cos(Math.atan((destinationY-playerY)/(destinationX-playerX))+3.14);
					 speedY=(float)Math.sin(Math.atan((destinationY-playerY)/(destinationX-playerX))+3.14);
				 }else
				 {
					 speedX=(float)Math.cos(Math.atan((destinationY-playerY)/(destinationX-playerX)));
					 speedY=(float)Math.sin(Math.atan((destinationY-playerY)/(destinationX-playerX)));
				 }
				 
				 if(destinationX-playerX>0)	
					rotate((float)(Math.atan((destinationY-playerY)/(destinationX-playerX))*180/3.14)+90);
				 else
					rotate((float)(Math.atan((destinationY-playerY)/(destinationX-playerX))*180/3.14)-90);
			 }			 			 

			 body.setLinearVelocity(new Vector2(-speedX*getSpeed(), -speedY*getSpeed()));
			 
			 playerX=this.getX()+64;
			 playerY=this.getY()+42;
			 
			 
			 if((Math.abs(playerX-destinationX)<playerSpeed)&&(Math.abs(playerY-destinationY)<playerSpeed))
			 {
				 speedX=0;
				 speedY=0;
				 body.setLinearVelocity(new Vector2(0, 0));
				 rotate(0);

				 
			 }
			 goAnimation();
		 }
		 
		 if((speedX!=0)||(speedY!=0))
		 {
			 if((Math.abs(playerX-lastX-64)<0.5)&&(Math.abs(playerY-lastY-42)<0.5))
			 {
				 speedX=0;
				 speedY=0;
				 body.setLinearVelocity(new Vector2(0, 0));
				 rotate(0);
				 lastX=-100;
				 lastY=-100;
			 }
			 else
			 {
				lastX=this.getX();
			 	lastY=this.getY();
			 }
		 }
		 }
	 }	 
		 
	public float getSpeed()
	{
		if(!isDead)
		{
		float result=playerSpeed;
		
		for(int i=0; i<effects.size();i++)
		{
			if(effects.get(i).type.equals("speed"))
				result+=effects.get(i).power;
		}
		
		if(result<0)
			result=0;
			return result;
		}
		else
			return 0;
	}
	 
	 public void setDist(float xDist, float yDist)
	 {
		 
		 if(!isDead)
		 { 
		 
		 destinationX=xDist;
		 destinationY=yDist;
		 
		 if((xDist-playerX)!=0)
		 {
			 if(xDist-playerX>0)
			 {
				 speedX=(float)Math.cos(Math.atan((yDist-playerY)/(xDist-playerX))+3.14);
				 speedY=(float)Math.sin(Math.atan((yDist-playerY)/(xDist-playerX))+3.14);
			 }else
			 {
				 speedX=(float)Math.cos(Math.atan((yDist-playerY)/(xDist-playerX)));
				 speedY=(float)Math.sin(Math.atan((yDist-playerY)/(xDist-playerX)));
			 }
			 
			 if(xDist-playerX>0)	
				rotate((float)(Math.atan((yDist-playerY)/(xDist-playerX))*180/3.14)+90);
			 else
				rotate((float)(Math.atan((yDist-playerY)/(xDist-playerX))*180/3.14)-90);
		 }
		 } 
	 }
	 
	 public void goAnimation()
	 {
		 if(!isGo)
		 {
			 isGo=true;
			 final long[] PLAYER_ANIMATE = new long[] { 0, 200, 200, 200, 200, 200, 200, 0 };
		     animate(PLAYER_ANIMATE, 0, 7, true);
		 }
	 }
	 
	 public void dieAnimation()
	 {
			 isGo=false;
			 final long[] PLAYER_ANIMATE = new long[] { 0, 0, 0, 0, 0, 0, 0, 200 };
		     animate(PLAYER_ANIMATE, 0, 7, true);
	 }
	 
	 public void stayAnimation()
	 {
		 isGo=false;
		 final long[] PLAYER_ANIMATE = new long[] { 300, 0, 0, 0, 0, 0, 0, 0 };
	     animate(PLAYER_ANIMATE, 0, 7, true);
	 }
	 
	 public abstract void setBuf(float power, long time, String type);
	 
	 public void addBufAnimation(AnimatedSprite effect)
	 {
		 effect.setSize(this.getWidth()+30, this.getWidth()+30);
		 effect.setX(effect.getX()-15);
		 effect.setY(effect.getY()-15-this.getWidth()/4); 
		 final long[] SHADOW_ANIMATE = new long[] { 50, 50, 50, 50, 50, 50, 50, 50 };
		 effect.animate(SHADOW_ANIMATE, 0, 7, true);
		 //effect.setZIndex(this.getZIndex()-1);
		 this.attachChild(effect);  
	 }
	 
	 public void updateEffects(final HUD gameHUD)
	 {
		 if(!this.isDead)
		 { 
		 for(int i=0; i<effects.size();i++)
		 {
			 effects.get(i).update();
			 if(effects.get(i).time<0)
			 {
				
				 gameHUD.detachChild(effects.get(i).effectTexture);
				 
				 this.detachChild(effects.get(i).effectAnimation);
				 effects.remove(i);				 
				 
				 for(int j=0;j<effects.size();j++)
				 {
					 gameHUD.detachChild(effects.get(j).effectTexture);
					 effects.get(j).x=10+j*40;
					 gameHUD.attachChild(effects.get(j).effectTexture);
				 }
			 }else
			 if(effects.get(i).type.equals("health"))
			 {
				 health+=effects.get(i).power;
				 if(health>maxHealth)
				 {
					 health=maxHealth;
				 }
				 if(health<=0)
					 isDead=true;
					 
			 }
		 }
		 }
	 }
	 
	 public void attacked(float damage)
	 {
		 health-=damage;
		 TextInformHolder temp = new TextInformHolder(this.getX(),this.getY(),100,"-"+String.valueOf(damage));
		 tempText.add(temp);
		 
		 
		 if(health<=0)
		 {
			 body.setLinearVelocity(0,0);
			 this.speedX=0;
			 this.speedY=0;
			 destinationX=-100;
		     destinationY=-100;
			 this.dieAnimation();
			 isDead=true;
		 }
		 
		 
	 }
	 
	 public float getDamage()
	 {
		 float result=this.playerMagic.buletDamage;
		 
		 for(int i=0; i<effects.size();i++)
		 {
			 if(effects.get(i).type.equals("damage"))
			 {
				 result+=effects.get(i).power;
			 }
		 }
		 
		 return result;
	 }
	 
	 public Boolean getIsDead()
	 {
		 if(isDead)
		 {
			 if(this.timeOfDying==0)
				 return true;
			 else
			 {
				 timeOfDying--;
				 return false;
			 }
		 }else
			 return false;
		 
		 
	 }
	 
}
