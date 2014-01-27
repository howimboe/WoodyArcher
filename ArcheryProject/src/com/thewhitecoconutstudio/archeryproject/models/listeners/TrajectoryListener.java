package com.thewhitecoconutstudio.archeryproject.models.listeners;

import com.thewhitecoconutstudio.archeryproject.models.gameobjects.*;

public interface TrajectoryListener {

	/**
	 * 
	 * @param genericObject
	 */
	void onEnd(GenericObject genericObject);
}
