package com.magickbattle.game.charater;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.magickbattle.engine.ResourcesManager;
import com.magickbattle.game.GameScene;
import com.magickbattle.game.gui.TextInformHolder;
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
        initAI();   
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
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
    {
    	if (pSceneTouchEvent.isActionDown())
        {
    		mScene.touchEnemy=true;
        }
    	
        if (pSceneTouchEvent.isActionUp())
        {
        	if(mScene.touchEnemy)
        	{
        		this.setBuf(this.oppositeMagic.debufPower, this.oppositeMagic.debufTime, this.oppositeMagic.debufType);
        	}
        	mScene.freeEnemy=true;
        	mScene.touchEnemy=false;
        }
        return true;
    }
}
