package com.thewhitecoconutstudio.archeryproject.models;

import org.andengine.entity.sprite.Sprite;

import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Arrow;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Cross;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Crosshair;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Shelf;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Target;
import com.thewhitecoconutstudio.archeryproject.models.listeners.TrajectoryListener;

public interface SessionModelUpdater {

	/**
	 * 
	 * @param x
	 * @param y
	 */
	Crosshair createTheCrosshair(float x, float y);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param trajectoryListener
	 */
	Arrow createAnArrow(float x, float y,  TrajectoryListener trajectoryListener);
	
	/**
	 * @param x
	 * @param y
	 * @param i
	 * @return
	 */
	public Cross createACross(float x, float y, int i);
	
	/**
	 * @param sprite
	 */
	public void hitObject(Sprite sprite);
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	Shelf createAShelf(float x, float y);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param name
	 */
	Target createATarget(float x, float y, String name);
	
	/**
	 * 
	 * @param target
	 * @param x
	 * @param y
	 * @return the score
	 */
	int hitATarget(Target target, float x, float y);
	
	/**
	 * Should not be here since it does not 
	 * update anything on the model.
	 * 
	 * @param sprite
	 */
	java.util.Collection<Target> computeCollision(Sprite sprite);
	
	/**
	 * Used to computeGameOver
	 * 
	 * @return
	 */
	public boolean isThereAnyRemainingTarget();

	/**
	 * Called to update the crosshair position
	 * 
	 * @param x
	 * @param y
	 */
	public void moveCrosshair(float x, float y);
	
	/**
	 * Dispose the crosshair after the shoot
	 */
	public void disposeCrosshair();
}
