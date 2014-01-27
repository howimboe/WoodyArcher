package com.thewhitecoconutstudio.archeryproject.scenes;

import org.andengine.entity.scene.Scene;

public interface ViewChangeRequester {

	public Scene goToSplashSceneThenMenu();
	
	public Scene goToLoadingScene();
	
	public Scene goToMenuScene();
	
	public Scene goToGameScene();
	
	public Scene goToOptionScene();
	
	public Scene goToResultScreen();
	
	public Scene goToRankingScreen();
	
	public void goToNextLevel();
}
