package com.example.gameactive;
import java.util.LinkedList;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class MagicParser extends DefaultHandler
{
	PlayerMagic playerMagic = new PlayerMagic();
	String thisElement = "";
	String tempMagic="none";
	String tempCost="none";
	String tempPower="none";
	String tempTime="none";
	String tempType="none";
	String tempNode="none";
	String tempMPS="none";
	String tempHP="none";
	String tempMP="none";
	
	
	public PlayerMagic getResult(){
	  return playerMagic;
	}

	@Override
	public void startDocument() throws SAXException {
	  System.out.println("Start parse XML...");
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
	  thisElement = qName;
	  if(thisElement.equals("element"))
	  {
		  playerMagic.element=atts.getValue("value");
	  }
	  
	  if(thisElement.equals("HP"))
	  {
		  this.tempHP=atts.getValue("value");
	  }
	  if(thisElement.equals("MP"))
	  {
		  this.tempMP=atts.getValue("value");
	  }
	  if(thisElement.equals("MPS"))
	  {
		  this.tempMPS=atts.getValue("value");
	  }
	  
	  
	  if(thisElement.equals("magic"))
	  {
		  tempMagic=atts.getValue("value");
		  tempCost=atts.getValue("cost");
		  tempPower=atts.getValue("power");
		  if((tempMagic.equals("buf"))||(tempMagic.equals("debuf")))
		  {
			  tempType=atts.getValue("type");
			  tempTime=atts.getValue("time");
		  }
		  tempNode="magic";
	  }

	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
	  thisElement = "";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	  if (tempNode.equals("magic")) {
		 String tempValue=new String(ch, start, length);
		 if(tempMagic.equals("buf"))
			 playerMagic.initBuf(tempType, Float.parseFloat(tempPower), Long.parseLong(tempTime), Float.parseFloat(tempCost));
		 if(tempMagic.equals("debuf"))
			 playerMagic.initDebuf(tempType, Float.parseFloat(tempPower), Long.parseLong(tempTime), Float.parseFloat(tempCost));
		 if(tempMagic.equals("bulet"))
			 playerMagic.initBulet(Float.parseFloat(tempPower), Float.parseFloat(tempCost));
		 if(tempMagic.equals("wall"))
			 playerMagic.initWall(Float.parseFloat(tempPower), Float.parseFloat(tempCost));
		 if(tempMagic.equals("element"))
			 playerMagic.initElement(Float.parseFloat(tempPower), Float.parseFloat(tempCost));
		 	     
	  }
	  tempNode="none";
	    
	}

	@Override
	public void endDocument() {
	  System.out.println("Stop parse XML...");
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