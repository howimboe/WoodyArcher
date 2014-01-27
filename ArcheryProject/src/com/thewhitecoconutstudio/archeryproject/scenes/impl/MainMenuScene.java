package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.NineSliceSprite;
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

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{

	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;

	//Graphics
	private final static String BOOTSCREEN = "bg_bg";
	private final static String PLAY = "play";
	private final static String MAIN_MENU_BG = "main_menu";
	private final static String OPTIONS = "options";
//	private final static String HUD_LEFT_TEXT_BACKGROUND = "hud_1";

	//Fonts
	private final static String HUD_FONT = "font_hud";
	
	//Controller
	private final MainActionListener gameController;
	
	public MainMenuScene(VertexBufferObjectManager vbom, BoundCamera camera, BaseSceneListener listener, GameActivity activity,MainActionListener gameController){
		super(vbom, camera, listener);
		this.resources = new SceneResources(MainMenuScene.class.getSimpleName(), 1024, activity);
		this.resources2 = new SceneResources(MainMenuScene.class.getSimpleName(), 1024, activity);
		this.resources.createGraphics(BOOTSCREEN,PLAY,OPTIONS);
		this.resources2.createGraphics(MAIN_MENU_BG);
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
		
//		this.listener.onSceneCreated();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}
	
//	private IEntity createHUD1background(Text text){
//		NineSliceSprite nice = new NineSliceSprite(
//				(text.getWidth() + 10) / 2 + text.getX(),
//				text.getY() + text.getHeight()/2,
//				text.getWidth() + 10,
//				text.getHeight() + 10,
//				this.resources.getTextureRegion(HUD_LEFT_TEXT_BACKGROUND),9,9,9,9,vbom);
//		
//		nice.attachChild(text);
//
//		return nice;
//	}

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
		
		//MENU ITEMS
//		Text playText = new Text(0, GameValues.getInstance().getHeight() - 50, this.resources.getFont(HUD_FONT), "Score: .0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
//		playText.setAnchorCenter(0, 0); 
//		playText.setText("Score: 00000");
//		this.createHUD1background(playText);
//		menuChildScene.attachChild(playText);
		
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, this.resources.getTextureRegion(PLAY), vbom), 1.2f, 1);
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, this.resources.getTextureRegion(OPTIONS), vbom), 1.2f, 1);

		//Menu BG
		
//		Sprite sprite_bg = new Sprite(0, 0, resources2.getTextureRegion(MAIN_MENU_BG), vbom);
//		sprite_bg.setWidth(playMenuItem.getWidth() + 100);
//		sprite_bg.setHeight(2 * (playMenuItem.getHeight() + 150));
//		menuChildScene.setBackground(new SpriteBackground(sprite_bg));
//		menuChildScene.attachChild(sprite_bg);
		NineSliceSprite nice = new NineSliceSprite(
				GameValues.getInstance().getCenterX(),
				GameValues.getInstance().getCenterY(),
				playMenuItem.getWidth() + 200,
				2 * (playMenuItem.getHeight() + 150) + 150,
				this.resources2.getTextureRegion(MAIN_MENU_BG),50,150,50,50,vbom);
		this.attachChild(nice);
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(0, 0);
		optionsMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() - 150);

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		Log.d("MainMenu", "Click");
		switch(pMenuItem.getID()) {
		case MENU_PLAY:
			//Load Game Scene!
			this.gameController.launchNewSession(null);
			return true;
		case MENU_OPTIONS:
			this.gameController.launchOptions(null);
			return true;
		default:
			return false;
		}
	}

}
