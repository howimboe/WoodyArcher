package com.thewhitecoconutstudio.archeryproject.models;

import com.thewhitecoconutstudio.archeryproject.models.listeners.MainModelChangeListener;

public interface MainModel extends MainModelUpdater{

	public void setMainModelChangeListener(MainModelChangeListener mainModelChangeListener);
}
