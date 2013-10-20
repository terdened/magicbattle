package com.example.gameactive;

public class TextInformHolder 
{
	public float x,y,time;
	public String text;
	public  float xSize, ySize;
	
	public TextInformHolder(float x, float y, float time, String text)
	{
		this.x=x;
		this.y=y;
		this.time=time;
		this.text=text;
		xSize=0;
		ySize=0;
	}
	

	public TextInformHolder(float x, float y, float time, String text, float xSize, float ySize)
	{
		this.x=x;
		this.y=y;
		this.time=time;
		this.text=text;
		this.xSize=xSize;
		this.ySize=ySize;
	}

}
