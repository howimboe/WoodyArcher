package com.thewhitecoconutstudio.archeryproject.controllers;

/**
 * this interface describes the actions
 * handled by this controller
 * 
 * @author Paul
 *
 */
public interface HUDController{

	public void reset();
	
	public void scoreBlue();
	
	public void score(int score);
	
	public void scoreRed();
	
	public void scoreWhite();
	
	public void scoreDistance(double distance);
	
	public void setTimer(double time);
	
	public void setArrowNumber(int i);
	
	public void decreaseArrowNumber(int i);
	
	public void increasrArrowNumber(int i);
	
	public void decreaseTargetNumber(String type);
	
	public void increaseTargetNumber(String type);
}
