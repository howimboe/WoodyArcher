package com.thewhitecoconutstudio.archeryproject.models.listeners;

import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Arrow;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Cross;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Crosshair;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Shelf;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Target;

public interface SessionModelChangeListener {
	public void onCrossCreated(Cross cross);
	
	public void onTargetCreated(Target target);
	
	public void onCrosshairCreated(Crosshair crosshair);
	
	public void onShelfCreated(Shelf shelf);
	
	public void onArrowCreated(Arrow arrow);
	
	public void onCrossAdded(Cross cross, Target target);
	
	public void onMovingCrosshair(Crosshair crosshair);
}
