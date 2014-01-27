package com.thewhitecoconutstudio.archeryproject.models.listeners;

public interface HUDModelChangeListener {

	public void updateScore(int score);
	
	public void updateDistance(double distance);
	
	public void updateArrowNumber(int number);
	
	public void updateTargetNumbre(int number);
	
	public void updateTime(double time);
	
	public void noMoreArrows();
	
	public void noMoreTime();
	
	public void noMoreTargets();
	
	public void lastSeconds();
	
	public void missed();
}
