/**
 * 
 */
package com.thewhitecoconutstudio.archeryproject.controllers.impl;

import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;

import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.controllers.MainController;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionController;
import com.thewhitecoconutstudio.archeryproject.models.HUDModelUpdater;
import com.thewhitecoconutstudio.archeryproject.models.SessionModel;
import com.thewhitecoconutstudio.archeryproject.models.impl.SessionModelImpl;
import com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager;
import com.thewhitecoconutstudio.archeryproject.scenes.ViewChangeRequester;

/**
 * @author Paul
 *
 */
public class MainControllerImpl implements MainController{

	private final static String DEBUG_TAG = "MAIN_CONTROLLER";
	
	//Unused for now
//	private final ModelChangeListener modelChangeListener;
	private ViewChangeRequester viewChangeRequester;
	
	private SceneViewManager sceneViewManager;
	
	public MainControllerImpl(){
//		this.modelChangeListener = modelChangeListener;
		this.viewChangeRequester = sceneViewManager;
	}
	
	public void setSceneViewManager(SceneViewManager sceneViewManager){
		this.sceneViewManager = sceneViewManager;
		this.viewChangeRequester = sceneViewManager;
	}

	@Override
	public void startingApp(OnCreateSceneCallback pOnCreateSceneCallback) {
		Log.d(DEBUG_TAG, "startingApp");
//		this.viewChangeRequester.goToSplashSceneThenMenu();
		pOnCreateSceneCallback.onCreateSceneFinished(this.viewChangeRequester.goToMenuScene());
		

	}

	@Override
	public void menuScreen(OnPopulateSceneCallback pOnCreateSceneCallback) {
		Log.d(DEBUG_TAG, "menuScreen");
		this.viewChangeRequester.goToMenuScene();
		pOnCreateSceneCallback.onPopulateSceneFinished();
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.controllers.GameController#launchNewSession()
	 */
	@Override
	public void launchNewSession(OnCreateSceneCallback pOnCreateSceneCallback) {
		Log.d(DEBUG_TAG, "launchNewSession");
		SessionModel sessionModel = new SessionModelImpl();
		sessionModel.setSessionModelChangeListener(this.sceneViewManager);
		sessionModel.setHUDModelChangeListener(this.sceneViewManager);
		
		SessionController sessionController = new SessionControllerImpl(sessionModel, (HUDModelUpdater) sessionModel, this.viewChangeRequester);
		
		this.sceneViewManager.setSessionController(sessionController);
		
		this.viewChangeRequester.goToGameScene();
		
	}

	@Override
	public void launchOptions(OnCreateSceneCallback pOnCreateSceneCallback) {
		Log.d(DEBUG_TAG, "launchOptions");
		this.viewChangeRequester.goToOptionScene();
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "goBack");
	}

	@Override
	public void rankings(OnCreateSceneCallback pOnCreateSceneCallback) {
		Log.d(DEBUG_TAG, "rankings");
		this.viewChangeRequester.goToRankingScreen();
	}

	@Override
	public void goBackToMenu() {
		this.viewChangeRequester.goToMenuScene();
	}

	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------
}
