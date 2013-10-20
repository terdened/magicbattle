package com.example.gameactive;

import java.util.LinkedList;

public class ReceptorDamage extends Receptor
{

	public ReceptorDamage(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


	private Boolean isDanger(float x, float y, Bulet bulet, LinkedList<Wall> walls)
	{
	
		
		Boolean result=Intersection(bulet.finalX, bulet.finalY, bulet.finalX-bulet.speedX*1000, 
									bulet.finalY-bulet.speedY*1000, x, y, x+128, y+64);
		
		if(!result)
			result=Intersection(bulet.finalX, bulet.finalY, bulet.finalX-bulet.speedX*1000, 
								bulet.finalY-bulet.speedY*1000, x, y+64, x+128, y);

		if(result)
		{
			for(int i=0;i<walls.size();i++)
			{
				Boolean temp=Intersection(bulet.finalX, bulet.finalY, x, y,
				walls.get(i).x, walls.get(i).y+64, walls.get(i).x+128, walls.get(i).y);

				if(!temp)
					temp=Intersection(bulet.finalX, bulet.finalY, x, y,
							walls.get(i).x, walls.get(i).y, walls.get(i).x+128, walls.get(i).y+64);
				
				if(temp)
				{
					result=false;
					break;
				}
			}
		}
		return result;
	}
	
	
	@Override
	public int getState(GameState gameState, Memory memory)
	{
		Boolean isDanger=false;
		Boolean canEscape=true;
		
		
		for(int i=0;i<gameState.bulet.size();i++)
		{
			if(gameState.bulet.get(i).element.equals(gameState.playerMagic.element))
			{
				if(isDanger(gameState.enemy.getX(),gameState.enemy.getY(),gameState.bulet.get(i),gameState.walls))
				{
					isDanger=true;
					canEscape=canEscape(gameState.bulet.get(i),gameState.enemy);
				}
			}
		}
		
		if((gameState.bulet.size()>2)&&(canEscape==false)&&(isDanger))
			state=4;
		else
		if((gameState.bulet.size()<=2)&&(canEscape==false)&&(isDanger))
			state=3;
		else
		if((gameState.bulet.size()<=2)&&(canEscape==true)&&(isDanger))
			state=2;
		else
		if((gameState.bulet.size()==1)&&(canEscape==true)&&(isDanger))
			state=1;
		else
			state=0;
		
		return state;
	}
	
	public Boolean Intersection(float ax1, float ay1, float ax2,float ay2, float bx1, float by1, float bx2, float by2)
	{
		   float v1,v2,v3,v4;
		   v1=(bx2-bx1)*(ay1-by1)-(by2-by1)*(ax1-bx1);
		   v2=(bx2-bx1)*(ay2-by1)-(by2-by1)*(ax2-bx1);
		   v3=(ax2-ax1)*(by1-ay1)-(ay2-ay1)*(bx1-ax1);
		   v4=(ax2-ax1)*(by2-ay1)-(ay2-ay1)*(bx2-ax1);
		   if((v1*v2<0)&&(v3*v4<0))
			   return true;
		   else
			   return false;
	}
	
	public Boolean canEscape(Bulet bulet, Enemy enemy)
	{
		float distance=(float)Math.sqrt(Math.pow(enemy.getX()-bulet.getX(),2)+(float)Math.pow(enemy.getY()-bulet.getY(),2));
		float speedBulet=(float)Math.sqrt(Math.pow(bulet.speedX,2)+(float)Math.pow(bulet.speedY,2));
		float speedEnemy=enemy.playerSpeed;
		
		
		Boolean result=false;
		
		if(distance/speedBulet<=128/speedEnemy)
			result=false;
		else
			result=true;
		
		return result;
	}
	
}
