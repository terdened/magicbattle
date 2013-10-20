package com.example.gameactive;

import java.util.LinkedList;
import java.util.List;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;

public class Bulet
{
	protected float startX;
	protected float startY;
	protected float finalX;
	protected float finalY;
	
	protected float speedX;
	protected float speedY;
	
	protected float speedK;
	
	protected long startTime;

	
	protected Sprite player_bulet;

	
	protected Boolean isInit;
	protected Boolean isRemove;
	
	public float damage;
	
	public String element;
	 
	public Bulet(){} // No-argument Constructor
	
	Bulet(float damage, String element)
	{
		isInit=false;
		isRemove=false;
		this.damage=damage;
		this.element=element;
	}
	
	public void init(float x, float y, long time)
	{
		startX=x;
		startY=y;
		speedK=(float) 0.1;
		isInit=true;
		startTime=time;
	}
	
	public void loadBulet(ITextureRegion player_bulet_region, VertexBufferObjectManager vbom, final LinkedList<Wall> walls, final Player player, final List<Enemy> enemyList)
    {
		player_bulet = new Sprite(0, 0, player_bulet_region, vbom)
		{
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) 
            {
                super.onManagedUpdate(pSecondsElapsed);
                int l=walls.size();
                for(int i=0; i<l;i++)
                {                
	                if (walls.get(i).wallTexture.collidesWith(this))
	                {
	                	if(!walls.get(i).element.equals("wind"))
	                	{
	                		if((walls.get(i).element.equals("fire"))&&(element.equals("fire")))
	                		{
	                			damage+=0.1f;
		                		
	                		}else
	                		{
	                			walls.get(i).attack(damage);
		                		isRemove=true;
	                		}
	                	}else
	                	{
	                		Vector2 vecA= new Vector2(speedX,speedY);
	                		Vector2 vecB= new Vector2(-(float)Math.cos((double)walls.get(i).wallTexture.getRotation()),-(float)Math.sin((double)walls.get(i).wallTexture.getRotation()));
	                		Vector2 vecC=vecA.add(vecB);
	                		speedX=vecC.x;
	                		speedY=vecC.y;
	                	}
	                }
                }
                if (player.collidesWith(this))
                {
                	player.attacked(damage);
                	isRemove=true;
                }
                
                for(int i=0;i<enemyList.size();i++)
                if (enemyList.get(i).collidesWith(this))
                {
                	enemyList.get(i).attacked(damage);
                	enemyList.get(i).isAttackted=true;
                	isRemove=true;
                }
            }
        };
    }
	
	public void startFly(float x, float y, long time)
	{
		finalX=x;
		finalY=y;
		
		
		speedK/=(time-startTime)*0.01;
		speedX= speedK*(startX-finalX);
		speedY= speedK*(startY-finalY);
		
		player_bulet.setX(finalX);
		player_bulet.setY(finalY);
		
		finalX-=player_bulet.getWidth()/2;
		finalY-=player_bulet.getHeight()/2;
	}
	
	public void lastInit(float x, float y, long time)
	{
		finalX=x;
		finalY=y;
		
	}
	
	public void createShadow(VertexBufferObjectManager vbo,  ITiledTextureRegion shadow_region)
    {
    	AnimatedSprite shadow=new AnimatedSprite(0,0,shadow_region,vbo);
    	shadow.setSize(player_bulet.getWidth()+30, player_bulet.getHeight()+30);
    	shadow.setX(shadow.getX()-15);
    	shadow.setY(shadow.getY()-15);    	
    	final long[] SHADOW_ANIMATE = new long[] { 100, 100, 100, 100, 100, 100, 100, 100 };
        shadow.animate(SHADOW_ANIMATE, 0, 7, true);
    	shadow.setZIndex(player_bulet.getZIndex()-1);
    	player_bulet.attachChild(shadow);    	
    }
	
	public void update(Vector2 wind)
	{
		Vector2 temp=new Vector2(speedX,speedY);
		temp.add(wind);
		if(isInit)
		{
		finalX-=temp.x;
		finalY-=temp.y;
		
		player_bulet.setX(finalX);
		player_bulet.setY(finalY);
		}
	}
	
	public float getX()
	{
		return finalX;
	}
	
	public float getY()
	{
		return finalY;
	}
	
	


}
