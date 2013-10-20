package com.example.gameactive;

import java.io.IOException;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.*;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.engine.*;

import android.view.KeyEvent;


public class GameActivity extends BaseGameActivity
{

	private BoundCamera camera;
	private ResourcesManager resourcesManager;
	
	
	@Override
	protected void onDestroy()
	{
	    super.onDestroy();
	    System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}

	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	
	public EngineOptions onCreateEngineOptions()
	{
		camera = new BoundCamera(0, 0, 720, 1280);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(800, 1280), this.camera);
	    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    return engineOptions;
	}
	

	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{
	    ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
	    resourcesManager = ResourcesManager.getInstance();
	    pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {
    	SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
    }

    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {
    	 mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
    	    {
    	        public void onTimePassed(final TimerHandler pTimerHandler) 
    	        {
    	            mEngine.unregisterUpdateHandler(pTimerHandler);
    	            SceneManager.getInstance().createMenuScene();
    	        }
    	    }));
    	    pOnPopulateSceneCallback.onPopulateSceneFinished();
    }   

}