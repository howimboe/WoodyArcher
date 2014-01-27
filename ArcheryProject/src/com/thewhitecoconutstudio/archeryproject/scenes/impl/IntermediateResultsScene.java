package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.NineSliceSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.GameActivity;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionActionListener;
import com.thewhitecoconutstudio.archeryproject.scenes.BaseSceneListener;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;

public class IntermediateResultsScene extends BaseScene {

	//Graphics
	private final static String HUD_LEFT_TEXT_BACKGROUND = "hud_1";
	private final static String HUD_FONT = "font_hud";

	//Controller
	private final SessionActionListener gameController;

	//Scene
	private final GameScene scene;

	//Target
	private final Sprite target;
	private IEntity target_parent;
	
	
	public IntermediateResultsScene(Engine engine, GameActivity activity, VertexBufferObjectManager vbom, BoundCamera camera, BaseSceneListener listener, SessionActionListener gameController, GameScene scene, Sprite target){
		super(vbom, camera, listener);
		this.resources = new SceneResources(IntermediateResultsScene.class.getSimpleName(), 1024, activity);
		this.resources.createGraphics(HUD_LEFT_TEXT_BACKGROUND);
		this.resources.createFonts(HUD_FONT);
		this.scene = scene;
		this.gameController = gameController;
		this.target = target;
		//Attach touch listener
				this.setOnAreaTouchListener(new IOnAreaTouchListener() {
					
					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							ITouchArea pTouchArea, float pTouchAreaLocalX,
							float pTouchAreaLocalY) {
						Log.d("DETACH","DETACH §!!!!");
						IntermediateResultsScene.this.closeAll();
						return false;
					}
				}); 
	}

	private void closeAll() {
		IntermediateResultsScene.this.target.detachSelf();
		IntermediateResultsScene.this.target.setScale(0.5f);
		IntermediateResultsScene.this.target_parent.attachChild(IntermediateResultsScene.this.target);
//		IntermediateResultsScene.this.reset();
//		IntermediateResultsScene.this.dispose();
//		();
		
		IntermediateResultsScene.this.clearChildScene();
		IntermediateResultsScene.this.scene.buildController();
		this.resources.unloadResource();
	}
	
	@Override
	public void createScene() {
		creatSubScene();
		this.listener.onSceneCreated();
		
//		this.engine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() 
//				{
//					public void onTimePassed(final TimerHandler pTimerHandler) 
//					{
//						IntermediateResultsScene.this.engine.unregisterUpdateHandler(pTimerHandler);
//						
////						setScene(IntermediateResultsScene.this.futureScene);
//						IntermediateResultsScene.this.closeAll();
//					}
//				}));
		
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	private void creatSubScene() {
		//Load resources 
		this.resources.loadResources();
		
		//Remove the controller
		this.scene.deleteController();
		
		//Add to parent scene
		this.scene.setChildScene(this);
		this.setPosition(0,0);
		this.setBackgroundEnabled(false);
		//Fond

		float[] xy = this.scene.convertSceneCoordinatesToLocalCoordinates(0,0);
		
		NineSliceSprite nice = new NineSliceSprite(
				GameValues.getInstance().getCenterX(),
				GameValues.getInstance().getCenterY(),
				GameValues.getInstance().getWidth()/2,
				GameValues.getInstance().getHeight()/2,
				this.resources.getTextureRegion(HUD_LEFT_TEXT_BACKGROUND),9,9,9,9,vbom);
		this.attachChild(nice);

		//Target
		this.target_parent = target.getParent();
		this.target.detachSelf();
		this.attachChild(target);
		this.target.setScale(1.1f);

		//Points
		Text scoreText = new Text(
				GameValues.getInstance().getCenterX(),
				GameValues.getInstance().getCenterY() - 100,
				this.resources.getFont(HUD_FONT), "Score: .0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 0); 
		scoreText.setColor(Color.BLACK);
		scoreText.setText("Score: 00000");
		this.attachChild(scoreText);
		
		
	}

	//	@Override
	//	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
	//		switch(pMenuItem.getID()) {
	//		case MENU_PLAY:
	//			//Load Game Scene!
	//			this.gameController.launchNewSession(null);
	//			return true;
	//		case MENU_OPTIONS:
	//			this.gameController.launchOptions(null);
	//			return true;
	//		default:
	//			return false;
	//		}
	//	}

}
