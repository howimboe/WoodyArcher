package com.thewhitecoconutstudio.archeryproject.controllers;

import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Arrow;

public interface SessionActionListener {
	/**
	 * Called when start aiming
	 */
	public void startAiming();

	
	/**
	 * Called when stop Aiming, and the start of the shooting
	 */
	public void startShooting();
	
	/**
	 * Load the level
	 */
	public void loadLevel();
	
	/**
	 * When the arrow reach end of trajectory
	 * @param arrow
	 */
	void stopCourse(Arrow arrow);
	
	/**
	 * Called when all targets have been destroyed
	 */
	public void success();
	
	/**
	 * Called when game is over
	 */
	public void gameOver(GameOverCauses cause);
	
	/**
	 * Called when moving the crosshair
	 * 
	 * @param x
	 * @param y
	 */
	public void moveCrosshair(float x, float y);

}
