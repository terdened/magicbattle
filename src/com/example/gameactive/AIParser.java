package com.example.gameactive;
import java.util.LinkedList;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class AIParser extends DefaultHandler
{
	AI ai = new AI();
	String thisElement = "";
	int tempId;
	String tempAction;
	float[] tempReceptorK={0,0,0,0,0};
	int currentIndex=0;
	LinkedList<ReceptorK> tempReceptorKList=new LinkedList<ReceptorK>();
	Boolean isCreated=false;
	
	public AI getResult(){
	  return ai;
	}

	@Override
	public void startDocument() throws SAXException {
	  System.out.println("Start parse XML...");
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
	  thisElement = qName;
	  if(thisElement.equals("node"))
	  {
		  if(atts.getValue("type").equals("define"))
			  ai.path.addNode(new PathNode(Integer.parseInt(atts.getValue("x")),Integer.parseInt(atts.getValue("y"))));
		  else
			  ai.path.addNode(new PathNode(true,Integer.parseInt(atts.getValue("x")),Integer.parseInt(atts.getValue("y")),Integer.parseInt(atts.getValue("minX")),Integer.parseInt(atts.getValue("minY"))));
	  }
	  if(thisElement.equals("memory"))
		  ai.memory=new Memory(Integer.parseInt(atts.getValue("size")));
	  if(thisElement.equals("receptor"))
		  tempId=Integer.parseInt(atts.getValue("id"));
	  if(thisElement.equals("reflector"))
		  tempAction=atts.getValue("action");
	  if(thisElement.equals("receptork"))
	  {
		  tempAction=atts.getValue("action");
		  tempId=Integer.parseInt(atts.getValue("id"));
		  for(int i=0;i<ai.reflectors.size();i++)
		  {
			  if(ai.reflectors.get(i).action.equals(tempAction))
			  {
				  tempReceptorKList.add(new ReceptorK(tempId, tempReceptorK));
				  ai.reflectors.getLast().receptorsK.add(new ReceptorK(tempId, tempReceptorK));
				  break;
			  }
		  }
		  
		  tempReceptorKList=new LinkedList<ReceptorK>();
		  currentIndex=0;
		  tempReceptorK=new float[]{0,0,0,0,0};
		  isCreated=false;
	  }
	  if(thisElement.equals("k"))
	  {
		  tempReceptorK[currentIndex]=Float.parseFloat(atts.getValue("value"));
		  currentIndex++;
	  }
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
	  thisElement = "";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	  if (thisElement.equals("receptor")) {
		 String tempValue=new String(ch, start, length);
		 if(tempValue.equals("isDamage"))
			 ai.receptors.add(new ReceptorDamage(tempId));
		 if(tempValue.equals("random"))
			 ai.receptors.add(new ReceptorRandom(tempId));
		 if(tempValue.equals("isAttackted"))
			 ai.receptors.add(new ReceptorAttackted(tempId));
		 if(tempValue.equals("isAnotherAttacked"))
			 ai.receptors.add(new ReceptorAnotherAttacked(tempId));
		 if(tempValue.equals("isWallRepeat"))
			 ai.receptors.add(new ReceptorRepeatControl(tempId,"createWall",new float[] {50,100,150,200}));
		 if(tempValue.equals("isBuletRepeat"))
			 ai.receptors.add(new ReceptorRepeatControl(tempId,"createBulet",new float[] {50,100,150,200}));
		 if(tempValue.equals("isAttackPlayerRepeat"))
			 ai.receptors.add(new ReceptorRepeatControl(tempId,"attackPlayer",new float[] {50,100,150,200}));
		 if(tempValue.equals("isGoRepeat"))
			 ai.receptors.add(new ReceptorRepeatControl(tempId,"go",new float[] {50,100,150,200}));
		 if(tempValue.equals("isStormtrooperRepeat"))
			 ai.receptors.add(new ReceptorRepeatControl(tempId,"createStormtrooper",new float[] {500,600,700,800}));
		 		 	     
	  }
	  
	  if ((thisElement.equals("reflector"))&&(isCreated==false)) {
			 ai.reflectors.add(new Reflector(tempAction,tempReceptorKList));
			 tempReceptorKList=new LinkedList<ReceptorK>();
			 isCreated=true;
		  }
	    
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