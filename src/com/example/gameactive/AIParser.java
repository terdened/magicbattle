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

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/*
 * This class load AI parametres from XML-file using SAX-parser.
 * @author Denis Terehin
 */
public class AiParser extends DefaultHandler
{
	private Ai mAi;
	private String mThisElement;
	private int mTempId;
	private String mTempAction;
	private float[] mTempReceptorK={0,0,0,0,0};
	private int mCurrentIndex;
	private LinkedList<ReceptorK> mTempReceptorKList;
	private Boolean mIsCreated;
	
	public void init(GameScene scene){
		mAi = new Ai();
		mIsCreated=false;
		mTempReceptorKList=new LinkedList<ReceptorK>();
		mCurrentIndex=0;
		mThisElement = "";
	}
	
	public Ai getResult(){
	  return mAi;
	}

	@Override
	public void startDocument() throws SAXException {
	  System.out.println("Start parse XML...");
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
	  mThisElement = qName;
	  if(mThisElement.equals("node"))
	  {
		  if(atts.getValue("type").equals("define"))
			  mAi.getPath().addNode(new PathNode(Integer.parseInt(atts.getValue("x")),Integer.parseInt(atts.getValue("y"))));
		  else
			  mAi.getPath().addNode(new PathNode(true,Integer.parseInt(atts.getValue("x")),Integer.parseInt(atts.getValue("y")),Integer.parseInt(atts.getValue("minX")),Integer.parseInt(atts.getValue("minY"))));
	  }
	  if(mThisElement.equals("memory"))
		  mAi.setMemory(new Memory(Integer.parseInt(atts.getValue("size"))));
	  if(mThisElement.equals("receptor"))
		  mTempId=Integer.parseInt(atts.getValue("id"));
	  if(mThisElement.equals("reflector"))
		  mTempAction=atts.getValue("action");
	  if(mThisElement.equals("receptork"))
	  {
		  mTempAction=atts.getValue("action");
		  mTempId=Integer.parseInt(atts.getValue("id"));
		  for(int i=0;i<mAi.getReflectors().size();i++)
		  {
			  if(mAi.getReflectors().get(i).getAction().equals(mTempAction))
			  {
				  mTempReceptorKList.add(new ReceptorK(mTempId, mTempReceptorK));
				  mAi.getReflectors().getLast().getReceptorsK().add(new ReceptorK(mTempId, mTempReceptorK));
				  break;
			  }
		  }
		  
		  mTempReceptorKList=new LinkedList<ReceptorK>();
		  mCurrentIndex=0;
		  mTempReceptorK=new float[]{0,0,0,0,0};
		  mIsCreated=false;
	  }
	  if(mThisElement.equals("k"))
	  {
		  mTempReceptorK[mCurrentIndex]=Float.parseFloat(atts.getValue("value"));
		  mCurrentIndex++;
	  }
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
	  mThisElement = "";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	  if (mThisElement.equals("receptor")) {
		 String tempValue=new String(ch, start, length);
		 if(tempValue.equals("isDamage"))
			 mAi.getReceptors().add(new ReceptorDamage(mTempId));
		 if(tempValue.equals("random"))
			 mAi.getReceptors().add(new ReceptorRandom(mTempId));
		 if(tempValue.equals("isAttackted"))
			 mAi.getReceptors().add(new ReceptorAttackted(mTempId));
		 if(tempValue.equals("isAnotherAttacked"))
			 mAi.getReceptors().add(new ReceptorAnotherAttacked(mTempId));
		 if(tempValue.equals("isStay"))
			 mAi.getReceptors().add(new ReceptorIsStay(mTempId));
		 if(tempValue.equals("thereAreMobs"))
			 mAi.getReceptors().add(new ReceptorThereAreMobs(mTempId));
		 if(tempValue.equals("isWallRepeat"))
			 mAi.getReceptors().add(new ReceptorRepeatControl(mTempId,"createWall",new float[] {50,100,150,200}));
		 if(tempValue.equals("isBuletRepeat"))
			 mAi.getReceptors().add(new ReceptorRepeatControl(mTempId,"createBulet",new float[] {50,100,150,200}));
		 if(tempValue.equals("isAttackPlayerRepeat"))
			 mAi.getReceptors().add(new ReceptorRepeatControl(mTempId,"attackPlayer",new float[] {50,100,150,200}));
		 if(tempValue.equals("isGoRepeat"))
			 mAi.getReceptors().add(new ReceptorRepeatControl(mTempId,"go",new float[] {50,100,150,200}));
		 if(tempValue.equals("isStartGoRepeat"))
			 mAi.getReceptors().add(new ReceptorRepeatControl(mTempId,"go",new float[] {10,11,12,200}));
		 if(tempValue.equals("isStormtrooperRepeat"))
			 mAi.getReceptors().add(new ReceptorRepeatControl(mTempId,"createStormtrooper",new float[] {500,600,700,800}));
		 		 	     
	  }
	  
	  if ((mThisElement.equals("reflector"))&&(mIsCreated==false)) {
			 mAi.getReflectors().add(new Reflector(mTempAction,mTempReceptorKList));
			 mTempReceptorKList=new LinkedList<ReceptorK>();
			 mIsCreated=true;
		  }
	    
	}

	@Override
	public void endDocument() {
	  System.out.println("Stop parse XML...");
	}
}
