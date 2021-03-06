package com.magickbattle.game;

import java.util.LinkedList;
import java.util.List;

import com.magickbattle.game.character.Enemy;
import com.magickbattle.game.character.Player;
import com.magickbattle.game.magick.Bulet;
import com.magickbattle.game.magick.PlayerMagic;
import com.magickbattle.game.magick.Wall;

public class GameState {
	
	public LinkedList<Bulet> bulet;
	public LinkedList<Wall> walls;
	public Enemy enemy;
	public List<Enemy> enemyList;
	public PlayerMagic playerMagic;
	public Player player;
	public int time;
	public String action;
	
	public GameState(PlayerMagic playerMagic, Enemy enemy, LinkedList<Bulet> bulet,LinkedList<Wall> walls, Player player, List<Enemy> enemyList)
	{
		this.playerMagic=playerMagic;
		this.bulet=bulet;
		this.enemy=enemy;
		this.walls=walls;
		this.time=0;
		this.player=player;
		this.enemyList=enemyList;
	}
	
	public void setAction(String action)
	{
		this.action=action;
	}
	
	public void setTime(int time)
	{
		this.time+=time;
	}
	

	
}
