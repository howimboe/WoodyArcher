package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.thewhitecoconutstudio.archeryproject.scenes.BaseSceneListener;


public abstract class BaseScene extends Scene {
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
//    protected Engine engine;
//    protected GameActivity activity;
    protected VertexBufferObjectManager vbom;
    protected BoundCamera camera;
    
    protected BaseSceneListener listener;
    protected SceneResources resources;
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public BaseScene(VertexBufferObjectManager vbom, BoundCamera camera, BaseSceneListener listener) {
//        this.engine = engine;
//        this.activity = activity;
        this.vbom = vbom;
        this.camera = camera;
        
        if(this.listener == null){
        	this.listener = new BaseSceneListener() {
				
				@Override
				public void onSceneUnloaded() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSceneLoaded() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSceneFinished() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSceneCreated() {
					// TODO Auto-generated method stub
					
				}
			};
        } else {
        	this.listener = listener;
        }
        
    }
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();
    
    public void disposeScene(){
    	this.unloadSceneResources();
    	this.listener.onSceneFinished();
    }
    
    public void loadSceneResources(){
    	resources.loadResources();
    	
    	this.listener.onSceneLoaded();
    }
    
    public void unloadSceneResources(){
    	resources.unloadResource();
    	this.listener.onSceneUnloaded();
    }

}