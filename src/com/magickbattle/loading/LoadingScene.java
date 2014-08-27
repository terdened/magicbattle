package com.magickbattle.loading;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.*;

import com.magickbattle.engine.BaseScene;
import com.magickbattle.engine.SceneManager.SceneType;

public class LoadingScene extends BaseScene
{
    @Override
    public void createScene()
    {
        setBackground(new Background(Color.BLACK));
        Text textHolder=new Text(260, 600, resourcesManager.font, "Loading...", vbom);
    	textHolder.setColor(Color.GREEN);
        attachChild(textHolder);
    }

    @Override
    public void onBackKeyPressed()
    {
        return;
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene()
    {

    }
}