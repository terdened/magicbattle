package com.magickbattle.game.menu;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.magickbattle.game.GameScene;


public class PauseMenu extends MenuScene implements IOnMenuItemClickListener{

	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	private final VertexBufferObjectManager mVbom;
	private final GameScene mScene;
	
	//---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
	
	public PauseMenu(Camera pCamera, VertexBufferObjectManager pVertexBufferObject, final GameScene pScene) {
		mScene=pScene;
		mVbom=pVertexBufferObject;
		mCamera=pCamera;
		this.setPosition(250,400);
	}
	
	//---------------------------------------------
    // OVERLOADED METHODS
    //---------------------------------------------
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		switch(pMenuItem.getID())
        {
	        case 0:
	        	resume();
	            return true;
	        case 1:
	        	toMainMenu();
	            return true;
	        case 2:
	        	mScene.restart();
	            return true;
	        case 3:
	        	mScene.nextLevel();
	            return true;
	        default:
	            return false;
        }
	}
	
	//---------------------------------------------
    // PRIVATE METHODS
    //---------------------------------------------
	
	private void resume()
	{
		mScene.resumeGame();
   	}
	
	private void toMainMenu()
	{
		mScene.toMainMenu();
   	}
	
	//---------------------------------------------
    // PUBLIC METHODS
    //---------------------------------------------
	
	public void createMenuChildScene(Camera pCamera)
   	{
   	    Sprite background = new Sprite(0,0,mScene.resourcesManager.pauseMenuBackground,mVbom);
   	    this.attachChild(background);
   	    
   	    final IMenuItem resume = new ScaleMenuItemDecorator(new SpriteMenuItem(
   	    		0, mScene.resourcesManager.pauseMenuResumeButton, mVbom), 0.8f, 1);
   	    this.addMenuItem(resume);
	    
   	    final IMenuItem mainMenu = new ScaleMenuItemDecorator(new SpriteMenuItem(
   	    		1, mScene.resourcesManager.pauseMenuGoToMenuButton, mVbom), 0.8f, 1);
	    this.addMenuItem(mainMenu);
	    
	    final IMenuItem restart = new ScaleMenuItemDecorator(new SpriteMenuItem(
   	    		2, mScene.resourcesManager.pauseMenuRestartButton, mVbom), 0.8f, 1);
	    this.addMenuItem(restart);
	    
	    final IMenuItem nextLevel = new ScaleMenuItemDecorator(new SpriteMenuItem(
   	    		3, mScene.resourcesManager.pauseMenuNextLevelButton, mVbom), 0.8f, 1);
	    this.addMenuItem(nextLevel);
   	    
   	    this.buildAnimations();
   	    this.setBackgroundEnabled(false);
   	    
   	    resume.setPosition(120, 20);
   	    restart.setPosition(120, 20);
   	    nextLevel.setPosition(120, 20);
   	    mainMenu.setPosition(20, 20);
   	 
   	    this.setOnMenuItemClickListener(this);
   	}
	
	public void updateMenuChildScene()
   	{
		if(mScene.mGameState=="game")
		{
			getChildByIndex(0).setPosition(0, -2000);
			getChildByIndex(1).setPosition(0, -2000);
			getChildByIndex(2).setPosition(0, -2000);
			getChildByIndex(3).setPosition(0, -2000);
			getChildByIndex(4).setPosition(0, -2000);
			
		}else
		if(mScene.mGameState=="pause")
		{
			getChildByIndex(0).setPosition(0, 0);
			getChildByIndex(1).setPosition(120, 20);
			getChildByIndex(2).setPosition(20, 20);
			getChildByIndex(3).setPosition(0, -2000);
			getChildByIndex(4).setPosition(0, -2000);
		}
		if(mScene.mGameState=="die")
		{
			getChildByIndex(0).setPosition(0, 0);
			getChildByIndex(1).setPosition(0, -2000);
			getChildByIndex(2).setPosition(20, 20);
			getChildByIndex(3).setPosition(120, 20);
			getChildByIndex(4).setPosition(0, -2000);
		}
		if(mScene.mGameState=="win")
		{
			getChildByIndex(0).setPosition(0, 0);
			getChildByIndex(1).setPosition(0, -2000);
			getChildByIndex(2).setPosition(20, 20);
			getChildByIndex(3).setPosition(0, -2000);
			getChildByIndex(4).setPosition(120, 20);
		}
   	}

}
