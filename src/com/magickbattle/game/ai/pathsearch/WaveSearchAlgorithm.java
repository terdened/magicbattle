package com.magickbattle.game.ai.pathsearch;

import com.badlogic.gdx.math.Vector2;
import com.magickbattle.game.level.Level;

public class WaveSearchAlgorithm {
	
	public Path findPath(Level pLevel, Vector2 pStartPosition ,Vector2 pEndPosition)
	{
		Path result = new Path();
		
		int[][] levelField = pLevel.getLevelGrid();
		
		levelField = initStartPosition(levelField, pStartPosition);
		levelField = wavePropagation(levelField, pEndPosition);
		
		if(levelField[(int)pEndPosition.y][(int)pEndPosition.x]>=0)
		{
			result.mPathNodes.add(pEndPosition);
			
			while(levelField[(int)result.mPathNodes.getFirst().y][(int)result.mPathNodes.getFirst().x]!=0)
			{
				result = findNextNode(levelField,result);
			}
			
		}
		
		return result;
	}
	
	private int[][] initStartPosition(int[][] pLevelField, Vector2 pStartPosition)
	{
		pLevelField[(int)pStartPosition.y][(int)pStartPosition.x] = 0;
		return pLevelField;
	}
	
	private int[][] wavePropagation(int[][] pLevelField, Vector2 pEndPosition)
	{
		int currentStep=0;
		
		do
		{					
			for(int i=0; i<pLevelField.length;i++)
			{
				for(int j=0; j<pLevelField[i].length;j++)
				{
					if(pLevelField[i][j]==currentStep)
					{
						pLevelField = markNeighborNodes(pLevelField, new Vector2(j,i));
					}
				}
			}
			currentStep++;
			
		}while((pLevelField[(int)pEndPosition.y][(int)pEndPosition.x]==-1)&&
				(currentStep < 100 ));
			
		return pLevelField;
	}
	
	private int[][] markNeighborNodes(int[][] pLevelField, Vector2 pPosition)
	{
		if(pPosition.x>0)
		{
			pLevelField = markNode(pLevelField, new Vector2(pPosition.x-1,pPosition.y), 
					pLevelField[(int)pPosition.y][(int)pPosition.x]+1);
		}
		
		if(pPosition.y>0)
		{
			pLevelField = markNode(pLevelField, new Vector2(pPosition.x,pPosition.y-1), 
					pLevelField[(int)pPosition.y][(int)pPosition.x]+1);
		}
		
		if(pPosition.x<pLevelField[0].length-1)
		{
			pLevelField = markNode(pLevelField, new Vector2(pPosition.x+1,pPosition.y), 
					pLevelField[(int)pPosition.y][(int)pPosition.x]+1);
		}
		
		if(pPosition.y<pLevelField.length-1)
		{
			pLevelField = markNode(pLevelField, new Vector2(pPosition.x,pPosition.y+1), 
					pLevelField[(int)pPosition.y][(int)pPosition.x]+1);
		}
			
		return pLevelField;
	}
	
	private int[][] markNode(int[][] pLevelField, Vector2 pPosition, int pValue)
	{
		if(pLevelField[(int)pPosition.y][(int)pPosition.x]==-1)
			pLevelField[(int)pPosition.y][(int)pPosition.x]=pValue;
		
		return pLevelField;
	}
	
	
	private Path findNextNode(int[][] pLevelField, Path pPath)
	{
		Vector2 lastTile = pPath.mPathNodes.getFirst();
		
		if(lastTile.x>0)
		{
			if(pLevelField[(int)lastTile.y][(int)lastTile.x]-1==pLevelField[(int)lastTile.y][(int)lastTile.x-1])
			{
				pPath.mPathNodes.addFirst(new Vector2(lastTile.x-1, lastTile.y));
				return pPath;
			}
		}
		
		if(lastTile.y>0)
		{
			if(pLevelField[(int)lastTile.y][(int)lastTile.x]-1==pLevelField[(int)lastTile.y-1][(int)lastTile.x])
			{
				pPath.mPathNodes.addFirst(new Vector2(lastTile.x, lastTile.y-1));
				return pPath;
			}
		}
		
		if(lastTile.x<pLevelField[0].length-1)
		{
			if(pLevelField[(int)lastTile.y][(int)lastTile.x]-1==pLevelField[(int)lastTile.y][(int)lastTile.x+1])
			{
				pPath.mPathNodes.addFirst(new Vector2(lastTile.x+1, lastTile.y));
				return pPath;
			}
		}
		
		if(lastTile.y<pLevelField.length-1)
		{
			if(pLevelField[(int)lastTile.y][(int)lastTile.x]-1==pLevelField[(int)lastTile.y+1][(int)lastTile.x])
			{
				pPath.mPathNodes.addFirst(new Vector2(lastTile.x, lastTile.y+1));
				return pPath;
			}
		}
			
		return pPath;
	}
	
	public Vector2 realPositionToTiles(Vector2 pRealPosition)
	{
		Vector2 pTilePosition = new Vector2();
		
		pTilePosition.x=(int)pRealPosition.x/80;
		pTilePosition.y=(int)pRealPosition.y/80;
		
		return pTilePosition;
	}
	
	public Vector2 tilePositionToReal(Vector2 pTilePosition)
	{
		Vector2 pRealPosition = new Vector2();
		
		pRealPosition.x=(int)pTilePosition.x*80+40;
		pRealPosition.y=(int)pTilePosition.y*80+40;
		
		return pRealPosition;
	}
	

}
