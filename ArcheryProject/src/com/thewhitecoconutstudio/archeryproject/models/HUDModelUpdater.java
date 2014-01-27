package com.thewhitecoconutstudio.archeryproject.models;

import java.util.Map;

/**
 * @author paul
 *
 */
public interface HUDModelUpdater {
	
	/**
	 * Arrow --
	 */
	public void decreaseArrowNumber();
	
	/**
	 * Arrow ++
	 */
	public void increaseArrowNumber();

	/**
	 * When something has to be added
	 * 
	 * @param score
	 */
	public void increaseScore(int score);
	
	/**
	 * Add some time
	 * 
	 * @param time
	 */
	public void addTime(double time);
	
	/**
	 * Init time at the following value
	 * 
	 * @param time
	 */
	public void setTimer(double time);
	
	/**
	 * increase total distance
	 * 
	 * @param distance
	 */
	public void increaseDistance(float distance);
	
	/**
	 * Init arrowNumber
	 * 
	 * @param nb
	 */
	public void setArrowNumber(int i);
	
	/**
	 * Target --
	 * 
	 * @param type
	 */
	public void decreaseTargetNumber(String type);
	
	/**
	 * Target ++
	 * 
	 * @param type
	 */
	public void increaseTargetNumber(String type);
	
	/**
	 * Init target number
	 * 
	 * @param targets
	 */
	public void setTargetNumber(Map<String, Integer> targets);

	/**
	 * After each shoot, it resets the timer
	 */
	public void resetTimer();
	
}
