package com.example.gameactive;

import java.util.LinkedList;

public class Path 
{
	LinkedList<PathNode> nodeList=new LinkedList<PathNode>();
	int currentNodeIndex=0;
	
	Path()
	{
		nodeList=new LinkedList<PathNode>();
		currentNodeIndex=0;
	}
	
	public float getNodeX()
	{
		return nodeList.get(currentNodeIndex).getX();
	}
	
	public float getNodeY()
	{
		return nodeList.get(currentNodeIndex).getY();
	}
	
	public void addNode(PathNode node)
	{
		nodeList.add(node);
	}
	
	public void nextNode()
	{
		currentNodeIndex++;
		if(currentNodeIndex==nodeList.size())
			currentNodeIndex=0;
	}
}
