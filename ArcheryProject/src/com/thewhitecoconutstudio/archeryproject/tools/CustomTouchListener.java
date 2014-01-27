package com.thewhitecoconutstudio.archeryproject.tools;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;


public interface CustomTouchListener extends IOnScreenControlListener {
	
	/**
	 * Called when someone starts touching the controller
	 */
	public void onStartTouch();
	
	/**
	 * Called when someone stops touching the controller
	 */
	public void onStopTouch();
	
	/* (non-Javadoc)
	 * @see org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener#onControlChange(org.andengine.engine.camera.hud.controls.BaseOnScreenControl, float, float)
	 */
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl, float pValueX, float pValueY);

	/**
	 * Called when controller is clicked
	 * 
	 * @param pAnalogOnScreenControl
	 */
	public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl);
	
	/**
	 * Called when controller is clicked with custom controller
	 * 
	 * @param pAnalogOnScreenControl
	 */
	public void onControlClick(final MyController pAnalogOnScreenControl);
}
