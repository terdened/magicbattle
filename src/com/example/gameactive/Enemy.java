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

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


/*
 * Enemy is a Player with AI
 * Use AI parser for loading AI from XML-file
 * Enemy get next action from AI every step 
 * @author Denis Terehin
 */
public abstract class Enemy extends Player 
{
	 private Ai mAi;
	 private String mElement;
	 private String mName;
	 private GameScene mScene;
	 
	 public Enemy(final GameScene scene,float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld,  ITiledTextureRegion player_region)
	 {		 
	    	super(pX, pY, vbo, camera, physicsWorld, player_region);
	    	this.mScene=scene;
	    	
	 }
	
	 /*
	  * Initializtion of AI
	  */
	 public void initAI()
	 {
		 mAi=new Ai();
	 }
	 
	 /*
	  * Method return the best action in current situation
	  */
	 public String update(GameState gameState)
	 {
		 String action="stay";
		 
		 if(!this.getIsDead())
		 {
			 action=mAi.getAction(gameState);
			 		 
			 if((action.equals("go"))&&(isGo==false))
			 {
				 mAi.getPath().nextNode();
				 this.setDest( mAi.getPath().getNodeX(),  mAi.getPath().getNodeY());
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
			 else
			 if(action.equals("createMob"))
			 {
				this.createMob();
			 }
			 else
			 if(action.equals("mobAttack"))
			 {
				this.mobAttack();
			 }
			 
			 this.isAttackted=false;
		 }
		 
		  
		 return action;
			 
	 }
	 
	 /*
	  * Method create walls on game scene 
	  */
	 public void createWall()
	 {
		 LinkedList<Wall> temp=new LinkedList<Wall>();
		 temp.add(new Wall(this.getX(),this.getY()+100, playerMagic.wallHealth,
				 mScene.resourcesManager.enemy_wall_region, mScene.vbom,this.mElement));
		 temp.getLast().setRotation(135);
		 temp.add(new Wall(this.getX()+50,this.getY()+100, playerMagic.wallHealth,
				 mScene.resourcesManager.enemy_wall_region, mScene.vbom,this.mElement));
		 temp.getLast().setRotation(90);
		 temp.add(new Wall(this.getX()+100,this.getY()+100, playerMagic.wallHealth,
				 mScene.resourcesManager.enemy_wall_region, mScene.vbom,this.mElement));
		 temp.getLast().setRotation(45);
		 this.mScene.gameSceneCreator.createWall(temp, this);
	 }
	 
	 /*
	  * Method create stormtroopes on game scene 
	  */
	 public void createStormtroopes()
	 {
		 LinkedList<EnviromentObject> temp=new LinkedList<EnviromentObject>();
		 int count=5;
		 
		 for(int i=0;i<count;i++)
		 {
			 temp.add(new EnviromentObject((float)(800/(2*count)+i*800/count), (float)450, false, 
					 mScene.resourcesManager.enemyElementMagic,(long)350, mScene, new long[] {350}, "stormtrooper"));
			 temp.getLast().setRotation((float)Math.random()*90+135);
		 }
		 this.mScene.gameSceneCreator.createStormtrooper(temp);
	 }
	 
	 /*
	  * Method create bulet on game scene 
	  */
	 public void createBulet()
	 {
		 LinkedList<Bulet> temp=new LinkedList<Bulet>();
		 

		 	 temp.add(new Bulet(mScene.resourcesManager.player_bulet_region,getDamage(),mElement, mScene));
		 	 temp.getLast().init(this.getX()+64,this.getY()+100+32,100);
		 	 temp.getLast().lastInit(this.getX()+65,this.getY()+120+32,110);
		 	 
		 	temp.add(new Bulet(mScene.resourcesManager.player_bulet_region,getDamage(),mElement, mScene));
		 	 temp.getLast().init(this.getX()+64,this.getY()-100+32,100);
		 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
		 	 temp.getLast().lastInit(this.getX()+65,this.getY()-120+32,110);
		 	
		 	temp.add(new Bulet(mScene.resourcesManager.player_bulet_region,getDamage(),mElement, mScene));
		 	 temp.getLast().init(this.getX()+130+64,this.getY()+32,100);
		 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
		 	 temp.getLast().lastInit(this.getX()+150+64,this.getY()+32,110);
		 	 
		 	temp.add(new Bulet(mScene.resourcesManager.player_bulet_region,getDamage(),mElement, mScene));
		 	 temp.getLast().init(this.getX()-130+64,this.getY()+32,100);
		 	 //temp.getLast().loadBulet(resourcesManager.player_bulet_region, vbom, wall, player, enemy);
		 	 temp.getLast().lastInit(this.getX()-150+64,this.getY()+32,110);
		 	this.mScene.gameSceneCreator.createBulet(temp, this);
	 }
	 
	 /*
	  * Method attack player 
	  */
	 public void attackPlayer(float playerX, float playerY)
	 {
		 float tan=(this.getX()-playerX)/(playerY-this.getY());
		 float alpha=(float)Math.atan(tan);
		 alpha+=Math.toRadians(90);
		 
		 LinkedList<Bulet> temp=new LinkedList<Bulet>();
		 

		 temp.add(new Bulet(mScene.resourcesManager.player_bulet_region,getDamage(),mElement, mScene));
	 	 temp.getLast().init(this.getX()+this.getWidth()/2,this.getY()+100+this.getHeight()/2,100);
 
	 	 
	 	 float distX=(float)(this.getX()+this.getWidth()/2+10*Math.cos(alpha));
	 	 float distY=(float)(this.getY()+this.getHeight()/2+100+10*Math.sin(alpha));
	 	 temp.getLast().lastInit(distX,distY,110);

	 	 this.mScene.gameSceneCreator.createBulet(temp, this);
	 }
	 
	 /*
	  * Method create mob on game scene 
	  */
	 public void createMob()
	 {
		 mScene.gameSceneCreator.createMob("Soul",0);
	 }
	 
	 /*
	  * Method say "attack" for mobs
	  */
	 public void mobAttack()
	 {
		 for(int i=0;i<mScene.mobsList.size();i++)
			 mScene.mobsList.get(i).Go(0);
	 }
	 
	 /*
	  * Method cerate text on game scene
	  */
	 public void saySmth()
	 {
		 LinkedList<String> phrasesList=new LinkedList<String>();
		 
		 if(mName.equals("sokrat"))
		 {
			 phrasesList.add("Я знаю только то,\n что ничего не знаю");
			 phrasesList.add("Заговори, чтобы\n я тебя увидел");
			 phrasesList.add("Нельзя врачевать тело,\n не врачуя души");
			 phrasesList.add("Женишься ты или нет\n - все равно раскаешься");
			 phrasesList.add("Высшая мудрость \n различать добро и зло");
			 
		 }else
			 if(mName.equals("platon"))
			 {
				 phrasesList.add("Бог в нас самих");
				 phrasesList.add("Сколько рабов,\n столько врагов");
				 phrasesList.add("Не прибавляй\n огонь к огню");
				 phrasesList.add("Основа всякой мудрости\n есть терпение");
				 
			 }else
			 	 if(mName.equals("aristotel"))
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
	 }

	public Ai getAi() {
		return mAi;
	}

	public void setAi(Ai mAi) {
		this.mAi = mAi;
	}

	public String getElement() {
		return mElement;
	}

	public void setElement(String mElement) {
		this.mElement = mElement;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}
}
