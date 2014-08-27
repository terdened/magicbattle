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
package com.magickbattle.game.magick;

/*
 * Content spells descriptions for player
 * Provide access to spells descriptions
 * @author Denis Terehin
 */
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
	
	/*
	 * Initialization of element magic
	 * @param elementCost is a magic cost in mana
	 * @param elementPower is a power of spell
	 */
	public void initElement(float elementCost, float elementPower )
	{
		this.elementCost=elementCost;
		this.elementPower=elementPower;
	}
	
	/*
	 * Initialization of buf spells
	 * @param bufType is a param on which affect spell
	 * @param bufTime is a duration of spell
	 * @param bufPower is a power of spell
	 * @param Cost is a spell cost in mana
	 */
	public void initBuf(String bufType, float bufPower, long bufTime, float Cost)
	{
		this.bufType=bufType;
		this.bufPower=bufPower;
		this.bufTime=bufTime;
		bufCost=Cost;
	}
	
	/*
	 * Initialization of debuf spells
	 * @param debufType is a param on which affect spell
	 * @param debufTime is a duration of spell
	 * @param debufPower is a power of spell
	 * @param Cost is a spell cost in mana
	 */
	public void initDebuf(String debufType, float debufPower, long debufTime, float Cost)
	{
		this.debufType=debufType;
		this.debufPower=debufPower;
		this.debufTime=debufTime;
		debufCost=Cost;
	}
	
	/*
	 * Initialization of bulet spell
	 * @param buletDamage is a value of bulet damage
	 * @param Cost is a spell cost in mana
	 */
	public void initBulet(float buletDamage, float Cost)
	{
		this.buletDamage=buletDamage;
		buletCost=Cost;
	}
	
	/*
	 * Initialization of wall spell
	 * @param wallHealth is a value of wall health
	 * @param Cost is a spell cost in mana
	 */
	public void initWall(float wallHealth, float Cost)
	{
		this.wallHealth=wallHealth;
		wallCost=Cost;
	}
}
