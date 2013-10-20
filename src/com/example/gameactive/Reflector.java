package com.example.gameactive;

import java.util.ArrayList;
import java.util.LinkedList;


public class Reflector {
	
	public String action;
	public float k;
	public LinkedList<ReceptorK> receptorsK;
		
	public Reflector(String action, LinkedList<ReceptorK> receptorsK)
	{
		k=0;
		this.action=action;
		this.receptorsK=receptorsK;
	}

	public void estimateK(ArrayList<Receptor> receptorsList)
	{
		float result=0;
		for(int i=0;i<receptorsK.size();i++)
		{
			int tempIndex=-1;

			for(int j=0;j<receptorsList.size();j++)
			{
				if(receptorsList.get(j).id==receptorsK.get(i).id)
				{
					tempIndex=j;
					break;
				}
			}
			

				float temp=receptorsK.get(i).k[receptorsList.get(tempIndex).state]/receptorsK.size();
				result+=temp*(1-result);


		}
		
		k=result;
	}
	
}
