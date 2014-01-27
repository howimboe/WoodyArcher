package com.thewhitecoconutstudio.archeryproject.models.impl;

import com.thewhitecoconutstudio.archeryproject.models.MainModel;
import com.thewhitecoconutstudio.archeryproject.models.listeners.MainModelChangeListener;

public class MainModelImpl implements MainModel {

	private String player = "Player1";
	private int XP = 0;
	private int playerLevel = 0;
	
	private MainModelChangeListener modelChangeListeners;
	
	public MainModelImpl(){
	}
	
	public void setMainModelChangeListener(MainModelChangeListener mainModelChangeListener){
		this.modelChangeListeners = mainModelChangeListener;
	}

	@Override
	public void changePlayer(String pseudo) {
		this.player = pseudo;
		this.modelChangeListeners.updatePlayer(this.player);
		
	}

	@Override
	public void increaseXP(int value) {
		this.XP += value;
		this.modelChangeListeners.updateXP(this.XP);
	}

	@Override
	public void increasePlayerLevel(int value) {
		this.playerLevel += value;
		this.modelChangeListeners.updatePlayerLevel(this.playerLevel);
	}
}
