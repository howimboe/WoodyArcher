package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.NineSliceSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;

import android.opengl.GLES20;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.thewhitecoconutstudio.archeryproject.GameActivity;
import com.thewhitecoconutstudio.archeryproject.controllers.GameOverCauses;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionActionListener;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionController;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Arrow;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Cross;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Crosshair;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.GenericObject;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Shelf;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Target;
import com.thewhitecoconutstudio.archeryproject.models.listeners.HUDModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.models.listeners.MainModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.models.listeners.SessionModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.models.listeners.TrajectoryListener;
import com.thewhitecoconutstudio.archeryproject.scenes.BaseSceneListener;
import com.thewhitecoconutstudio.archeryproject.tools.CustomTouchListener;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;
import com.thewhitecoconutstudio.archeryproject.tools.MyController;


/**
 * Main game scene
 * 
 * @author Paul
 *
 */
public class GameScene extends BaseScene implements IOnSceneTouchListener, CustomTouchListener, TrajectoryListener , MainModelChangeListener, SessionModelChangeListener, HUDModelChangeListener {

	private static String DEBUG_TAG = GameScene.class.getSimpleName();

	//My MVC
	private SessionController sessionController;
	//	private SessionModel worldObjectModel;
	//	private HUDController hudController;
	//	private HUDModel hudObjectModel;

	//HUD
	private HUD gameHUD;
	private Text scoreText;
	private Text arrowText;
	private Text timeText;
	private Text distanceText;

	private CustomTimerCallback customTimerCallback;

	private PhysicsWorld physicsWorld;

	private static float GRAVITY = -4f;
	private boolean noMoreArrow = false;
	private boolean isGameOver = false;

	//Pause
	CameraScene mPauseScene;
	boolean isPaused = false;
	
	//Graphics
	private final static String MAIN_BACKGROUND = "bg_bg";
	private final static String CONTROLLER_BASE = "knob_fond";
	private final static String CONTROLLER_KNOB = "knob_stick";
	private final static String HUD_LEFT_TEXT_BACKGROUND = "hud1";
	private final static String HUD_CENTER_TEXT_BACKGROUND = "hud2";
	private final static String LEVEL_COMPLETED_WINDOW = "levelComplete";
	private final static String STARS = "stars";
	private final static String TARGET_CARTON = "carton";
	private final static String TARGET_EXTERN = "cible_externe";
	private final static String TARGET_MIDDLE = "cible_moyenne";
	private final static String TARGET_INTERN = "cible_interne";
	private final static String CROSS1 = "croix";
	private final static String CROSS2 = "croix2";
	private final static String CROSS3 = "croix3";
	private final static String CROSSHAIR = "crosshair";
	private final static String ARROW = "ball";
	private final static String SHELF = "shelf";
	private final static String TARGET = "target";

	private final GameActivity activity;
	private final SessionActionListener sessionActionListener;
	//Fonts
	private final static String HUD_FONT = "Pacifico";

	//Kasuki Hand Normal

	public GameScene(VertexBufferObjectManager vbom, BoundCamera camera, BaseSceneListener listener, GameActivity activity, SessionActionListener sessionActionListener){
		super(vbom, camera, listener);
		this.activity = activity;
		this.sessionActionListener = sessionActionListener;
		resources = new SceneResources(GameScene.class.getSimpleName(), 2048, (GameActivity) activity);
		resources.createGraphics(
				MAIN_BACKGROUND,
				CONTROLLER_BASE, CONTROLLER_KNOB,
				HUD_LEFT_TEXT_BACKGROUND, HUD_CENTER_TEXT_BACKGROUND,
				LEVEL_COMPLETED_WINDOW,
				TARGET_CARTON, TARGET_EXTERN, TARGET_MIDDLE, TARGET_INTERN,TARGET,
				CROSS1, CROSS2, CROSS3, CROSSHAIR, SHELF
				);
		resources.addTiledGraphics(STARS, 2, 1);
		resources.addTiledGraphics(ARROW, 2, 1);

		resources.createFonts(HUD_FONT);

		//		this.worldObjectModel = new SessionModelImpl((ModelChangeListener) this);

		//		this.hudObjectModel = new HUDModelImpl(this);
		//		this.hudObjectModel.setHUDUpdater(this);


		//		this.hudController = new HUDControllerImpl(hudObjectModel, this.gameController);
	}

