/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.gameactive;
import java.util.LinkedList;

/*
 * Receptor determines security of player
 * @author Denis Terehin
 */
public class ReceptorDamage extends Receptor
{

	public ReceptorDamage(int id) {
		super(id);
	}

	/*
	 * Check is player in danger
	 * @param x is a player x position
	 * @param y is a player y position
	 * @param bulet is a bulet for checking
	 * @param walls is a walls list
	 * @return true if bulet can attack player else return false
	 */
	private Boolean isDanger(float x, float y, Bulet bulet, LinkedList<Wall> walls)
	{
	
		
		Boolean result=Intersection(bulet.mFinalX, bulet.mFinalY, bulet.mFinalX-bulet.mSpeedX*1000, 
									bulet.mFinalY-bulet.mSpeedY*1000, x, y, x+128, y+64);
		
		if(!result)
			result=Intersection(bulet.mFinalX, bulet.mFinalY, bulet.mFinalX-bulet.mSpeedX*1000, 
								bulet.mFinalY-bulet.mSpeedY*1000, x, y+64, x+128, y);

		if(result)
		{
			for(int i=0;i<walls.size();i++)
			{
				Boolean temp=Intersection(bulet.mFinalX, bulet.mFinalY, x, y,
				walls.get(i).getX(), walls.get(i).getY()+64, walls.get(i).getX()+128, walls.get(i).getY());

				if(!temp)
					temp=Intersection(bulet.mFinalX, bulet.mFinalY, x, y,
							walls.get(i).getX(), walls.get(i).getY(), walls.get(i).getX()+128, walls.get(i).getY()+64);
				
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
			mState=4;
		else
		if((gameState.bulet.size()<=2)&&(canEscape==false)&&(isDanger))
			mState=3;
		else
		if((gameState.bulet.size()<=2)&&(canEscape==true)&&(isDanger))
			mState=2;
		else
		if((gameState.bulet.size()==1)&&(canEscape==true)&&(isDanger))
			mState=1;
		else
			mState=0;
		
		return mState;
	}
	
	/*
	 * Check intersection betwen line (ax1,ay1,ax2,ay2) and (bx1,by1,bx2,by2)
	 * @return true if lines intersect else return false
	 */
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
	
	/*
	 * Check danger escape possibility 
	 * @return true if player can escape danger else return false
	 */
	public Boolean canEscape(Bulet bulet, Enemy enemy)
	{
		float distance=
				(float)Math.sqrt(Math.pow(enemy.getX()-bulet.getX(),2)+(float)Math.pow(enemy.getY()-bulet.getY(),2));
		float speedBulet=(float)Math.sqrt(Math.pow(bulet.mSpeedX,2)+(float)Math.pow(bulet.mSpeedY,2));
		float speedEnemy=enemy.playerSpeed;
		
		
		Boolean result=false;
		
		if(distance/speedBulet<=128/speedEnemy)
			result=false;
		else
			result=true;
		
		return result;
	}
	
}
