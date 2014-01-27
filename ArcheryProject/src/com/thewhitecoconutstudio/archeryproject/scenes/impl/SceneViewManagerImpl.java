/**
 * 
 */
package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.GameActivity;
import com.thewhitecoconutstudio.archeryproject.controllers.MainActionListener;
import com.thewhitecoconutstudio.archeryproject.controllers.MainController;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionActionListener;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionController;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Arrow;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Cross;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Crosshair;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Shelf;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Target;
import com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager;

/**
 * @author paul
 *
 */
public class SceneViewManagerImpl implements SceneViewManager {

	private BaseScene currentScene;
	private BaseScene previousScene;
	private BaseScene futureScene;

	private GameScene gameScene;
	
	private MainActionListener gameController;
	private SessionActionListener sessionController;

	private final VertexBufferObjectManager vbom;
	private final Engine engine;
	private final BoundCamera camera;
	private final GameActivity activity;
	
	/**
	 * Default constructor
	 */
	public SceneViewManagerImpl(MainActionListener gameController, SessionActionListener sessionController, VertexBufferObjectManager vbom, Engine engine, BoundCamera camera, GameActivity activity){
		this.vbom = vbom;
		this.camera = camera;
		this.engine = engine;
		this.activity = activity;
		this.gameController = gameController;
		this.sessionController = sessionController;
		
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager#goToSplashScene()
	 */
	@Override
	public Scene goToSplashSceneThenMenu() {
		//Create the splashScene
		BaseScene splashScene = new SplashScene(
				this.engine,
				this.activity,
				this.vbom,
				this.camera, null, this);

		//GO TO SPLASH SCREEN
		this.goToNextScene(false, splashScene);
		
		 // SLEEP 2 SECONDS HERE ...
		return splashScene;
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager#goToLoadingScene()
	 */
	@Override
	public Scene goToLoadingScene() {
		// TODO Auto-generated method stub

		return null;
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager#goToMenuScene()
	 */
	@Override
	public Scene goToMenuScene() {

		//CREATE MENU
		BaseScene menuScene = new MainMenuScene(this.vbom, this.camera, null, this.activity, this.gameController);

		//LOAD MENU
		this.goToNextScene(false, menuScene);

		return menuScene;
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager#goToGameScene()
	 */
	@Override
	public Scene goToGameScene() {
		//CREATE GAME
		Log.d("SceneViewManager", "goToGameScene");
		GameScene gameScene = new GameScene(this.vbom, this.camera, null, this.activity, sessionController);

		this.gameScene = gameScene;
		//LOAD GAME
		this.goToNextScene(true, gameScene);

		this.sessionController.loadLevel();
		
		return gameScene;
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager#goToOptionScene()
	 */
	@Override
	public Scene goToOptionScene() {
		// TODO Auto-generated method stub

		return null;
	}
	

	private void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
	}


	private void goToNextScene(boolean requiresALoading, BaseScene sceneToLoad){

		//Setting the currentScene to previous
		this.previousScene = currentScene;

		if(requiresALoading){
			//Create the loading scene
			BaseScene loadingScene = new LoadingScene(engine, activity, vbom, camera, null);
			loadingScene.loadSceneResources();
			loadingScene.createScene();
			//Set the loading screen
			this.setScene(loadingScene);
		}

		//Get scene input
		this.futureScene = sceneToLoad;

		//Dispose of previous scene
		if(this.previousScene != null){
			this.previousScene.disposeScene();
		}

		//Load it
		futureScene.loadSceneResources();
		futureScene.createScene();

		if(requiresALoading){
			this.engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
			{
				public void onTimePassed(final TimerHandler pTimerHandler) 
				{
					SceneViewManagerImpl.this.engine.unregisterUpdateHandler(pTimerHandler);

					setScene(SceneViewManagerImpl.this.futureScene);
//					SceneViewManagerImpl.this.futureScene = null;
				}
			}));
		} else {
			setScene(this.futureScene);
//			SceneViewManagerImpl.this.futureScene = null;
		}
	}

	@Override
	public Scene goToResultScreen() {

		//CREATE MENU
		ResultsScene resultScene = new ResultsScene(this.vbom, this.camera, null, this.activity, this.gameController);

		//LOAD MENU
		this.goToNextScene(false, resultScene);

		return resultScene;
	}

	@Override
	public Scene goToRankingScreen() {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public void goToNextLevel() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onCrossCreated(Cross cross) {
		this.gameScene.onCrossCreated(cross);
	}

	@Override
	public void onTargetCreated(Target target) {
		this.gameScene.onTargetCreated(target);
	}

	@Override
	public void onCrosshairCreated(Crosshair crosshair) {
		this.gameScene.onCrosshairCreated(crosshair);
	}

	@Override
	public void onShelfCreated(Shelf shelf) {
		this.gameScene.onShelfCreated(shelf);
	}

	@Override
	public void onArrowCreated(Arrow arrow) {
		this.gameScene.onArrowCreated(arrow);
	}

	@Override
	public void updateScore(int score) {
		this.gameScene.updateScore(score);
	}

	@Override
	public void updateDistance(double distance) {
		this.gameScene.updateDistance(distance);
	}

	@Override
	public void updateArrowNumber(int number) {
		this.gameScene.updateArrowNumber(number);
	}

	@Override
	public void updateTargetNumbre(int number) {
		this.gameScene.updateTargetNumbre(number);
	}

	@Override
	public void updateTime(double time) {
		this.gameScene.updateTime(time);
	}

	@Override
	public void setSessionController(SessionController sessionController) {
		this.sessionController = sessionController;
	}

	@Override
	public void onMovingCrosshair(Crosshair crosshair) {
		this.gameScene.onMovingCrosshair(crosshair);
	}

	@Override
	public void updatePlayer(String pseudo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateXP(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayerLevel(int level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCrossAdded(Cross cross, Target target) {
		this.gameScene.onCrossAdded(cross, target);
	}

	@Override
	public void noMoreArrows() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noMoreTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noMoreTargets() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lastSeconds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void missed() {
		// TODO Auto-generated method stub
		
	}
}
