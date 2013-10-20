package com.example.gameactive;

import java.util.ArrayList;
import java.util.LinkedList;

public class AI {

	public ArrayList<Receptor> receptors;
	public LinkedList<Reflector> reflectors;
	
	public Memory memory;
	
	
	public LinkedList<Wall> wallQueue; //Некрасиво
	public LinkedList<Bulet> buletQueue; //Некрасиво
	public LinkedList<enviromentObject> objectQueue; //Некрасиво
	
	public Path path;
	
	public AI()
	{
		receptors=new ArrayList<Receptor>();
		reflectors=new LinkedList<Reflector>();
		
		//memory=new Memory(50);
		
		//receptors.add(new ReceptorDamage(1));
		//receptors.add(new ReceptorRepeatControl(2,"createWall"));
		//receptors.add(new ReceptorRepeatControl(3,"createBulet"));
		
		LinkedList<ReceptorK> receptorsK=new LinkedList<ReceptorK>();
		
		/*receptorsK.add(new ReceptorK(1, new float[] {0.1f, 1f, 0.7f, 0.5f, 0.1f}));

		
		reflectors.add(new Reflector("go", receptorsK));
		
		
		receptorsK=new LinkedList<ReceptorK>();
		receptorsK.add(new ReceptorK(1, new float[] {1, 0.1f, 0.3f, 0.5f, 0.7f}));
		receptorsK.add(new ReceptorK(3, new float[] {0f, 0.3f, 0.5f, 0.7f, 1f}));

		
		reflectors.add(new Reflector("stay", receptorsK));
		
		
		receptorsK=new LinkedList<ReceptorK>();		
		receptorsK.add(new ReceptorK(1,  new float[] {0.1f, 0.1f, 0.3f, 0.7f, 1f}));
		receptorsK.add(new ReceptorK(2, new float[] {1f, 0.7f, 0.5f, 0.3f, 0.1f}));
		
		reflectors.add(new Reflector("createWall", receptorsK));
		
		
		receptorsK=new LinkedList<ReceptorK>();		
		receptorsK.add(new ReceptorK(1, new float[] {1f, 0.7f, 0.5f, 0.3f, 0.1f})); //рецептор опасности
		receptorsK.add(new ReceptorK(2, new float[] {1f, 1.0f, 0.9f, 0.8f, 0.7f}));	 //рецептор повтора создания стены
		receptorsK.add(new ReceptorK(3, new float[] {1f, 0.7f, 0.5f, 0.3f, 0.1f}));	 //рецептор повтора создания снаряда
		
		reflectors.add(new Reflector("createBulet", receptorsK));
		*/
		wallQueue=new LinkedList<Wall>();
		buletQueue=new LinkedList<Bulet>();
		path=new Path();
		
	}
	
	
	public String getAction(GameState gameState)
	{
		for(int i=0; i<receptors.size(); i++)
		{
			receptors.get(i).getState(gameState, memory);
		}
				
		for(int i=0; i<reflectors.size(); i++)
		{
			reflectors.get(i).estimateK(receptors);
		}
		
		
		String result=reflectors.getFirst().action;
		int resultIndex=0;
		
		for(int i=1; i<reflectors.size();i++)
		{
			if(reflectors.get(i).k>reflectors.get(resultIndex).k)
			{
				result=reflectors.get(i).action;
				resultIndex=i;
			}
		}
		
		if(!result.equals("stay"))
		{
			gameState.setAction(result);
			memory.setState(gameState);  //Запоминаем последнее состояние и реакцию на него
		}
		
		memory.update();
		
		return result;
	}
	
	
	public void setWallList(LinkedList<Wall> temp)
	{
		wallQueue=new LinkedList<Wall>();
		wallQueue=temp;
	}
	
	public LinkedList<Wall> getWallList()
	{
		LinkedList<Wall> temp=wallQueue;
		return temp;
	}
	
	
	public void setBuletList(LinkedList<Bulet> temp)
	{
		buletQueue=new LinkedList<Bulet>();
		buletQueue=temp;
	}
	
	public LinkedList<Bulet> getBuletList()
	{
		LinkedList<Bulet> temp=buletQueue;
		return temp;
	}
	
	public void setObjectList(LinkedList<enviromentObject> temp)
	{
		objectQueue=new LinkedList<enviromentObject>();
		objectQueue=temp;
	}
	
	public LinkedList<enviromentObject> getObjectList()
	{
		LinkedList<enviromentObject> temp=objectQueue;
		return temp;
	}
	
}
