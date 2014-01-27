package com.thewhitecoconutstudio.archeryproject.models.listeners;

public interface MainModelChangeListener {

	public void updatePlayer(String pseudo);
	
	public void updateXP(int value);
	
	public void updatePlayerLevel(int level);
}
