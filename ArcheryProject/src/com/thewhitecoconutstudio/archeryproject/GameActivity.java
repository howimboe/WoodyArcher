package com.thewhitecoconutstudio.archeryproject;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.LayoutGameActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import com.thewhitecoconutstudio.archeryproject.controllers.MainActionListener;
import com.thewhitecoconutstudio.archeryproject.controllers.MainController;
import com.thewhitecoconutstudio.archeryproject.controllers.impl.MainControllerImpl;
import com.thewhitecoconutstudio.archeryproject.models.MainModel;
import com.thewhitecoconutstudio.archeryproject.models.impl.MainModelImpl;
import com.thewhitecoconutstudio.archeryproject.models.listeners.MainModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.scenes.SceneViewManager;
import com.thewhitecoconutstudio.archeryproject.scenes.impl.SceneViewManagerImpl;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;


public class GameActivity extends LayoutGameActivity {

	private BoundCamera camera;

//	private String[] mPlanetTitles = {"Cars","Trucks","Tacos"};
//	private DrawerLayout mDrawerLayout;
//	private ListView mDrawerList;

	//Model
	//	private final HUDObjectModel HUDModel;
	//	private final WorldObjectModel worldObjectModel;

	//Controller
	//	private final HUDController HUDController;
	private MainController mainController;


		public GameActivity(){
			super();



			//		//View instances
			//		this.sceneManager = SceneManager.getInstance();		

			//Model instances
			//		this.HUDModel = new HUDObjectModelImpl();
			//		this.worldObjectModel = new WorldObjectModelImpl();

			//Controller instances
			//		this.HUDController = new HUDControllerImpl(HUDModel);

		}

		private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("GameActivity", "Uncaught exception is: ", ex);
				System.exit(0);
			}
		};

		@Override
		public EngineOptions onCreateEngineOptions(){
			Log.d("ACTIVITY", "onCreateEngineOptions");
			//Set default exception handler to avoid bad reboot
			Thread.setDefaultUncaughtExceptionHandler(handler);

			//Get the metrics to adapt display
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			GameValues values = GameValues.getInstance();
			values.setHeight(metrics.widthPixels);
			values.setWidth(metrics.heightPixels);

			camera = new BoundCamera(0, 0, GameValues.getInstance().getWidth(), GameValues.getInstance().getHeight());

			EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(GameValues.getInstance().getWidth(), GameValues.getInstance().getHeight()), this.camera);

			//No sound
			engineOptions.getAudioOptions().setNeedsMusic(false).setNeedsSound(false);

			//Wake
			engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

			return engineOptions;
		}

		@Override
		public Engine onCreateEngine(EngineOptions pEngineOptions) {
			Log.d("ACTIVITY", "onCreateEngine");
			Engine engine = new LimitedFPSEngine(pEngineOptions, 60);
			return engine;
		}

		public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {

			Log.d("ACTIVITY", "onCreateResources");

			//MainModel
			MainModel mainModel = new MainModelImpl();

			//MainController
			this.mainController = new MainControllerImpl();


			//SceneViewManager
			SceneViewManager svm = new SceneViewManagerImpl((MainActionListener)mainController,null,this.getVertexBufferObjectManager(),this.getEngine(),this.camera,this);

			mainModel.setMainModelChangeListener((MainModelChangeListener) svm);

			this.mainController.setSceneViewManager(svm);

			//		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
			//		setResourcesManager(ResourcesManager.getInstance());
			//NOTHING TODO?
			pOnCreateResourcesCallback.onCreateResourcesFinished();
		}

		public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
			this.mainController.startingApp(pOnCreateSceneCallback);
		}

		public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {

			Log.d("ACTIVITY", "onPopulateScene");

			//		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
			//		{
			//			public void onTimePassed(final TimerHandler pTimerHandler) 
			//			{
			//				mEngine.unregisterUpdateHandler(pTimerHandler);
			//				GameActivity.this.mainController.menuScreen(null);
			//			}
			//		}));
			pOnPopulateSceneCallback.onPopulateSceneFinished();
		}

		@Override
		public void onDestroy()
		{
			super.onDestroy();
			System.exit(0);	
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) 
		{  
			if (keyCode == KeyEvent.KEYCODE_BACK)
			{
				//TODO
				//			this.gameController..getCurrentScene().onBackKeyPressed();
			}
			return false; 
		}

		@Override
		protected int getLayoutID() {
			// TODO Auto-generated method stub
			return R.layout.activity_main;
		}

		@Override
		protected int getRenderSurfaceViewID() {
			// TODO Auto-generated method stub
			return R.id.layout_render;
		}

	}

