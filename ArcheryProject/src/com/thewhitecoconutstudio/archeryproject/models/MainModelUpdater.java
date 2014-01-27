package com.thewhitecoconutstudio.archeryproject.models;

public interface MainModelUpdater {

	public void changePlayer(String pseudo);
	
	public void increaseXP(int value);
	
	public void increasePlayerLevel(int value);
}
