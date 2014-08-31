package com.magickbattle.game.character;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.magickbattle.engine.ResourcesManager;
import com.magickbattle.game.GameScene;
import com.magickbattle.game.ai.EyeAI;
import com.magickbattle.game.magick.Bulet;
import com.magickbattle.game.magick.Effect;
import com.magickbattle.game.magick.PlayerMagic;

public class EyeEnemy extends Enemy{
	

	public EyeEnemy(GameScene scene, float pX, float pY,
			VertexBufferObjectManager vbo, Camera camera,
			PhysicsWorld physicsWorld, ResourcesManager pResourcesManager) {
		super(scene, pX, pY, vbo, camera, physicsWorld, pResourcesManager.eye_region);

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
        mAI = new EyeAI(mScene, this);
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
	
	
	
	
	
	public void goWhileWaiting()
	{
		float newX = this.playerX+(float)Math.random()*200-100;
		float newY = this.playerY+(float)Math.random()*200-100;
		
		this.setDest(newX, newY);
	}
	
	public void goToPlayer(float pDistance)
	{
		Vector2 distance = new Vector2();
		
		distance.x = getX()- mScene.player.getX();
		distance.y = getY()- mScene.player.getY();
		
		if(distance.len()>200)
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
	
	public void attackPlayer(float playerX, float playerY)
	{
	 		
	}
	
	public void attackPlayer()
	{
		float tan=Math.abs(this.getX()-mScene.player.playerX)/(mScene.player.playerY-this.getY());
 		float alpha=(float)Math.atan(tan);
 		alpha+=Math.toRadians(90);
 		 
 		int xSign=1;
		int ySign=1;
		
		if(this.getX()<mScene.player.playerX)
			xSign=-1;
		
		if(this.getY()>mScene.player.playerY)
			ySign=-1;
 		 
 		 LinkedList<Bulet> temp=new LinkedList<Bulet>();
 		 
 
 		 temp.add(new Bulet(mScene.resourcesManager.player_bulet_region,getDamage(),
 				 playerMagic.element, mScene));
 	 	 temp.getLast().init(this.getX()+this.getWidth()/2,this.getY()+100+this.getHeight()/2,100);
  
 	 	 
 	 	 float distX=(float)(this.getX()+this.getWidth()/2+xSign*10*Math.cos(alpha));
 	 	 float distY=(float)(this.getY()+this.getHeight()/2+ySign*100+10*Math.sin(alpha));
 	 	 temp.getLast().lastInit(distX,distY,110);
 
 	 	 this.mScene.gameSceneCreator.createBulet(temp, this);
	}
}
