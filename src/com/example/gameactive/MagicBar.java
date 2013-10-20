package com.example.gameactive;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class MagicBar 
{
	private float X;
	private float Y;
	
	private float iconHeight;

	private float iconDistantion;
	
	private int choosenIcon;
	
	public AnimatedSprite buletIcon;
	public AnimatedSprite wallIcon;
	public AnimatedSprite natureIcon;
	public AnimatedSprite bufIcon;
	public AnimatedSprite debufIcon;
	
	public MagicBar(float X, float Y, float iconHeight, float iconDistantion)
	{
		this.X=X;
		this.Y=Y;
		this.iconHeight=iconHeight;
		this.iconDistantion=iconDistantion;
		choosenIcon=1;
	}
	
	
	public void loadImages(ITiledTextureRegion buletIcon, ITiledTextureRegion wallIcon, ITiledTextureRegion natureIcon,
			ITiledTextureRegion bufIcon, ITiledTextureRegion debufIcon, VertexBufferObjectManager vbom)
	{
		
		this.buletIcon = new AnimatedSprite(X, Y, buletIcon, vbom)
		{
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(1);
	            }
	            return true;
	        };
	    };
		
		this.wallIcon = new AnimatedSprite(X, Y+iconHeight+iconDistantion, wallIcon, vbom)
		{
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(2);
	            }
	            return true;
	        };
	    };
		this.natureIcon = new AnimatedSprite(X, Y+2*(iconHeight+iconDistantion), natureIcon, vbom){
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(3);
	            }
	            return true;
	        };
	    };
		this.bufIcon = new AnimatedSprite(X, Y+3*(iconHeight+iconDistantion), bufIcon, vbom){
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(4);
	            }
	            return true;
	        };
	    };
		this.debufIcon = new AnimatedSprite(X, Y+4*(iconHeight+iconDistantion), debufIcon, vbom){
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	chooseIcon(5);
	            }
	            return true;
	        };
	    };
		
		this.buletIcon.setCurrentTileIndex(1);
		this.wallIcon.setCurrentTileIndex(0);
		this.natureIcon.setCurrentTileIndex(0);
		this.bufIcon.setCurrentTileIndex(0);
		this.debufIcon.setCurrentTileIndex(0);
	}
	
	public void chooseIcon(int index)
	{
		int i1=0;
		int i2=0;
		int i3=0;
		int i4=0;
		int i5=0;
		
		if(index==1)
			i1=1;
		if(index==2)
			i2=1;
		if(index==3)
			i3=1;
		if(index==4)
			i4=1;
		if(index==5)
			i5=1;
		
		this.buletIcon.setCurrentTileIndex(i1);
		this.wallIcon.setCurrentTileIndex(i2);
		this.natureIcon.setCurrentTileIndex(i3);
		this.bufIcon.setCurrentTileIndex(i4);
		this.debufIcon.setCurrentTileIndex(i5);
		
		choosenIcon=index;
		
	}
	
	public int getChoosenIcon()
	{
		return choosenIcon;
	}
	
}
