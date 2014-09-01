package com.magickbattle.game;

import java.io.InputStream;
import java.util.LinkedList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.magickbattle.engine.BaseScene;
import com.magickbattle.engine.ResourcesManager;
import com.magickbattle.engine.SceneManager;
import com.magickbattle.engine.SceneManager.SceneType;
import com.magickbattle.game.character.Enemy;
import com.magickbattle.game.character.Player;
import com.magickbattle.game.gui.InformText;
import com.magickbattle.game.gui.MagicBar;
import com.magickbattle.game.gui.PlayerStatus;
import com.magickbattle.game.gui.TextInformHolder;
import com.magickbattle.game.level.Level;
import com.magickbattle.game.magick.Buf;
import com.magickbattle.game.magick.Bulet;
import com.magickbattle.game.magick.MagicParser;
import com.magickbattle.game.magick.PlayerMagic;
import com.magickbattle.game.magick.Wall;


public class GameScene extends BaseScene implements IOnSceneTouchListener 
{
	
	//Start variables
	public Player player;
	public GameSceneLoader gameSceneLoader= new GameSceneLoader(this);
	public GameSceneCreator gameSceneCreator= new GameSceneCreator(this);
	public GameSceneEventsController gameSceneEventController= new GameSceneEventsController(this);
	public LinkedList<InformText> textList= new LinkedList<InformText>();
	public Sprite bckgr;
	public Sprite river;
	public LinkedList<Bulet> bulet;
	public LinkedList<Enemy> mEnemyList;
	public LinkedList<Buf> buf;
	public LinkedList<Wall> wall;
	public LinkedList<Body> wallBody;
	public PlayerMagic playerMagic;
	public int objectsCol=0;
	public Boolean touchPlayer=false;
	public Boolean touchEnemy=false;
	public Boolean freePlayer=false;
	public Boolean freeEnemy=false;
	public Weather weather;
	public float xLastWall=0;
	public float yLastWall=0;
	public float startX=0;
	public float startY=0;
	public float lastX=0;
	public float lastY=0;
	public float nowX=0;
	public float nowY=0;
	public long lastTapTime=0;
	public long startTapTime=0;
	public int buletCount=0;
	public int bufCount=0;
	public MagicBar magicBar;
	public PlayerStatus playerStatus;
	public LinkedList<PlayerStatus> enemyStatus=new LinkedList<PlayerStatus>();
	public HUD gameHUD;
	public FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
	public PhysicsWorld physicsWorld;
	public String levelName;
	public Level mLevel;
	public Entity mBackground;
	public LinkedList<Enemy> mEnemiesToRemove;
	//End variables
	
	public void addTextList(LinkedList<TextInformHolder> tempList,float width)
	{
		for(int i=0;i<tempList.size();i++)
		{
			InformText tempText=new InformText(tempList.get(i).getX(), tempList.get(i).getY(),
					resourcesManager.font, tempList.get(i).getText(), vbom, (int)tempList.get(i).getTime());  		
			textList.add(tempText);
			textList.getLast().setScale(0.5f);
			textList.getLast().setX(textList.getLast().getX()+width/2-textList.getLast().getWidth()/2);
			
			this.attachChild(textList.getLast());
		}
	}
	
	public GameScene(String element, String level)
	{
		mEnemyList = new LinkedList<Enemy>();
		mEnemiesToRemove = new LinkedList<Enemy>();
		levelName=level;
		try
	    {
		    SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
		    MagicParser tempPlayerMagic=new MagicParser();

		    InputStream curDir = activity.getAssets().open("playerMagic/"+element+"PlayerMagic.lvl");
		    saxParser.parse(curDir, tempPlayerMagic);
		    playerMagic=tempPlayerMagic.getResult();
	    }
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
		
		loadLevel(1);
        
        
        gameSceneCreator.createHUD();
        gameSceneCreator.createMagicBar();
		
		//enemyMagic=enemy.playerMagic;
		player.playerMagic=playerMagic;
		//player.oppositeMagic=enemyMagic;
		
		
	}
	
	private void loadLevel(int levelID)
	{
		gameSceneLoader.loadLevel(levelID);
		int a=0;
	}

    @Override
    public void createScene()
    {

		mBackground = new Entity();
		this.attachChild(mBackground);
    	gameSceneCreator=new GameSceneCreator(this);
    	gameSceneEventController= new GameSceneEventsController(this);
       
        //createControllers();
    	gameSceneCreator.createPhysics();
        //createRiver();s
        
        buletCount=0;

        weather = new Weather(false, 0, false, 0, false, 0 , new Vector2(0,0), 
        		resourcesManager.player_region.mWallRegion, resourcesManager,this);
        this.attachChild(weather.scene);
        //weather.setRain(2000);
        bulet = new LinkedList<Bulet>();
        buf = new LinkedList<Buf>();
        wall = new LinkedList<Wall>(); 
        wallBody= new LinkedList<Body>();
        setOnSceneTouchListener(this);
        gameSceneEventController.onSceneUpdateEvent(this);
        
    }
     
    @Override
    public void onBackKeyPressed()
    {
    	disposeScene();
    	SceneManager.getInstance().loadPlayerMenuScene(engine);
    }
    
    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene()
    {
    	camera.setHUD(null);
        camera.setCenter(640, 400);
        camera.setChaseEntity(null);
    }
    
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
    {
    	 return gameSceneEventController.onSceneTouchEvent( pScene, pSceneTouchEvent);
    }
    
    public void attachLevelHolder(Level pLevel)
    {
    	mLevel=pLevel;
    	mBackground.attachChild(mLevel);
    }
    
    public void attachEnemy(Enemy pEnemy)
    {
    	mEnemyList.add(pEnemy);
    }
    
    public void detachEnemy(Enemy pEnemy)
    {
    	mEnemyList.remove(pEnemy);
    	
    	if(pEnemy.body!=null)
    		physicsWorld.destroyBody(pEnemy.body);
    	
    	this.unregisterTouchArea(pEnemy);
    	this.detachChild(pEnemy);
    }
    
}