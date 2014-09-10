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
package com.magickbattle.engine;


import java.util.LinkedList;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
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
import org.andengine.util.debug.Debug;
import android.graphics.Color;

/*
 * Control loading and destroing resources
 * @author Denis Terehin
 */
public class ResourcesManager
{
	private BitmapTextureAtlas mSplashTextureAtlas;
	private BuildableBitmapTextureAtlas mMenuTextureAtlas;
	private BuildableBitmapTextureAtlas mGameTextureAtlas;
	private BuildableBitmapTextureAtlas mGameBackgroundTextureAtlas;
    private static final ResourcesManager INSTANCE = new ResourcesManager();
	private BuildableBitmapTextureAtlas playerMenuTextureAtlas;
	
	public ITextureRegion splash_region;
	public ITextureRegion play_region;
	public ITextureRegion options_region;
	public ITextureRegion playermenu_background_region;
	public ITextureRegion menu_background_region;
	public ITextureRegion level_point_region;
	public ITextureRegion base_region;
	public ITextureRegion knob_region;
	public PlayerRegion player_region;
	public PlayerRegion eye_region;
	public PlayerRegion bat_region;
	public PlayerRegion worm_region;
	public PlayerRegion spider_region;
	public PlayerRegion plant_region;
	public PlayerRegion human_region;
	public ITiledTextureRegion mobs_regions[];
	public ITiledTextureRegion rain;
	public ITiledTextureRegion dark_shadow;
	public ITiledTextureRegion light_shadow;
	public ITiledTextureRegion healthDownBuf;
	public ITiledTextureRegion healthUpBuf;
	public ITiledTextureRegion speedUpBuf;
	public ITiledTextureRegion speedDownBuf;
	public ITextureRegion gamebkg_region[];
	public LinkedList<ITextureRegion> objectsList=new LinkedList <ITextureRegion>();
	public ITextureRegion river_region;
	public ITiledTextureRegion stone_region;
	public ITiledTextureRegion whole_region;
	public ITiledTextureRegion tree_region;
	public ITextureRegion edge_region;
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
    	mMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuTextureAtlas, activity, "play.png");
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuTextureAtlas, activity, "options.png");
    	
    	
    	try 
    	{
    	    this.mMenuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.mMenuTextureAtlas.load();
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
	    mGameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.DEFAULT);
	    mGameBackgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.DEFAULT);
	    	    
	    objectsList=new LinkedList <ITextureRegion>();
	    
	    player_region = new PlayerRegion();
	    player_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/playerWater.png", 6, 3);
	    edge_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "levels/edge.png");
	    
	    if(element=="water")
	    {
	    	player_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/playerWater.png", 6, 3);
	    	player_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	player_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	player_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1);   
	    }
	    if(element=="fire")
	    {
	    	player_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/playerFire.png", 6, 3);
	    	player_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/fire_bulet.png");
	    	player_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/fireWall.png", 8, 1);
	    	player_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/fireTail.png", 8, 1);   
	     }
	    if(element=="earth")
	    {
	    	player_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/playerEarth.png", 6, 3);
	    	player_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/earth_bulet.png");
	    	player_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/earthWall.png", 8, 1);
	    	player_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/earthTail.png", 8, 1);   
	    }
	    if(element=="wind")
	    {
	    	player_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/playerWind.png", 6, 3);
	    	player_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/wind_bulet.png");
	    	player_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/windWall.png", 8, 1);
	    	player_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/windTail.png", 8, 1);   
	    }
	    
	    if(level.equals("1"))
	    {
	    	stone_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/stone.png", 1, 1);
	    	whole_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/whole.png", 4, 3); 
			   
	    	gamebkg_region = new ITextureRegion[2];
		    gamebkg_region[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/dungeon_1.png");
		    gamebkg_region[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/dungeon_1.png");
		    
	    	eye_region = new PlayerRegion();
	    	eye_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Eye.png", 6, 3);	
	    	eye_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	eye_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	eye_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	    	spider_region = new PlayerRegion();	    	
	    	spider_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Spider.png", 6, 3);	
	    	spider_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	spider_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	spider_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
	    	
	    	plant_region = new PlayerRegion();	    	
	    	plant_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Plant.png", 6, 3);	
	    	plant_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/earth_bulet.png");
	    	plant_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	plant_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	    	
	    	human_region = new PlayerRegion();	    	
	    	human_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Human.png", 6, 3);	
	    	human_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/earth_bulet.png");
	    	human_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	human_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
		}
	    else
	    if(level.equals("2"))
	    {
	    	stone_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/stone.png", 1, 1);
		    whole_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/whole.png", 4, 3); 
		   
	    	gamebkg_region = new ITextureRegion[1];
		    gamebkg_region[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/dungeon_1.png");
		    
		    bat_region = new PlayerRegion();
	    	bat_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Bat.png", 6, 3);
	    	bat_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	bat_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	bat_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	    	eye_region = new PlayerRegion();
	    	eye_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Eye.png", 6, 3);	
	    	eye_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	eye_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	eye_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	    	worm_region = new PlayerRegion();	    	
	    	worm_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Worm.png", 6, 3);	
	    	worm_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	worm_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	worm_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	    	spider_region = worm_region;
	   }
	    else
	    if(level.equals("3"))
	    {
	    	stone_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/stone.png", 1, 1);
		    whole_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/river.png", 4, 3); 
		    tree_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/tree.png", 1, 1); 
		    
	    	gamebkg_region = new ITextureRegion[3];
		    gamebkg_region[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/forest_1.png");
		    gamebkg_region[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/forest_2.png");
		    gamebkg_region[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/forest_3.png");
	    	
	    	spider_region = new PlayerRegion();	    	
	    	spider_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Spider.png", 6, 3);	
	    	spider_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	spider_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	spider_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	    	plant_region = new PlayerRegion();	    	
	    	plant_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Plant.png", 6, 3);	
	    	plant_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/earth_bulet.png");
	    	plant_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	plant_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	   }
	    else
	    if(level.equals("5"))
	    {
	    	stone_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/stone.png", 1, 1);
		    whole_region=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "levels/swamp.png", 4, 3); 
		   
	    	gamebkg_region = new ITextureRegion[2];
		    gamebkg_region[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/swamp_1.png");
		    gamebkg_region[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameBackgroundTextureAtlas, activity, "levels/backgrounds/swamp_1.png");
		    
	    	eye_region = new PlayerRegion();
	    	eye_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Eye.png", 6, 3);	
	    	eye_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	eye_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	eye_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 
		
	    	worm_region = new PlayerRegion();	    	
	    	worm_region.mPlayerRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "characters/Worm.png", 6, 3);	
	    	worm_region.mBuletRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "magics/water_bulet.png");
	    	worm_region.mWallRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "magics/waterWall.png", 8, 1);
	    	worm_region.mTailRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/waterTail.png", 8, 1); 

	   }
		    
	    
	    dark_shadow=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/shadow.png", 8, 1);
	    light_shadow=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/shadowLight.png", 8, 1);
	    
	    speedDownBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/speedDownBuf.png", 8, 1);
	    speedUpBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/speedUpBuf.png", 8, 1);
	    healthDownBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/healthDownBuf.png", 8, 1);
	    healthUpBuf=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "effects/healthUpBuf.png", 8, 1);
	    
	    manaLine=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "icons/manaLine.png");
	    healthLine=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "icons/healthLine.png");
	    healthPlusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "icons/healthPlusIcon.png");
		healthMinusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "icons/healthMinusIcon.png");
		speedPlusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "icons/speedPlusIcon.png");
		speedMinusIcon=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameTextureAtlas, activity, "icons/speedMinusIcon.png");
	   	    
	    bulet_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "icons/buleticon.png", 2, 1);
		wall_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "icons/wallicon.png", 2, 1);
		nature_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "icons/natureicon.png", 2, 1);
		buf_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "icons/buficon.png", 2, 1);
		debuf_icon=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas, activity, "icons/debuficon.png", 2, 1);
		 
	    try 
	    {
	        this.mGameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
	        this.mGameTextureAtlas.load();
	    } 
 	    catch (final TextureAtlasBuilderException e)
	    {
	        Debug.e(e);
	    } 	
	    
	    try 
	    {
	        this.mGameBackgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
	        this.mGameBackgroundTextureAtlas.load();
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
    	player_region = null;
    	edge_region= null;
	    river_region=null;
		eye_region=null;
		bat_region=null;
		worm_region=null;
	    gamebkg_region=null;
	    stone_region=null;
	    whole_region=null; 
	    dark_shadow=null;
	    light_shadow=null;
	    speedDownBuf=null;
	    speedUpBuf=null;
	    healthDownBuf=null;
	    healthUpBuf=null;
	    manaLine=null;
	    healthLine=null;
	    healthPlusIcon=null;
		healthMinusIcon=null;
		speedPlusIcon=null;
		speedMinusIcon=null;
	    bulet_icon=null;
		wall_icon=null;
		nature_icon=null;
		buf_icon=null;
		debuf_icon=null;
		
		if(mGameTextureAtlas!=null)
			mGameTextureAtlas.unload();
		
		if(mGameBackgroundTextureAtlas!=null)
			mGameBackgroundTextureAtlas.unload();
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
    	mSplashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashTextureAtlas, activity, "splash.png", 0, 0);
    	mSplashTextureAtlas.load();
    }
    
    public void unloadSplashScreen()
    {
    	mSplashTextureAtlas.unload();
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
    	
    	playermenu_background_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "playerMenuBackground1.png");
    	menu_background_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "menuBackground.png");
    	level_point_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerMenuTextureAtlas, activity, "mapPoint.png");

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
    	fireElementButton = null;
    	waterElementButton = null;
    	earthElementButton = null;
    	windElementButton = null;
    	backButton = null;
    	playermenu_background_region = null;
    	level_point_region = null;
    	menu_background_region=null;
    	
    	if(playerMenuTextureAtlas!=null)
    		this.playerMenuTextureAtlas.unload();
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

