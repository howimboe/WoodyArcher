package com.thewhitecoconutstudio.archeryproject.scenes;

import com.thewhitecoconutstudio.archeryproject.controllers.SessionController;
import com.thewhitecoconutstudio.archeryproject.models.listeners.MainModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.models.listeners.ModelChangeListener;

/**
 * 
 * 
 * @author paul
 *
 */
public interface SceneViewManager extends ViewChangeRequester, ModelChangeListener, MainModelChangeListener {

	void setSessionController(SessionController sessionController);

}
