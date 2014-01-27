package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import java.util.HashMap;
import java.util.Map;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.GameActivity;

public class SceneResources {
	
	private static String DEBUG_TAG = SceneResources.class.getSimpleName();
	
	private static String PNG_EXTENSION = ".png";
	private static String TTF_EXTENSION = ".ttf";
	private static final String GFX_PATH = "gfx";
	private static final String FONT_PATH = "fonts";
	private static final String SLASH = "/";
	
	private final GameActivity activity;
	private final String name;
	
	//Graphics
	private final BuildableBitmapTextureAtlas graphicAtlas;
	private final Map<String, ITextureRegion> textureRegions;
	private final Map<String, ITiledTextureRegion> tiledTextureRegions;
	
	//Fonts
	private final ITexture fontAtlas;
	private final Map<String, Font> fonts;
	
	public SceneResources(String name, int atlasSize, GameActivity activity){
		
		this.activity = activity;
		this.name = name;
		
		//Graphics
		this.graphicAtlas =  new BuildableBitmapTextureAtlas(activity.getTextureManager(), atlasSize, atlasSize, TextureOptions.BILINEAR);
		this.textureRegions = new HashMap<String, ITextureRegion>();
		this.tiledTextureRegions = new HashMap<String, ITiledTextureRegion>();
		//Fonts
		this.fontAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.fonts = new HashMap<String, Font>();
	}

	public ITextureRegion getTextureRegion(String name){
		Log.d("TEST", "name : " + name);
		return this.textureRegions.get(name);
	}
	
	public ITextureRegion getTiledTextureRegion(String name){
		return this.tiledTextureRegions.get(name);
	}
	
	public Font getFont(String name){
		return this.fonts.get(name);
	}
	
	public void createGraphics(String... names){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GFX_PATH + SLASH + this.name + SLASH);
		
		for (int i = 0; i < names.length; i++) {
			Log.d("TEST", "creating : " + names[i] + " > " + (names[i] + PNG_EXTENSION));
			this.textureRegions.put(
					names[i], 
					BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.graphicAtlas, activity, names[i] + PNG_EXTENSION));
		}
	}
	
	public void addTiledGraphics(String name, int nb_tiles_x, int nb_tiles_y){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GFX_PATH + SLASH + this.name + SLASH);
		
			this.tiledTextureRegions.put(
					name, 
					BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.graphicAtlas, activity, name + PNG_EXTENSION,nb_tiles_x,nb_tiles_y));

	}
	
	public void createFonts(String... names){
		for (int i = 0; i < names.length; i++) {
			this.fonts.put(
					names[i], 
					FontFactory.createStroke(
							activity.getFontManager(),
							this.fontAtlas,
							//activity.getAssets(),
							//GFX_PATH + SLASH + FONT_PATH + SLASH + names[i],
							Typeface.createFromAsset(activity.getAssets(), GFX_PATH + SLASH + FONT_PATH + SLASH + names[i] + TTF_EXTENSION),
							30, true, Color.WHITE, 2, Color.GRAY)
					);
			this.fonts.get(names[i]).load();
			
		}
		
		/*
		 font = FontFactory.createStrokeFromAsset(
		 	activity.getFontManager(),
		 	 mainFontTexture,
		 	  activity.getAssets(),
		 	   "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
		   
		*/
	}
	
	
	public void loadResources(){
		Log.d(DEBUG_TAG, "Loading resources for scene : " + this.name);
		try {
			
			//Graphics
			this.graphicAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			this.graphicAtlas.load();
			
			//Fonts
			this.fontAtlas.load();
			
			//Music
			
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d(DEBUG_TAG, "Done Loading resources for scene : " + this.name);
	}
	
	public void unloadResource(){
		this.graphicAtlas.unload();
		this.fontAtlas.unload();
	}
	
}
