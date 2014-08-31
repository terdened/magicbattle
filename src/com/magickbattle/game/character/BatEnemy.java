package com.magickbattle.game.character;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.magickbattle.engine.ResourcesManager;
import com.magickbattle.game.GameScene;
import com.magickbattle.game.ai.BatAI;
import com.magickbattle.game.level.LevelObject;
import com.magickbattle.game.magick.Bulet;
import com.magickbattle.game.magick.Effect;
import com.magickbattle.game.magick.PlayerMagic;

public class BatEnemy extends Enemy{
	

	public BatEnemy(GameScene scene, float pX, float pY,
			VertexBufferObjectManager vbo, Camera camera,
			PhysicsWorld physicsWorld, ResourcesManager pResourcesManager) {
		super(scene, pX, pY, vbo, camera, physicsWorld, pResourcesManager.bat_region);

		playerMagic = new PlayerMagic();
		

		playerMagic.element="water";
		
		playerMagic.bufCost=30;
		playerMagic.bufPower=0.3f;
		playerMagic.bufTime=100;
		playerMagic.bufType="health";
		
		playerMagic.debufCost=30;
		playerMagic.debufPower=-2;
		playerMagic.debufTime=300;
		playerMagic.debufType="speed";
		
		playerMagic.buletDamage=20;
		playerMagic.buletCost=30;

		playerMagic.elementPower=1;	
		playerMagic.elementCost=400;	
		
		health=100;
	    maxHealth=100;
	    maxMana=100;
	    manaPower=100;
	    manaSpeed=0.1f;
	    
	    setName("eye");
	    
	    setElement("water");
        createShadow(mScene.vbom, mScene.resourcesManager.dark_shadow);
        mAI = new BatAI(mScene, this);
	    stayAnimation();
	}

	@Override
	public void onDie() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBuf(float power, long time, String type) {

    	ITextureRegion effect_Texture = mScene.resourcesManager.speedMinusIcon;
    	
    	ITiledTextureRegion tempEffectAnimation=mScene.resourcesManager.speedDownBuf;
    	
    	if((type.equals("speed"))&&(power<0))
    	{
    		tempEffectAnimation=mScene.resourcesManager.speedDownBuf;
    	}
    	else
    	if((type.equals("speed"))&&(power>=0))
    	{
    		tempEffectAnimation=mScene.resourcesManager.speedUpBuf;
    	}
    	else
    	if((type.equals("health"))&&(power<0))
    	{
    		tempEffectAnimation=mScene.resourcesManager.healthDownBuf;
    	}
    	else
    	if((type.equals("health"))&&(power>=0))
    	{
    		tempEffectAnimation=mScene.resourcesManager.healthUpBuf;
    	}
    	else
    	if((type.equals("damage"))&&(power<0))
    	{
    		tempEffectAnimation=mScene.resourcesManager.speedDownBuf;
    	}
    	else
    	if((type.equals("damage"))&&(power>=0))
    	{
    		tempEffectAnimation=mScene.resourcesManager.speedDownBuf;
    	}
    	
    	if(effects.size()==0)
   			 effects.add(new Effect(10,  140,  power,  time, effect_Texture, mScene.vbom,tempEffectAnimation, type));
   		 else
   			 effects.add(new Effect(effects.getLast().getX()+40,  140,  power,  time, effect_Texture, mScene.vbom,tempEffectAnimation, type));
    	
    	if((type.equals("speed"))&&(power<0))
    	{
    		this.addBufAnimation(effects.getLast());
    		effect_Texture = mScene.resourcesManager.speedMinusIcon;
    	}
    	if((type.equals("speed"))&&(power>=0))
    	{
    		this.addBufAnimation(effects.getLast());
    		effect_Texture = mScene.resourcesManager.speedPlusIcon;
    	}
    	
    	if((type.equals("health"))&&(power<0))
    	{
    		this.addBufAnimation(effects.getLast());
    		effect_Texture = mScene.resourcesManager.healthMinusIcon;
    	}
    	if((type.equals("health"))&&(power>=0))
    	{
    		this.addBufAnimation(effects.getLast());
    		effect_Texture = mScene.resourcesManager.healthPlusIcon;
    	}
    	if((type.equals("damage"))&&(power<0))
    	{
    		effect_Texture = mScene.resourcesManager.healthMinusIcon;
    	}
    	if((type.equals("damage"))&&(power>=0))
    	{
    		effect_Texture = mScene.resourcesManager.healthPlusIcon;
    	}
  		 
    	mScene.gameHUD.attachChild(effects.getLast().effectTexture);
		
	}
	
	@Override
	protected void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{        
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.maskBits=0x0002;
		body = PhysicsFactory.createCircleBody(physicsWorld, this, BodyType.DynamicBody, fixDef);
		
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
	
	
	
	public void goWhileWaiting()
	{
		float newX = this.playerX+(float)Math.random()*200-100;
		float newY = this.playerY+(float)Math.random()*200-100;
		
		if(newX<0)
			newX=0;
		
		if(newX>this.mScene.mLevel.getWidth())
			newX=this.mScene.mLevel.getWidth();
		
		if(newY<0)
			newY=0;
		
		if(newY>this.mScene.mLevel.getHeight())
			newY=this.mScene.mLevel.getHeight();
		
		this.setDest(newX, newY);
	}
	
	public void goToPlayer(float pDistance)
	{
		Vector2 distance = new Vector2();
		
		distance.x = getX()- mScene.player.getX();
		distance.y = getY()- mScene.player.getY();
		
		if(distance.len()>100)
		{
			float tan=Math.abs((this.getX()-mScene.player.playerX)/(this.getY()-mScene.player.playerY));
			float alpha=(float)Math.atan(tan);
			alpha+=Math.toRadians(90);
			
			int xSign=1;
			int ySign=1;
			
			if(this.getX()<mScene.player.playerX)
				xSign=-1;
			
			if(this.getY()>mScene.player.playerY)
				ySign=-1;
			
		 	float distX=(float)(this.getX()+this.getWidth()/2+xSign*pDistance*Math.cos(alpha));
		 	float distY=(float)(this.getY()+this.getHeight()/2+ySign*pDistance*Math.sin(alpha));
	
		 	this.setDest(distX, distY);
		}
	}
	
	public void attackPlayer()
	{
		 mScene.player.attacked(this.getDamage());
		 attacked(-this.getDamage()/2);
	}
}
