package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.GameActivity;
import com.thewhitecoconutstudio.archeryproject.controllers.MainActionListener;
import com.thewhitecoconutstudio.archeryproject.scenes.BaseSceneListener;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;

public class ResultsScene extends BaseScene implements IOnMenuItemClickListener{

	private MenuScene menuChildScene;
	private final int BACK_MENU = 0;

	//Graphics
	private final static String BOOTSCREEN = "bootscreen";
	private final static String PLAY = "play";

	//Fonts
	private final static String HUD_FONT = "font_hud";
		
	//Text
	private Text scoreText;
	private Text nbTarget;
	
	//Controller
	private final MainActionListener gameController;
	
	public ResultsScene(VertexBufferObjectManager vbom, BoundCamera camera, BaseSceneListener listener, GameActivity activity,MainActionListener gameController){
		super(vbom, camera, listener);
		this.resources = new SceneResources(ResultsScene.class.getSimpleName(), 1024, activity);
		this.resources.createGraphics(BOOTSCREEN,PLAY);
		this.resources.createFonts(HUD_FONT);
		this.gameController = gameController;
	}

	@Override
	public void createScene() {
		Log.d("TOTO", "setTheListener");
		this.setOnAreaTouchListener(new IOnAreaTouchListener() {
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					ITouchArea pTouchArea, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				Log.d("TOUCH","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
				return false;
			}
		});
		createBackground();
		createMenuChildScene();
		
		scoreText = new Text(GameValues.getInstance().getCenterX(), GameValues.getInstance().getHeight() - 300, this.resources.getFont(HUD_FONT), "Arrow: /.0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 0); 
		scoreText.setText("Score : " + 5000);
		this.attachChild(scoreText);
		
		nbTarget = new Text(GameValues.getInstance().getCenterX(), GameValues.getInstance().getHeight() - 400, this.resources.getFont(HUD_FONT), "Arrow: /.0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		nbTarget.setAnchorCenter(0, 0); 
		nbTarget.setText("Nb Targets : " + 5 + "/10");
		this.attachChild(nbTarget);
		
//		this.listener.onSceneCreated();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	private void createBackground() {
//		attachChild(new Sprite(GameValues.getInstance().getCenterX(), GameValues.getInstance().getCenterY(), this.resources.getTextureRegion(BOOTSCREEN), vbom)
//		{
//			@Override
//			protected void preDraw(GLState pGLState, Camera pCamera) 
//			{
//				super.preDraw(pGLState, pCamera);
//				pGLState.enableDither();
//			}
//		});
		Sprite bg = new Sprite(GameValues.getInstance().getCenterX(), GameValues.getInstance().getCenterY(), this.resources.getTextureRegion(BOOTSCREEN), vbom)
		{
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

	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(GameValues.getInstance().getCenterX(), GameValues.getInstance().getCenterY());
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(BACK_MENU, this.resources.getTextureRegion(PLAY), vbom), 1.2f, 1);

		menuChildScene.addMenuItem(playMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(0, -150);

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		Log.d("MainMenu", "Click");
		switch(pMenuItem.getID()) {
		case BACK_MENU:
			this.gameController.goBackToMenu();
			return true;
		default:
			return false;
		}
	}

}
