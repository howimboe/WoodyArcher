package com.thewhitecoconutstudio.archeryproject.controllers.impl;

import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.controllers.HUDController;
import com.thewhitecoconutstudio.archeryproject.models.HUDModelUpdater;

public class HUDControllerImpl implements HUDController {

	private final static int SCORE_BLUE  = 5;
	private final static int SCORE_WHITE = 25;
	private final static int SCORE_RED   = 50;
	private final static String DEBUG_TAG = "HUDControllerImpl";
	
	private HUDModelUpdater model;
	
	public HUDControllerImpl(HUDModelUpdater model){
		this.model = model;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scoreBlue() {
		Log.d(DEBUG_TAG, "scoreBlue");
		model.increaseScore(SCORE_BLUE);
	}

	@Override
	public void scoreRed() {
		Log.d(DEBUG_TAG, "scoreRed");
		model.increaseScore(SCORE_RED);
		
	}

	@Override
	public void scoreWhite() {
		Log.d(DEBUG_TAG, "scoreWhite");
		model.increaseScore(SCORE_WHITE);
	}

	@Override
	public void scoreDistance(double distance) {
		Log.d(DEBUG_TAG, "scoreDistance : " + distance);
		model.increaseDistance((float) distance);
	}

	@Override
	public void setTimer(double time) {
		Log.d(DEBUG_TAG, "setTimer : " + time);
		model.setTimer(time);
	}

	@Override
	public void setArrowNumber(int i) {
		Log.d(DEBUG_TAG, "setArrowNumber : " + i);
		model.setArrowNumber(i);
	}

	@Override
	public void decreaseArrowNumber(int i) {
		Log.d(DEBUG_TAG, "decreaseArrowNumber : " + (-i));
		model.decreaseArrowNumber();
	}

	@Override
	public void increasrArrowNumber(int i) {
		Log.d(DEBUG_TAG, "increasrArrowNumber : " + i);
		model.increaseArrowNumber();
	}

	@Override
	public void score(int score) {
		model.increaseScore(score);
	}

	@Override
	public void decreaseTargetNumber(String type) {
		model.decreaseTargetNumber(type);
	}

	@Override
	public void increaseTargetNumber(String type) {
		model.increaseTargetNumber(type);
	}

}