	@Override
	public void createScene() {
		Log.d(DEBUG_TAG, "Creating scene ...");
		createBackground();
		createHUD();
		createPhysics();
		buildController();
		setOnSceneTouchListener(this); 
		//		this.loadLevel();
		//		levelCompleteWindow = new ShootCompletedWindow(vbom, this.worldObjectModel, this.hudObjectModel, this.resources);

		//		for(int i= 150; i < 450; i = i + 4){
		//			for(int j= 350; j < 650; j = j + 4){
		//				GameScene.this.gameController.simulateShoot(i, j, GameScene.this.physicsWorld, GameScene.this.vbom);
		//			}
		//		}
		this.listener.onSceneCreated();

		Log.d(DEBUG_TAG, "Done Creating scene ...");
	}

	//	private void loadLevel(){
	//		Map<String, Integer> targetsNumberForEachType = new HashMap<String, Integer>();
	//		Target target = this.worldObjectModel.createATarget(280, 600,"toto");
	//		targetsNumberForEachType.put("TYPE1", 1);
	//		this.attachChild(target);
	//		Shelf shelf = this.worldObjectModel.createAShelf(250, 200);
	//		this.attachChild(shelf);
	//		this.hudObjectModel.setTargetNumber(targetsNumberForEachType);
	//	}

	@Override
	public void onBackKeyPressed()
	{
		//		SceneManager.getInstance().loadMenuScene(engine);
	}

	public void buildController(){
		final MyController analogOnScreenControl = new MyController(
				GameValues.getInstance().getControllerX(), GameValues.getInstance().getControllerY(),
				this.camera, 
				this.resources.getTextureRegion(CONTROLLER_BASE),
				this.resources.getTextureRegion(CONTROLLER_KNOB),
				0.1f, 200, this.vbom,this);


		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		analogOnScreenControl.getControlBase().setAlpha(0.1f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 0);
		analogOnScreenControl.getControlBase().setScale(0.5f);
		analogOnScreenControl.getControlKnob().setScale(1f);
//		analogOnScreenControl.getControlKnob().setAlpha(0.1f);
		analogOnScreenControl.refreshControlKnobPosition();
		this.setChildScene(analogOnScreenControl);
	}

	public void deleteController(){
		this.clearChildScene();
	}

	@Override
	public void disposeScene() {
		camera.setHUD(null);
		camera.setChaseEntity(null);
		super.disposeScene();

	}

	private void createBackground(){

		Sprite bg = new Sprite(GameValues.getInstance().getCenterX(), GameValues.getInstance().getCenterY(), this.resources.getTextureRegion(MAIN_BACKGROUND), vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) 
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		

		bg.setWidth(GameValues.getInstance().getWidth());
		bg.setHeight(GameValues.getInstance().getHeight());
		
		attachChild(bg);

	}

	private void createHUD1background(Text text){
		NineSliceSprite nice = new NineSliceSprite(
				(text.getWidth() + 10) / 2 + text.getX(),
				text.getY() + text.getHeight()/2,
				text.getWidth() + 100,
				text.getHeight() + 80,
				this.resources.getTextureRegion(HUD_LEFT_TEXT_BACKGROUND),9,9,9,9,vbom);
		this.attachChild(nice);
	}

	private void createHUD2background(Text text){
		NineSliceSprite nice = new NineSliceSprite(
				(text.getWidth() + 10) / 2 + text.getX(),
				text.getY() + text.getHeight()/2,
				text.getWidth() + 100,
				text.getHeight() + 100,
				this.resources.getTextureRegion(HUD_CENTER_TEXT_BACKGROUND),9,9,9,9,vbom);
//		Sprite spriite = new Sprite(text.getX(), text.getY(), this.resources.getTextureRegion(HUD_CENTER_TEXT_BACKGROUND), vbom);
//		spriite.setScale(0.3f);
		this.attachChild(nice);
	}

