package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import com.thewhitecoconutstudio.archeryproject.GameActivity;
import com.thewhitecoconutstudio.archeryproject.scenes.BaseSceneListener;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;

public class LoadingScene extends BaseScene {

	//Fonts
	private final static String LOADING_FONT = "font";

	public LoadingScene(Engine engine, GameActivity activity, VertexBufferObjectManager vbom, BoundCamera camera, BaseSceneListener listener){
		super(vbom, camera, listener);
		this.resources = new SceneResources(LoadingScene.class.getSimpleName(), 1024, activity);
		this.resources.createFonts(LOADING_FONT);
		

	}
	@Override
	public void createScene(){
		setBackground(new Background(Color.WHITE));
		attachChild(new Text(GameValues.getInstance().getCenterX(), GameValues.getInstance().getCenterY(), this.resources.getFont(LOADING_FONT), "Loading...", vbom));
		this.listener.onSceneCreated();
	}

	@Override
	public void onBackKeyPressed() {
		return;
	}

}