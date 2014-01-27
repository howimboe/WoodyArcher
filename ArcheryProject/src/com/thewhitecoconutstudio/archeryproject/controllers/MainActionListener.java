package com.thewhitecoconutstudio.archeryproject.controllers;

import org.andengine.ui.IGameInterface.OnCreateSceneCallback;


/**
 * This interface describes the actions
 * handled by this controller
 * @author paul
 *
 */
public interface MainActionListener {

	/**
	 * Called when back button is pressed
	 */
	public void goBack();
	
	/**
	 * Called when play is pressed
	 */
	public void launchNewSession(OnCreateSceneCallback pOnCreateSceneCallback);

	/**
	 * Called when ranking is pressed
	 */
	public void rankings(OnCreateSceneCallback pOnCreateSceneCallback);

	/**
	 * Called when options is pressed
	 */
	public void launchOptions(OnCreateSceneCallback pOnCreateSceneCallback);
	
	/**
	 * Go back to menu
	 */
	public void goBackToMenu();
}