	private void createHUD(){
		gameHUD = new HUD();

		//Points
		scoreText = new Text(10, GameValues.getInstance().getHeight() - 100, this.resources.getFont(HUD_FONT), "Score: .0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 0); 
		scoreText.setText("Score: 00000");
		this.createHUD1background(scoreText);
		gameHUD.attachChild(scoreText);

		//Arrows
		arrowText = new Text(10, GameValues.getInstance().getHeight() - 250, this.resources.getFont(HUD_FONT), "Arrow: /.0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		arrowText.setAnchorCenter(0, 0); 
		arrowText.setText("Arrow : 0/10");
		this.createHUD1background(arrowText);
		gameHUD.attachChild(arrowText);

		//Time
		timeText = new Text(GameValues.getInstance().getWidth()-100, GameValues.getInstance().getHeight()-100, this.resources.getFont(HUD_FONT), "Time: .0123456789Sec", new TextOptions(HorizontalAlign.LEFT), vbom);
		timeText.setAnchorCenter(0, 0); 
		timeText.setTextOptions(new TextOptions(HorizontalAlign.CENTER));
//		timeText.setScale(2f);
		timeText.setText("15 \n sec.");
		this.createHUD2background(timeText);
		gameHUD.attachChild(timeText);

		//Distance
		distanceText = new Text(10, GameValues.getInstance().getHeight() - 400, this.resources.getFont(HUD_FONT), "Distance: .0123456789m", new TextOptions(HorizontalAlign.LEFT), vbom);
		distanceText.setAnchorCenter(0, 0); 
		distanceText.setText("Distance: 0m");
		this.createHUD1background(distanceText);
		gameHUD.attachChild(distanceText);

		camera.setHUD(gameHUD);
	}

	private void createPhysics(){
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0.0f, GRAVITY), false); 
		registerUpdateHandler(physicsWorld);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)	{
		return false;
	}

	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl, float pValueX, float pValueY) {
		if(pValueX != 0 && pValueY != 0){ 
			this.sessionActionListener.moveCrosshair(1.5f*pValueX, 1.5f*pValueY);
		}
	}

	@Override
	public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTouch() {
		this.sessionActionListener.startAiming();

		if(this.customTimerCallback == null){
			this.customTimerCallback = new CustomTimerCallback(this, this.sessionActionListener);
			this.registerUpdateHandler(new TimerHandler(1,true,this.customTimerCallback));
		}

	}

	protected void setTimeText(String text){
		this.timeText.setText(text);
	}
	@Override
	public void onStopTouch() {
		this.sessionActionListener.startShooting();
		initPauseScene();
//		if(this.customTimerCallback != null){
//			this.customTimerCallback.reset();
//		}
	}

	@Override
	public void onControlClick(MyController pAnalogOnScreenControl) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param genericObject
	 */
	@Override
	public void onEnd(GenericObject genericObject) {
		this.sessionActionListener.stopCourse((Arrow) genericObject);
	}

	@Override
	public void updateTime(double time) {
		this.timeText.setText("Time : " + time + " s");
	}

	@Override
	public void updateArrowNumber(int nb) {
		this.arrowText.setText("Arrow : " + nb + "/10");
		if(nb == 0){
			noMoreArrow = true;
		}
	}

	public void endOfGame(){
		if(!isGameOver){
			Sprite sprite = new Sprite(10, 10, this.resources.getTextureRegion(LEVEL_COMPLETED_WINDOW), vbom)
			{
				boolean isActivated = false;

				@Override
				protected void onManagedUpdate(float pSecondsElapsed) 
				{
					super.onManagedUpdate(pSecondsElapsed);
					if(!isActivated){
						isActivated = true;
						//												GameScene.this.levelCompleteWindow.display(0,0,0,0, GameScene.this, camera);
						//						this.setVisible(false);
					}
				}
			};
			sprite.setScale(0.5f);
			this.attachChild(sprite);
			isGameOver = true;
		}

		//	    
		//	    this.attachChild(sprite);
		//		SceneManager.getInstance().createResultScene();
		//		SceneManager.getInstance().loadResultScene(engine);
	}

	@Override
	public void updateScore(int score) {
		this.scoreText.setText(score + "");
	}

	@Override
	public void updateDistance(double distance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTargetNumbre(int number) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCrossCreated(Cross cross) {
		cross.createPhysics(physicsWorld, this.resources.getTextureRegion(this.CROSS1), vbom);
		this.attachChild(cross.getSprite());
	}

	@Override
	public void onTargetCreated(Target target) {
		Log.d("GameScene", "onTargetCreated : "  + target);
		target.createPhysics(physicsWorld, this.resources.getTextureRegion(this.TARGET), vbom);
		this.attachChild(target.getSprite());
	}

	@Override
	public void onCrosshairCreated(Crosshair crosshair) {
		crosshair.createPhysics(physicsWorld, this.resources.getTextureRegion(this.CROSSHAIR), vbom);
		this.attachChild(crosshair.getSprite());
	}

	@Override
	public void onShelfCreated(Shelf shelf) {
		shelf.createPhysics(physicsWorld, this.resources.getTextureRegion(this.SHELF), vbom);
		this.attachChild(shelf.getSprite());
	}

	@Override
	public void onArrowCreated(Arrow arrow) {
		arrow.createPhysics(physicsWorld, this.resources.getTiledTextureRegion(this.ARROW), vbom);
		this.attachChild(arrow.getSprite());
	}

	@Override
	public void onMovingCrosshair(Crosshair crosshair) {
		crosshair.move(crosshair.getvx(), crosshair.getvy());
	}

	@Override
	public void updatePlayer(String pseudo) {
		//		this.
	}

	@Override
	public void updateXP(int value) {
		this.scoreText.setText(value +" xp");
	}

	@Override
	public void updatePlayerLevel(int level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCrossAdded(Cross cross, Target target) {
		cross.createPhysics(physicsWorld, this.resources.getTextureRegion(this.CROSS1), vbom);
		cross.getSprite().setX(target.getSprite().convertSceneCoordinatesToLocalCoordinates(cross.getX(), cross.getY())[0]);
		cross.getSprite().setY(target.getSprite().convertSceneCoordinatesToLocalCoordinates(cross.getX(), cross.getY())[1]);
		target.getSprite().attachChild(cross.getSprite());
	}

	@Override
	public void noMoreArrows() {
		this.sessionActionListener.gameOver(GameOverCauses.NO_MORE_ARROW);
	}

	@Override
	public void noMoreTime() {
		this.sessionActionListener.gameOver(GameOverCauses.NO_MORE_TIME);
	}

	@Override
	public void noMoreTargets() {
		this.sessionActionListener.success();
	}

	@Override
	public void lastSeconds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void missed() {
		// TODO Auto-generated method stub
		
	}
	
	// PAUSE
	  private void initPauseScene() {
          this.mPauseScene = new CameraScene(this.camera);
         
          /* Make the 'PAUSED'-label centered on the camera. */
          final float centerX = GameValues.getInstance().getCenterX();
          final float centerY = GameValues.getInstance().getCenterY();
          final Sprite pausedSprite = createPauseSprite(centerX, centerY);
          this.mPauseScene.registerTouchArea(pausedSprite);
          this.mPauseScene.attachChild(pausedSprite);
         
          /* Makes the paused Game look through. */
          this.mPauseScene.setBackgroundEnabled(false);
  }
 
  /**
   * Creates a Sprite that manages Pausing
   * @param pX X Position to be created at
   * @param pY Y Position to be created at
   * @return
   */
  private Sprite createPauseSprite(float pX, float pY) {
          final Sprite pauseButton = new Sprite(pX, pY,
                          resources.getTiledTextureRegion(STARS),
                          activity.getVertexBufferObjectManager()) {

                  @Override
                  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
                                  final float pTouchAreaLocalX, final float pTouchAreaLocalY) {                          
                          // Toggle pause or not                         
                          switch(pSceneTouchEvent.getAction()) {
                                  case TouchEvent.ACTION_DOWN:
                                          if(isPaused) {
                                                  isPaused = false;
                                                  GameScene.this.clearChildScene();
                                                  GameScene.this.setIgnoreUpdate(false);
                                          }
                                          else {
                                                  isPaused = true;
                                                  GameScene.this.setIgnoreUpdate(true);
                                                  GameScene.this.setChildScene(mPauseScene, false, true, true);
                                                  Log.d("paused", "done");
                                          }
                                          break;
                                  case TouchEvent.ACTION_MOVE:
                                          break;
                                  case TouchEvent.ACTION_UP:
                                          break;
                          }
                          return true;
                  }
          };
          return pauseButton;
  }
	// END PAUSE
	

}