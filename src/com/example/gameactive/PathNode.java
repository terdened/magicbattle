package com.example.gameactive;

public class PathNode 
{
	String type;
	float x;
	float y;
	float maxX;
	float maxY;
	float minX;
	float minY;
	
	
	
	PathNode(float x, float y)
	{
		type="define";
		this.x=x;
		this.y=y;
	}
	
	PathNode(Boolean temp ,float maxX, float maxY, float minX, float minY)
	{
		type="random";
		this.maxX=maxX;
		this.maxY=maxY;
		this.minX=minX;
		this.minY=minY;
	}
	
	public float getX()
	{
		if(type=="define")
			return x;
		else
			return (float)Math.random()*maxX;
	}
	
	public float getY()
	{
		if(type=="define")
			return y;
		else
			return (float)Math.random()*maxY;
	}
}
