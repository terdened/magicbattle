package com.example.gameactive;

public class PlayerMagic {
 
	public String element;
	
	public String debufType;
	public float debufPower;
	public long debufTime;
	public float debufCost;
	
	public String bufType;
	public float bufPower;
	public long bufTime;
	public float bufCost;
	
	public float buletDamage;
	public float buletCost;
	
	public float wallHealth;
	public float wallCost;
	
	public float elementCost;
	public float elementPower;
	
	public void initElement(float elementCost, float elementPower )
	{
		this.elementCost=elementCost;
		this.elementPower=elementPower;
	}
	
	public void initBuf(String bufType, float bufPower, long bufTime, float Cost)
	{
		this.bufType=bufType;
		this.bufPower=bufPower;
		this.bufTime=bufTime;
		bufCost=Cost;
	}
	
	public void initDebuf(String debufType, float debufPower, long debufTime, float Cost)
	{
		this.debufType=debufType;
		this.debufPower=debufPower;
		this.debufTime=debufTime;
		debufCost=Cost;
	}
	
	public void initBulet(float buletDamage, float Cost)
	{
		this.buletDamage=buletDamage;
		buletCost=Cost;
	}
	
	public void initWall(float wallHealth, float Cost)
	{
		this.wallHealth=wallHealth;
		wallCost=Cost;
	}
	
	
	
	
}
