package com.magickbattle.playermenu;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.magickbattle.engine.BaseScene;
import com.magickbattle.engine.SceneManager;
import com.magickbattle.engine.SceneManager.SceneType;

public class PlayerMenuScene extends BaseScene implements IOnMenuItemClickListener, IOnSceneTouchListener 
{
	//private Sprite bckgr;

	
	private MenuScene menuChildScene;
	private MenuScene mainMenuChildScene;
	private Sprite mBackground;
	
	private final int MENU_FIRE = 0;
	private final int MENU_WATER = 1;
	private final int MENU_EARTH = 2;
	private final int MENU_WIND = 3;
	private final int MENU_BACK = 4;
	private final int MENU_HIDE_ELEMENTS = 5;
	
	Scene chooseElement;
	
	
	public float startTapY=0;
	public float startTapX=0;
	
	
	String selectedLevel="none";
	ScrollableMenu scrollMenu;
	
    @Override
    public void createScene()
    {
    	createBackground();
    	createMainMenuChildScene();
    	setOnSceneTouchListener(this);
        onSceneUpdateEvent(this);
    }
    
    
    public void createChooseElement()
    {
    	chooseElement=new Scene();
    	menuChildScene = new MenuScene(camera);
 	    menuChildScene.setPosition(0, 0);
 	    
 	    final IMenuItem chooseFire = new ScaleMenuItemDecorator(
 	    		new SpriteMenuItem(MENU_FIRE, resourcesManager.fireElementButton, vbom), 0.8f, 1);
 	    final IMenuItem chooseWater = new ScaleMenuItemDecorator(
 	    		new SpriteMenuItem(MENU_WATER, resourcesManager.waterElementButton, vbom), 0.8f, 1);
 	    final IMenuItem chooseEarth = new ScaleMenuItemDecorator(
 	    		new SpriteMenuItem(MENU_EARTH, resourcesManager.earthElementButton, vbom), 0.8f, 1);
 	    final IMenuItem chooseWind = new ScaleMenuItemDecorator(
 	    		new SpriteMenuItem(MENU_WIND, resourcesManager.windElementButton, vbom), 0.8f, 1);
 	    final IMenuItem back = new ScaleMenuItemDecorator(
 	    		new SpriteMenuItem(MENU_HIDE_ELEMENTS, resourcesManager.backButton, vbom), 0.8f, 1);
 	    
 	    menuChildScene.addMenuItem(chooseFire);
 	    menuChildScene.addMenuItem(chooseWater);
 	    menuChildScene.addMenuItem(chooseEarth);
 	    menuChildScene.addMenuItem(chooseWind);
 	    menuChildScene.addMenuItem(back);

 	    menuChildScene.buildAnimations();
 	    menuChildScene.setBackgroundEnabled(false);
 	    
 	    chooseFire.setPosition(0, 0);
 	    chooseWater.setPosition(256, 256);
 	    chooseEarth.setPosition(0, 256);
 	    chooseWind.setPosition(256, 0);
 	    back.setPosition(back.getX(), back.getY() + 90);
 	    
 	    menuChildScene.setOnMenuItemClickListener(this);
 	   scrollMenu.setBlocked(true);
 	   setChildScene(menuChildScene);
 	   //setChildScene(chooseElement);
    }
    
