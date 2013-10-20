package com.example.gameactive;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class HellCircle 
{
	public Entity scene;
	public Sprite background;
	public String selectedLevel="none";
	
	
	public HellCircle(ITextureRegion region, VertexBufferObjectManager vbom)
	{
		scene=new Entity();
		background=new Sprite(0,0,region,vbom);
		background.setScale(1.69f,1.6f);
		background.setPosition(155, 240);
		scene.attachChild(background);
	}
	
	public void addButton(float x, float y, ITextureRegion region, VertexBufferObjectManager vbom, String level, final Scene scene)
	{
		Sprite Button=new LevelHolder(x,y,region,vbom,level)
		{
			
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
            {
                if (pSceneTouchEvent.isActionDown())
                {

                }
                if (pSceneTouchEvent.isActionUp())
                {
                	selectedLevel=level;
                }
                return true;
            };
		};
		this.scene.attachChild(Button);
		scene.registerTouchArea(Button);
	}
}
