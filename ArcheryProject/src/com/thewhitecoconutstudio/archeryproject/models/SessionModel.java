package com.thewhitecoconutstudio.archeryproject.models;

import org.andengine.entity.sprite.Sprite;

import com.badlogic.gdx.physics.box2d.Body;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Crosshair;
import com.thewhitecoconutstudio.archeryproject.models.listeners.HUDModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.models.listeners.SessionModelChangeListener;

public interface SessionModel extends SessionModelUpdater {

	public Body retrieveBody(Sprite sprite);
	
	public Crosshair getCrosshair();
	
	
	public void hitObject(Sprite sprite);
	
	public Sprite getCurrentTarget();
	
	public void dispose();
	
	public void displayGameOver();
	
	public void setSessionModelChangeListener(SessionModelChangeListener sessionModelChangeListener);
	
	public void setHUDModelChangeListener(HUDModelChangeListener hudModelChangeListener);
}
