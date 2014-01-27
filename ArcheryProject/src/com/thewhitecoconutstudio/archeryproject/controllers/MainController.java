package com.thewhitecoconutstudio.archeryproject.controllers;

import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;

import com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager;


/**
 * GameController is the main handler for the app.
 * 
 * 
 * @author Paul
 *
 */
public interface MainController extends MainActionListener{

	/**
	 * Called when the app need to start
	 */
	public void startingApp(OnCreateSceneCallback pOnCreateSceneCallback);

	/**
	 * Called when menu is requested
	 */
	public void menuScreen(OnPopulateSceneCallback pOnCreateSceneCallback);
	
	public void setSceneViewManager(SceneViewManager sceneViewManager);
}
