package com.example.gameactive;

import java.util.LinkedList;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;

public class Buf extends Bulet 
{

	public float power;
	public long time;
	public String type;
	
	
	public Buf(float power, long time, String type)
	{
		this.power=power;
		this.time=time;
		this.type=type;
		isInit=false;
		isRemove=false;
	}
	
	public void loadBulet(ITextureRegion player_bulet_region, VertexBufferObjectManager vbom, final LinkedList<Wall> walls, final Player player, final Enemy enemy)
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
	                	if(walls.get(i).element!="wind")
	                	{
	                		isRemove=true;
	                	}else
	                	{
	                		Vector2 vecA= new Vector2(speedX,speedY);
	                		Vector2 vecB= new Vector2((float)Math.cos((double)walls.get(i).wallTexture.getRotation()),(float)Math.sin((double)walls.get(i).wallTexture.getRotation()));
	                		Vector2 vecC=vecA.add(vecB);
	                		speedX=vecC.x;
	                		speedY=vecC.y;
	                	}
	                }
	                
	                
                }
                if (player.collidesWith(this))
                {
                	player.setBuf(power, time, type);
                	isRemove=true;
                }
                if (enemy.collidesWith(this))
                {
                	enemy.setBuf(power, time, type);
                	isRemove=true;
                }
            }
        };
    }
}
