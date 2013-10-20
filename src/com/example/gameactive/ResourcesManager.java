package com.example.gameactive;


import java.util.LinkedList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
//import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import com.example.gameactive.GameActivity;


public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
	public ITextureRegion splash_region;
	private BitmapTextureAtlas splashTextureAtlas;	
	
	public ITextureRegion play_region;
	public ITextureRegion options_region;
	public ITextureRegion limb_region;
	public ITextureRegion sokrat_region;
	public ITextureRegion haron_region;
	public ITextureRegion veider_region;
	public ITextureRegion dorian_region;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	
	public ITextureRegion base_region;
	public ITextureRegion knob_region;
	public ITiledTextureRegion player_region;
	
	public ITiledTextureRegion enemys_regions[];
	
	public ITiledTextureRegion enemy_region;
	public ITextureRegion enemy_bulet_region;
	public ITextureRegion enemy_buf_region;
	public ITextureRegion enemy_debuf_region;
	public ITiledTextureRegion enemy_wall_region;
	public ITiledTextureRegion enemyBuletTail;
	public ITiledTextureRegion enemyElementMagic;
	
	public ITiledTextureRegion rain;
	public ITiledTextureRegion playerBuletTail;
	public ITiledTextureRegion player_region_water;
	public ITiledTextureRegion player_region_fire;
	public ITiledTextureRegion dark_shadow;
	public ITiledTextureRegion light_shadow;
	public ITiledTextureRegion healthDownBuf;
	public ITiledTextureRegion healthUpBuf;
	public ITiledTextureRegion speedUpBuf;
	public ITiledTextureRegion speedDownBuf;
	public ITextureRegion gamebkg_region;
	
	public LinkedList<ITextureRegion> objectsList=new LinkedList <ITextureRegion>();
	public ITextureRegion river_region;
	
	public ITextureRegion player_bulet_region;
	public ITextureRegion player_buf_region;
	public ITextureRegion player_debuf_region;
	public ITiledTextureRegion wall_region;
	public ITiledTextureRegion bulet_icon;
	public ITiledTextureRegion wall_icon;
	public ITiledTextureRegion nature_icon;
	public ITiledTextureRegion buf_icon;
	public ITiledTextureRegion debuf_icon;
	public ITextureRegion healthPlusIcon;
	public ITextureRegion healthMinusIcon;
	public ITextureRegion speedPlusIcon;
	public ITextureRegion speedMinusIcon;
	public ITextureRegion healthLine;
	public ITextureRegion manaLine;
	
	public ITextureRegion fireElementButton;
	public ITextureRegion waterElementButton;
	public ITextureRegion earthElementButton;
	public ITextureRegion windElementButton;
	public ITextureRegion backButton;
	private BuildableBitmapTextureAtlas playerMenuTextureAtlas;
	
	private BuildableBitmapTextureAtlas gameTextureAtlas;
	
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    
    public Engine engine;
    public GameActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vbom;
    
    public Font font;
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    

    private void loadMenuFonts()
    {
    	FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.TTF", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }
    
    public void loadMenuResources()
    {
    	loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    
    public void loadGameResources(String element, String level)
    {
        loadGameGraphics(element, level);
        loadGameFonts();
        loadGameAudio();
    }
    
    private void loadMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
    	
    	
    	try 
    	{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    
    private void loadMenuAudio()
    {
        
    }

    private void loadGameGraphics(String element, String level)
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
	    gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
	    	   
	    objectsList=new LinkedList <ITextureRegion>();
	    
	    player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/playerWater.png", 8, 1);
	    if(element=="water")
	    {
	    	player_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/playerWater.png", 8, 1);
	    	player_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_bulet.png");
		    player_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_buf.png");
		    player_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_debuf.png");
		    wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
		    playerBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/waterTail.png", 8, 1);   
	    }
	    if(element=="fire")
	    {
	    	player_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/playerFire.png", 8, 1);
	    	player_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/fire_bulet.png");
		    player_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/fire_buf.png");
		    player_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/fire_debuf.png");
		    wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/earthWall.png", 8, 1);
		    playerBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/fireTail.png", 8, 1);
	    }
	    if(element=="earth")
	    {
	    	player_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/playerEarth.png", 8, 1);
	    	player_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/earth_bulet.png");
		    player_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/earth_buf.png");
		    player_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/earth_debuf.png");
		    wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/earthWall.png", 8, 1);
		    playerBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/fireTail.png", 8, 1);
	    }
	    if(element=="wind")
	    {
	    	player_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/playerWind.png", 8, 1);
	    	player_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/wind_bulet.png");
		    player_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/wind_buf.png");
		    player_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/wind_debuf.png");
		    wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/windWall.png", 8, 1);
		    playerBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/fireTail.png", 8, 1);
	    }
	    
	    
	    if(level.equals("veider"))
	    {
	    	river_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "levels/river.png");
		    //objectsList.add(river_region);
		    //objectsList.getFirst()=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "levels/river.png");
	    	enemys_regions=new ITiledTextureRegion[4];
	    	enemys_regions[0]=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/DarthVader.png", 8, 1);	
	    	
		    //enemy_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/DarthVader.png", 8, 1);	
			enemy_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_bulet.png");  
			enemy_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_buf.png");  
			enemy_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_debuf.png");
			enemy_wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
		    enemyBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		    enemyElementMagic=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/stormtrooper.png", 8, 1);
		}
	    if(level.equals("sokrat"))
	    {
	    	enemys_regions=new ITiledTextureRegion[4];
	    	enemys_regions[0]=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Sokrat.png", 8, 1);	
	    	enemys_regions[1]=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Platon.png", 8, 1);	
	    	enemys_regions[2]=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Arhimed.png", 8, 1);	
	    	
		    //enemy_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Sokrat.png", 8, 1);	
			enemy_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_bulet.png");  
			enemy_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_buf.png");  
			enemy_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_debuf.png");
			enemy_wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
		    enemyBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		    enemyElementMagic=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/stormtrooper.png", 8, 1);
		}
	    if(level.equals("dorian"))
	    {
	    	enemys_regions=new ITiledTextureRegion[4];
	    	enemys_regions[0]=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Dorian.png", 8, 1);	
	    	enemys_regions[1]=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/DoriansPicture.png", 1, 1);	
	    	
	    	
		    enemy_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Sokrat.png", 8, 1);	
			enemy_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_bulet.png");  
			enemy_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_buf.png");  
			enemy_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_debuf.png");
			enemy_wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
		    enemyBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		    enemyElementMagic=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/stormtrooper.png", 8, 1);
		}
	    if(level.equals("haron"))
	    {
	    	//river_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "levels/river.png");
	    	enemys_regions=new ITiledTextureRegion[4];
	    	enemys_regions[0]=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Haron.png", 8, 1);	
	    	
	    	ITextureRegion edge=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "levels/edge.png");
		    objectsList.add(edge);
		    //enemy_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "characters/Haron.png", 8, 1);	
			enemy_bulet_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_bulet.png");  
			enemy_buf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_buf.png");  
			enemy_debuf_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "magics/water_debuf.png");
			enemy_wall_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
		    enemyBuletTail=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		    enemyElementMagic=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/stormtrooper.png", 8, 1);
		}
	    gamebkg_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "levels/"+level+"Background.png");

	    rain=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/rain.png", 8, 1);
	    dark_shadow=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/shadow.png", 8, 1);
	    light_shadow=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/shadowLight.png", 8, 1);
	    
	    speedDownBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/speedDownBuf.png", 8, 1);
	    speedUpBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/speedUpBuf.png", 8, 1);
	    healthDownBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/healthDownBuf.png", 8, 1);
	    healthUpBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "effects/healthUpBuf.png", 8, 1);
	    
	    manaLine=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "icons/manaLine.png");
	    healthLine=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "icons/healthLine.png");
	    healthPlusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "icons/healthPlusIcon.png");
		healthMinusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "icons/healthMinusIcon.png");
		speedPlusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "icons/speedPlusIcon.png");
		speedMinusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "icons/speedMinusIcon.png");
	   	    
	    bulet_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "icons/buleticon.png", 2, 1);
		wall_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "icons/wallicon.png", 2, 1);
		nature_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "icons/natureicon.png", 2, 1);
		buf_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "icons/buficon.png", 2, 1);
		debuf_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "icons/debuficon.png", 2, 1);
		 
	    try 
	    {
	        this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
	        this.gameTextureAtlas.load();
	    } 
 	    catch (final TextureAtlasBuilderException e)
	    {
	        Debug.e(e);
	    } 	
    }
    
    private void loadGameFonts()
    {
    	FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.TTF", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }
    
    private void loadGameAudio()
    {
        
    }
    
    public void unloadGameTextures()
    {
        
    }
    
    public void unloadMenuTextures()
    {
        
    }
        
    public void loadMenuTextures()
    {
        
    }
    
    public void loadSplashScreen()
    {
    
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
    	splashTextureAtlas.load();
    	
    }
    
    public void unloadSplashScreen()
    {
    	splashTextureAtlas.unload();
    	splash_region = null;
    }
    
    
    
    
    
    
    
    
    
    
    public void loadPlayerMenuResources()
    {
    	loadPlayerMenuGraphics();
        loadPlayerMenuAudio();
        //loadPlayerMenuFonts();
    }
    
    
    public void loadPlayerMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/playerMenu/");
    	playerMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
    	fireElementButton= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "fire.png");
    	waterElementButton= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "water.png");
    	earthElementButton= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "earth.png");
    	windElementButton= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "wind.png");
    	backButton= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "back.png");
    	
    	limb_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "limbBackground.png");
    	
    	sokrat_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "sokrat.png");
    	veider_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "veider.png");
    	haron_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "haron.png");
    	dorian_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "dorian.png");
    	
    	this.playerMenuTextureAtlas.load();
    	try 
    	{
    	    this.playerMenuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    public void unloadPlayerMenuTextures()
    {
        
    }
        
    public void loadPlayerMenuTextures()
    {
    	loadPlayerMenuGraphics();
    }
    
    private void loadPlayerMenuAudio()
    {
        
    }
    
    
    
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }
}

