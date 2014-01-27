package com.thewhitecoconutstudio.archeryproject.scenes.impl;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.thewhitecoconutstudio.archeryproject.controllers.GameOverCauses;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionActionListener;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;

public class CustomTimerCallback implements ITimerCallback {
	
	private float time = GameValues.getInstance().getTimeOut(); // 30 SEC
	private final GameScene gameScene;
	private final SessionActionListener sessionActionListener;
	
	public CustomTimerCallback(final GameScene gameScene, final SessionActionListener sessionActionListener){
		this.gameScene = gameScene;
		this.sessionActionListener = sessionActionListener;
	}

	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {
		this.gameScene.setTimeText((int)this.time + "");
//		if(this.time == 0){
//			this.sessionActionListener.gameOver(GameOverCauses.NO_MORE_TIME);
//		} else {
			this.time -= 1;
//		}
	}

	public void reset(){
		this.time = GameValues.getInstance().getTimeOut();
	}
	
//	public CustomTimeHandler(float pTimerSeconds, ITimerCallback pTimerCallback) {
//		
//		ITimerCallback callback = new ITimerCallback()
//        {            
//
//			float time = 30f;
//			
//            @Override
//            public void onTimePassed(final TimerHandler pTimerHandler)
//            {
//            	Log.d("TIME", "Timer : " + this.time);
//            	GameScene.this.timeText.setText(this.time + "");
//				this.time -= 1;
//				if(this.time == 0){
//					GameScene.this.sessionActionListener.startShooting();
//				}
//            }
//            
//            public void resetAgain(){
//            	this.time = 30f;
//            }
//        }
//		
//		super(pTimerSeconds, callback);
//	}

}
