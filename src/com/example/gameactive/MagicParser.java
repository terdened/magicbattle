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
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/*
 * Class parse magics type from XML-file
 * Class use SAX-parser
 * @author Denis Terehin
 */
public class MagicParser extends DefaultHandler
{
	private PlayerMagic mPlayerMagic;
	private String mThisElement;
	private String mTempMagic;
	private String mTempCost;
	private String mTempPower;
	private String mTempTime;
	private String mTempType;
	private String mTempNode;
	private String mTempMPS;
	private String mTempHP;
	private String mTempMP;
	
	public MagicParser()
	{
		mPlayerMagic = new PlayerMagic();
		mThisElement = "";
		mTempMagic="none";
		mTempCost="none";
		mTempPower="none";
		mTempTime="none";
		mTempType="none";
		mTempNode="none";
		setTempMPS("none");
		setTempHP("none");
		setTempMP("none");
	}
	
	public PlayerMagic getResult(){
	  return mPlayerMagic;
	}

	@Override
	public void startDocument() throws SAXException {
	  System.out.println("Start parse XML...");
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
	  mThisElement = qName;
	  if(mThisElement.equals("element"))
	  {
		  mPlayerMagic.element=atts.getValue("value");
	  }
	  
	  if(mThisElement.equals("HP"))
	  {
		  this.setTempHP(atts.getValue("value"));
	  }
	  if(mThisElement.equals("MP"))
	  {
		  this.setTempMP(atts.getValue("value"));
	  }
	  if(mThisElement.equals("MPS"))
	  {
		  this.setTempMPS(atts.getValue("value"));
	  }
	  
	  
	  if(mThisElement.equals("magic"))
	  {
		  mTempMagic=atts.getValue("value");
		  mTempCost=atts.getValue("cost");
		  mTempPower=atts.getValue("power");
		  if((mTempMagic.equals("buf"))||(mTempMagic.equals("debuf")))
		  {
			  mTempType=atts.getValue("type");
			  mTempTime=atts.getValue("time");
		  }
		  mTempNode="magic";
	  }

	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
	  mThisElement = "";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	  if (mTempNode.equals("magic")) {
		 String tempValue=new String(ch, start, length);
		 if(mTempMagic.equals("buf"))
			 mPlayerMagic.initBuf(mTempType, Float.parseFloat(mTempPower), Long.parseLong(mTempTime), Float.parseFloat(mTempCost));
		 if(mTempMagic.equals("debuf"))
			 mPlayerMagic.initDebuf(mTempType, Float.parseFloat(mTempPower), Long.parseLong(mTempTime), Float.parseFloat(mTempCost));
		 if(mTempMagic.equals("bulet"))
			 mPlayerMagic.initBulet(Float.parseFloat(mTempPower), Float.parseFloat(mTempCost));
		 if(mTempMagic.equals("wall"))
			 mPlayerMagic.initWall(Float.parseFloat(mTempPower), Float.parseFloat(mTempCost));
		 if(mTempMagic.equals("element"))
			 mPlayerMagic.initElement(Float.parseFloat(mTempPower), Float.parseFloat(mTempCost));
		 	     
	  }
	  mTempNode="none";
	    
	}

	@Override
	public void endDocument() {
	  System.out.println("Stop parse XML...");
	}

	public PlayerMagic getPlayerMagic() {
		return mPlayerMagic;
	}

	public void setPlayerMagic(PlayerMagic mPlayerMagic) {
		this.mPlayerMagic = mPlayerMagic;
	}

	public String getTempMPS() {
		return mTempMPS;
	}

	public void setTempMPS(String mTempMPS) {
		this.mTempMPS = mTempMPS;
	}

	public String getTempHP() {
		return mTempHP;
	}

	public void setTempHP(String mTempHP) {
		this.mTempHP = mTempHP;
	}

	public String getTempMP() {
		return mTempMP;
	}

	public void setTempMP(String mTempMP) {
		this.mTempMP = mTempMP;
	}
}





/*@Override
public void startElement(String uri, String localName, String qName,
        Attributes attributes) throws SAXException {
    System.out.println("Тег: "+qName);
    if(qName.equals("book"))
        System.out.println("id книги "+attributes.getValue("id"));
//      System.out.println(attributes.getLength());
    super.startElement(uri, localName, qName, attributes);

    
}

@Override
public void characters(char[] c, int start, int length) 
                                             throws SAXException {
    super.characters(c, start,  length);
    for(int i=start;i< start+length;++i)
        System.err.print(c[i]);
}

@Override
public void endElement(String uri, String localName, String qName) 
                                                throws SAXException {
    
    System.out.println("Тег разобран: "+qName);
    super.endElement(uri,localName, qName);
}

@Override
public void startDocument() throws SAXException {
    System.out.println("Начало разбора документа!");
    super.startDocument();
}

@Override
public void endDocument() throws SAXException {
    super.endDocument();
    System.out.println("Разбор документа окончен!");

}*/