package com.example.gameactive;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class ScrollableMenu {

	public LinkedList<HellCircle> circlesList;
	public Boolean blocked=false;
	public float ySpeed=0;
	public int selectedLevel=0;

	public ScrollableMenu()
	{
		circlesList=new LinkedList<HellCircle>();
	}
	
	public void addCircle(ResourcesManager resourcesManager, VertexBufferObjectManager vbom)
	{
		circlesList.add(new HellCircle(resourcesManager.limb_region,vbom));
		circlesList.getLast().scene.setY((circlesList.size()-1)*1280);
	}
	
	public void moveDown()
	{
		if(!blocked)
		if(selectedLevel>0)
		{
			ySpeed=32;
			blocked=true;
			selectedLevel--;
		}		
	}
	
	public void moveUp()
	{
		if(!blocked)
		if(selectedLevel<circlesList.size()-1)
		{
			ySpeed=-32;
			blocked=true;
			selectedLevel++;
		}		
	}
	
	public void update()
	{
		if((blocked)&&(ySpeed!=0))
		{
			for(int i=0; i<circlesList.size();i++)
			{
				circlesList.get(i).scene.setY(circlesList.get(i).scene.getY()+ySpeed);
			}
			if(circlesList.get(selectedLevel).scene.getY()==0)
			{	
				blocked=false;
				ySpeed=0;
			}
		}
	}
	
	public void clearSelectedLevel()
	{
		for(int i=0;i<circlesList.size();i++)
		{
			circlesList.get(i).selectedLevel="none";
		}
	}
	
	
	 
}
