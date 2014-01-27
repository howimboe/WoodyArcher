package com.thewhitecoconutstudio.archeryproject.models.impl;

import java.util.Map;

import com.thewhitecoconutstudio.archeryproject.models.HUDModel;
import com.thewhitecoconutstudio.archeryproject.models.HUDModelUpdater;
import com.thewhitecoconutstudio.archeryproject.models.listeners.HUDModelChangeListener;

public class HUDModelImpl  implements HUDModel,HUDModelUpdater {

	private HUDModelChangeListener hudModelChangeListener;
	
	private int score = 0;
	private float distance = 0;
	private final int ARROW_DEFAULT = 10;
	private int arrowNb = 0;
	private double time = 0;
	private final double TIME_DEFAULT = 15;
	private final double TIME_HIGHLIGHT = 3;
	private int target = 4;
	
	public HUDModelImpl(){
		this.time = TIME_DEFAULT;
		this.arrowNb = ARROW_DEFAULT;
	}
	
	public void setHUDModelChangeListener(HUDModelChangeListener hudModelChangeListener){
		this.hudModelChangeListener = hudModelChangeListener;
	}
	
	@Override
	public void decreaseArrowNumber() {
		this.modifyArrowNumber(-1);
		this.hudModelChangeListener.updateArrowNumber(this.arrowNb);
	}

	@Override
	public void increaseArrowNumber() {
		this.modifyArrowNumber(1);
		this.hudModelChangeListener.updateArrowNumber(this.arrowNb);
	}

	@Override
	public void increaseScore(int score) {
		this.score += score;
		this.hudModelChangeListener.updateScore(this.score);
	}

	@Override
	public void addTime(double time) {
		this.modifyTime(time);
		this.hudModelChangeListener.updateTime(this.time);
	}

	@Override
	public void setTimer(double time) {
		this.time = time;
		this.hudModelChangeListener.updateTime(this.time);
		
	}

	@Override
	public void increaseDistance(float distance) {
		this.distance += distance;
		this.hudModelChangeListener.updateDistance(this.distance);
	}

	@Override
	public void setArrowNumber(int i) {
		this.arrowNb = i;
		this.hudModelChangeListener.updateArrowNumber(this.arrowNb);
	}

	@Override
	public void decreaseTargetNumber(String type) {
		this.modifyTargetNumber(-1);
		this.hudModelChangeListener.updateTargetNumbre(this.target);
	}

	@Override
	public void increaseTargetNumber(String type) {
		this.modifyTargetNumber(1);
		this.hudModelChangeListener.updateTargetNumbre(this.target);
	}

	@Override
	public void setTargetNumber(Map<String, Integer> targets) {
		this.target = targets.size();
		this.hudModelChangeListener.updateTargetNumbre(this.target);
	}

	private void modifyArrowNumber(int value){
		if((this.arrowNb + value) > 0 && (this.arrowNb + value)<100){
			this.arrowNb += value;
		} else {
			this.hudModelChangeListener.noMoreArrows();
		}
	}
	
	private void modifyTargetNumber(int value){
		if((this.target + value) > 0){
			this.target += value;
		} else {
			this.hudModelChangeListener.noMoreTargets();
		}
	}
	
	private void modifyTime(double value){
		if((this.time + value) > 0 ){
			this.time += value;
		} else if (this.time + value > TIME_HIGHLIGHT){
			this.time += value;
			this.hudModelChangeListener.lastSeconds();
		}else {
			this.hudModelChangeListener.noMoreTime();
		}
	}

	@Override
	public void resetTimer() {
		this.time = TIME_DEFAULT;
	}

}
