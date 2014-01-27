/**
 * 
 */
package com.thewhitecoconutstudio.archeryproject.controllers.impl;

import java.util.Collection;
import java.util.Iterator;

import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.util.IDisposable.AlreadyDisposedException;

import android.util.Log;

import com.thewhitecoconutstudio.archeryproject.controllers.GameOverCauses;
import com.thewhitecoconutstudio.archeryproject.controllers.SessionController;
import com.thewhitecoconutstudio.archeryproject.models.HUDModelUpdater;
import com.thewhitecoconutstudio.archeryproject.models.SessionModelUpdater;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Arrow;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Cross;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.GenericObject;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Target;
import com.thewhitecoconutstudio.archeryproject.models.listeners.TrajectoryListener;
import com.thewhitecoconutstudio.archeryproject.scenes.ViewChangeRequester;
import com.thewhitecoconutstudio.archeryproject.tools.GameValues;

/**
 * @author Paul
 *
 */
public class SessionControllerImpl extends HUDControllerImpl implements SessionController {

	private static final String DEBUG_TAG = "SessionControllerImpl";
	
	private SessionModelUpdater sessionModelUpdater;
	private HUDModelUpdater hudModelUpdater;
	private ViewChangeRequester viewChangeRequester;
	
	public SessionControllerImpl(SessionModelUpdater sessionModelUpdater, HUDModelUpdater hudModelUpdater, ViewChangeRequester viewChangeRequester){
		super(hudModelUpdater);
		this.sessionModelUpdater = sessionModelUpdater;
		this.hudModelUpdater = hudModelUpdater;
		this.viewChangeRequester = viewChangeRequester;
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.GameController#startAiming()
	 */
	@Override
	public void startAiming() {
		Log.d(DEBUG_TAG, "startAiming");
		
		//Create a crosshair
		this.sessionModelUpdater.createTheCrosshair(GameValues.getInstance().getCenterX(),GameValues.getInstance().getCenterY());
	}

	/* (non-Javadoc)
	 * @see com.thewhitecoconutstudio.archeryproject.GameController#stopAiming()
	 */
	@Override
	public void startShooting() {
		
		Log.d(DEBUG_TAG, "stopAiming");

		// Decrease by 1 arrow's nb
		this.hudModelUpdater.decreaseArrowNumber();
		
		// Reset timer
		this.hudModelUpdater.resetTimer();

		//Remove crosshair
		this.sessionModelUpdater.disposeCrosshair();
		
		//Launch arrow
		Log.d(DEBUG_TAG, "Launching Arrow..");
		//TODO Position?
		TrajectoryListener tl = new TrajectoryListener() {
			
			@Override
			public void onEnd(GenericObject genericObject) {
				Log.d(DEBUG_TAG, "onEnd");
				SessionControllerImpl.this.stopCourse((Arrow) genericObject );
			}
		};
		this.sessionModelUpdater.createAnArrow(GameValues.getInstance().getStartArrowX(), GameValues.getInstance().getStartArrowY(), tl);

	}

	/**
	 * 
	 * @param arrow
	 */
	@Override
	public void stopCourse(Arrow arrow) {
		Log.d(DEBUG_TAG, "Arrow stop its course !");

		float x = arrow.getBody().getPosition().x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		float y = arrow.getBody().getPosition().y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

		//Create a cross
		Cross cross = this.sessionModelUpdater.createACross(x, y,1);
		
		cross.getSprite().setAlpha(0.01f);

		//Compute if there is a hit
		Collection<Target> hitObject = this.sessionModelUpdater.computeCollision(cross.getSprite());

		//What to do if hit something
		if(hitObject != null && hitObject.size() > 0){
			Log.d(DEBUG_TAG, "Arrow has hit something !");
			cross.getSprite().setVisible(false);
			Iterator<Target> iter = hitObject.iterator();
			while(iter.hasNext()){
				Target target = iter.next();
				
				this.hudModelUpdater.increaseScore(this.sessionModelUpdater.hitATarget(target, x, y));
				
			}
			cross.dispose();

		} else {
//			this.sessionModelUpdater..missed();
		}
		
		//Is GameOver
		if(!this.sessionModelUpdater.isThereAnyRemainingTarget()){
			this.success();
		}

		//Delete the arrow
		try{
			arrow.dispose();
		} catch (AlreadyDisposedException ex){

		}
		
	}

	@Override
	public void success() {
		Log.d(DEBUG_TAG, "success");
		this.viewChangeRequester.goToNextLevel();
	}

	@Override
	public void gameOver(GameOverCauses cause) {
		Log.d(DEBUG_TAG, "gameOver : " + cause);
		this.viewChangeRequester.goToResultScreen();
	}

	@Override
	public void moveCrosshair(float x, float y) {
		Log.d(DEBUG_TAG, "moveCrosshair");
		this.sessionModelUpdater.moveCrosshair(x,y);
	}

	@Override
	public void loadLevel() {
		Log.d(DEBUG_TAG, "loadLevel");
		
		this.sessionModelUpdater.createAShelf(250, 400);
		this.sessionModelUpdater.createATarget(250, 500, "target1");
		
		this.sessionModelUpdater.createAShelf(350, 400);
		this.sessionModelUpdater.createATarget(350, 460, "target2");
		
	}

}
