package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.GameActivity;
import com.thewhitecoconutstudio.archeryproject.scenes.BaseSceneListener;
import com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;

public class SplashScene extends BaseScene{

	private Sprite splash;
	private final SceneViewManager sceneViewManager;
	private Engine engine;
	//Graphics
	private final static String SPLASH_LOGO = "thewhitecoconutstudio";

	public SplashScene(Engine engine, GameActivity activity, VertexBufferObjectManager vbom, BoundCamera camera, BaseSceneListener listener, SceneViewManager sceneViewManager){
		super(vbom, camera, listener);
		this.sceneViewManager = sceneViewManager;
		this.engine = engine;
		this.resources = new SceneResources(SplashScene.class.getSimpleName(), 512, activity);
		this.resources.createGraphics(SPLASH_LOGO);
		
	}

	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));
		splash = new Sprite(0,0, this.resources.getTextureRegion(SPLASH_LOGO), vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) 
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		splash.setScale(1.5f);
		splash.setPosition(GameValues.getInstance().getCenterX(), GameValues.getInstance().getCenterY());
		attachChild(splash);
		Log.d("TOTO", "Start waiting");
//		TimerHandler my_timer = new TimerHandler(5, false,
//			    new ITimerCallback() {
//			        @Override
//			        public void onTimePassed(final TimerHandler pTimerHandler) {
//			        	Log.d("TOTO", "Go gogoogogogoogogogog");
//			            SplashScene.this.sceneViewManager.goToMenuScene();
//			        }
//			});
//			this.engine.registerUpdateHandler(my_timer);
			
//		this.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
//		    {
//		        public void onTimePassed(final TimerHandler pTimerHandler) 
//		        {
//		        	SplashScene.this.unregisterUpdateHandler(pTimerHandler);
////		            SceneManager.getInstance().createMenuScene();
//		        	Log.d("TOTO", "Go gogoogogogoogogogog");
//		            SplashScene.this.sceneViewManager.goToMenuScene();
//		        }
//		    }));
		
		this.sceneViewManager.goToMenuScene();
		if(this.listener !=null){
			this.listener.onSceneCreated();
		}
	}

	@Override
	public void onBackKeyPressed(){

	}

	@Override
	public void disposeScene(){
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

}