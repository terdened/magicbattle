package com.example.gameactive;

import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class Enemy extends Player 
{
	 public AI ai;
	
	 public String element;
	 public String name;
	
	 
	 
	 public Enemy(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld,  ITiledTextureRegion player_region)
	 {
	    	super(pX, pY, vbo, camera, physicsWorld, player_region);
	 }
	
	 public void initAI()
	 {
		 ai=new AI();
	 }
	 
	 public String update(GameState gameState)
	 {
		 String action="stay";
		 
		 if(!this.isDead)
		 {
			 action=ai.getAction(gameState);
			 		 
			 if((action.equals("go"))&&(isGo==false))
			 {
				 ai.path.nextNode();
				 this.setDist( ai.path.getNodeX(),  ai.path.getNodeY());
			 }
			 else
			 if(action.equals("createWall"))
			 {
				 createWall();
			 }
			 else
			 if(action.equals("createBulet"))
			 {
				createBulet();
			 }
			 else
			 if(action.equals("createStormtrooper"))
			 {
				 createStormtroopes();
			 }
			 else
			 if(action.equals("saySmth"))
			 {
				 saySmth();
			 }
			 else
			 if(action.equals("attackPlayer"))
			 {
				 this.attackPlayer(gameState.player.getX(), gameState.player.getY());
			 }
			 else
			 if(action.equals("attackSelf"))
			 {
				this.attacked(300);
			 }
			 
			 
			 this.isAttackted=false;
		 }
		 
		  
		 return action;
			 
	 }
	 
	 public void createWall()
	 {
		 LinkedList<Wall> temp=new LinkedList<Wall>();
		 temp.add(new Wall(this.getX(),this.getY()+100,this.element));
		 temp.getLast().rotation=135;
		 temp.add(new Wall(this.getX()+50,this.getY()+100,this.element));
		 temp.getLast().rotation=90;
		 temp.add(new Wall(this.getX()+100,this.getY()+100,this.element));
		 temp.getLast().rotation=45;
		 ai.setWallList(temp);
	 }
	 
	 public void createStormtroopes()
	 {
		 LinkedList<enviromentObject> temp=new LinkedList<enviromentObject>();
		 int count=5;
		 
		 for(int i=0;i<count;i++)
		 {
			 temp.add(new enviromentObject(800/(2*count)+i*800/count, 450, false, 350, "stormtrooper", 0));
			 temp.getLast().rotation=(float)Math.random()*90+135;
		 }
		 
		

		 ai.setObjectList(temp);
	 }
	 
	 
	 public void createBulet()
	 {
		 LinkedList<Bulet> temp=new LinkedList<Bulet>();
		 

		 	 temp.add(new Bulet(this.getDamage(),this.element));
		 	 temp.getLast().init(this.getX()+64,this.getY()+100+32,100);
		 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
		 	 temp.getLast().lastInit(this.getX()+65,this.getY()+120+32,110);
		 	 
		 	temp.add(new Bulet(this.getDamage(),this.element));
		 	 temp.getLast().init(this.getX()+64,this.getY()-100+32,100);
		 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
		 	 temp.getLast().lastInit(this.getX()+65,this.getY()-120+32,110);
		 	 
		 	temp.add(new Bulet(this.getDamage(),this.element));
		 	 temp.getLast().init(this.getX()+130+64,this.getY()+32,100);
		 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
		 	 temp.getLast().lastInit(this.getX()+150+64,this.getY()+32,110);
		 	 
		 	temp.add(new Bulet(this.getDamage(),this.element));
		 	 temp.getLast().init(this.getX()-130+64,this.getY()+32,100);
		 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
		 	 temp.getLast().lastInit(this.getX()-150+64,this.getY()+32,110);

		 ai.setBuletList(temp);
	 }
	 
	 public void attackPlayer(float playerX, float playerY)
	 {
		 float tan=(this.getX()-playerX)/(playerY-this.getY());
		 float alpha=(float)Math.atan(tan);
		 alpha+=Math.toRadians(90);
		 
		 LinkedList<Bulet> temp=new LinkedList<Bulet>();
		 

	 	 temp.add(new Bulet(this.getDamage(),this.element));
	 	 temp.getLast().init(this.getX()+this.getWidth()/2,this.getY()+100+this.getHeight()/2,100);
 
	 	 
	 	 float distX=(float)(this.getX()+this.getWidth()/2+10*Math.cos(alpha));
	 	 float distY=(float)(this.getY()+this.getHeight()/2+100+10*Math.sin(alpha));
	 	 temp.getLast().lastInit(distX,distY,110);
	 	 

	 ai.setBuletList(temp);
	 }
	 
	 
	 public void saySmth()
	 {
		 LinkedList<String> phrasesList=new LinkedList<String>();
		 
		 if(name.equals("sokrat"))
		 {
			 phrasesList.add("Я знаю только то,\n что ничего не знаю");
			 phrasesList.add("Заговори, чтобы\n я тебя увидел");
			 phrasesList.add("Нельзя врачевать тело,\n не врачуя души");
			 phrasesList.add("Женишься ты или нет\n - все равно раскаешься");
			 phrasesList.add("Высшая мудрость \n различать добро и зло");
			 
		 }else
			 if(name.equals("platon"))
			 {
				 phrasesList.add("Бог в нас самих");
				 phrasesList.add("Сколько рабов,\n столько врагов");
				 phrasesList.add("Не прибавляй\n огонь к огню");
				 phrasesList.add("Основа всякой мудрости\n есть терпение");
				 
			 }else
			 	 if(name.equals("aristotel"))
				 {
					 phrasesList.add("Платон - друг,\n но истина дороже");
					 phrasesList.add("Начало есть более \n чем половина всего");
					 phrasesList.add("Жизнь требует движения");
					 phrasesList.add("Друг всем \n ничей друг");
					 phrasesList.add("Преступление нуждается \n лишь в предлоге");
					 
				 }
		int index=(int)(Math.random()*phrasesList.size());
		
		TextInformHolder temp = new TextInformHolder(this.getX(),this.getY()-100,100,phrasesList.get(index));
		tempText.add(temp);
		
		//text.setX(64-text.getWidth()/2);
		//text.setY(32-text.getHeight()/2);
	 }
}