    public void clearScene()
    {
	   this.clearChildScene();
    }
    
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, 
    		float pMenuItemLocalY)
    {
            switch(pMenuItem.getID())
            {
            case MENU_FIRE:
            	SceneManager.getInstance().loadGameScene(engine,"fire",selectedLevel);
                return true;
            case MENU_WATER:
            	SceneManager.getInstance().loadGameScene(engine,"water",selectedLevel);
                return true;
            case MENU_EARTH:
            	SceneManager.getInstance().loadGameScene(engine,"earth",selectedLevel);
                return true;
            case MENU_WIND:
            	SceneManager.getInstance().loadGameScene(engine,"wind",selectedLevel);
                return true;
            case MENU_BACK:
            	SceneManager.getInstance().loadMenuScene(engine);
                return true;
            case MENU_HIDE_ELEMENTS:
            	this.clearChildScene();
            	clearScene();
            	this.detachSelf();
            	selectedLevel="none";
            	scrollMenu.clearSelectedLevel();
            	scrollMenu.setBlocked(false);
            	createMainMenuChildScene();
                return true;
            default:
                return false;
        }
    }
    
    private void createMainMenuChildScene()
	{
    	mainMenuChildScene = new MenuScene(camera);
    	mainMenuChildScene.setPosition(0, 0);
 	    
 	    final IMenuItem back = new ScaleMenuItemDecorator(
 	    		new SpriteMenuItem(MENU_BACK, resourcesManager.backButton, vbom), 1.2f, 1);
 	    

 	   mainMenuChildScene.addMenuItem(back);

 	   mainMenuChildScene.buildAnimations();
 	   mainMenuChildScene.setBackgroundEnabled(false);
 	    

 	    back.setPosition(back.getX(), back.getY() + 580);
 	    
 	   mainMenuChildScene.setOnMenuItemClickListener(this);
 	   setChildScene(mainMenuChildScene);
	}

    private void createBackground()
    {
    	mBackground=new Sprite(0, 0, resourcesManager.menu_background_region, vbom);
    	this.attachChild(mBackground);
    	
    	scrollMenu=new ScrollableMenu();
    	
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	
    	scrollMenu.getCirclesList().getLast().addButton(100, 200, resourcesManager.level_point_region, vbom, "1",this);
    	scrollMenu.getCirclesList().getLast().addButton(415, 300, resourcesManager.level_point_region, vbom, "2",this);
    	scrollMenu.getCirclesList().getLast().addButton(440, 450, resourcesManager.level_point_region, vbom, "3",this);
    	scrollMenu.getCirclesList().getLast().addButton(100, 520, resourcesManager.level_point_region, vbom, "2",this);
    	
    	
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	
    	scrollMenu.getCirclesList().getLast().addButton(100, 150, resourcesManager.level_point_region, vbom, "dorian",this);
    	
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	scrollMenu.addCircle(resourcesManager, vbom);    
    	attachChild(scrollMenu.getCirclesList().getLast().getScene());
    	scrollMenu.getCirclesList().getLast().addButton(50, 150, resourcesManager.level_point_region, vbom, "veider",this);
    }
    
    @Override
    public void onBackKeyPressed()
    {
    	SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneType getSceneType()
    {
    	return SceneType.SCENE_PLAYER_MENU;
    }

    @Override
    public void disposeScene()
    {
    	
        this.detachSelf();
        this.dispose();
    }
    
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
    {
    	if(pSceneTouchEvent.isActionMove())
    	{
    		
    	}
        if (pSceneTouchEvent.isActionDown())
        {
        	startTapX=pSceneTouchEvent.getX();
        	startTapY=pSceneTouchEvent.getY(); 	       	
        }
        
        if (pSceneTouchEvent.isActionUp())
        {
        	if(Math.abs(startTapY-pSceneTouchEvent.getY())>Math.abs(startTapX-pSceneTouchEvent.getX()))
        	{
        		if(startTapY-pSceneTouchEvent.getY()>0)
        		{
        			scrollMenu.moveUp();
        		}else
        			scrollMenu.moveDown();
        	}       	
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
            	scrollMenu.update();
            	
            	if(selectedLevel=="none")
            	{
	            	for(int i=0;i<scrollMenu.getCirclesList().size();i++)
	            	{
	            		if(scrollMenu.getCirclesList().get(i).getSelectedLevel()!="none")
	            		{
	            			createChooseElement();
	            			selectedLevel=scrollMenu.getCirclesList().get(i).getSelectedLevel();
	            		}
	            	}
            	}
            }
    });
		
        return false;
    }
    
}
