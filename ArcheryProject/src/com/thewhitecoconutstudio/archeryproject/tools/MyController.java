package com.thewhitecoconutstudio.archeryproject.tools;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;


/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 00:21:55 - 11.07.2010
 */
public class MyController extends BaseOnScreenControl implements IClickDetectorListener {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ClickDetector mClickDetector = new ClickDetector(this);
	private CustomTouchListener customTouchListener;
	// ===========================================================
	// Constructors
	// ===========================================================

	public MyController(final float pX, final float pY, final Camera pCamera, final ITextureRegion pControlBaseTextureRegion, final ITextureRegion pControlKnobTextureRegion, final float pTimeBetweenUpdates, final VertexBufferObjectManager pVertexBufferObjectManager, final CustomTouchListener pAnalogOnScreenControlListener) {
		super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pVertexBufferObjectManager, pAnalogOnScreenControlListener);
		this.customTouchListener = pAnalogOnScreenControlListener;
		this.mClickDetector.setEnabled(false);
	}

	public MyController(final float pX, final float pY, final Camera pCamera, final ITextureRegion pControlBaseTextureRegion, final ITextureRegion pControlKnobTextureRegion, final float pTimeBetweenUpdates, final long pOnControlClickMaximumMilliseconds, final VertexBufferObjectManager pVertexBufferObjectManager, final CustomTouchListener iMyControllerListener) {
		super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pVertexBufferObjectManager, iMyControllerListener);
		this.customTouchListener = iMyControllerListener;
		this.mClickDetector.setTriggerClickMaximumMilliseconds(pOnControlClickMaximumMilliseconds);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	@Override
	public CustomTouchListener getOnScreenControlListener() {
		return (CustomTouchListener)super.getOnScreenControlListener();
	}

	public void setOnControlClickEnabled(final boolean pOnControlClickEnabled) {
		this.mClickDetector.setEnabled(pOnControlClickEnabled);
	}

	public void setOnControlClickMaximumMilliseconds(final long pOnControlClickMaximumMilliseconds) {
		this.mClickDetector.setTriggerClickMaximumMilliseconds(pOnControlClickMaximumMilliseconds);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onClick(final ClickDetector pClickDetector, final int pPointerID, final float pSceneX, final float pSceneY) {
		this.getOnScreenControlListener().onControlClick(this);
	}

	@Override
	protected boolean onHandleControlBaseTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if(this.customTouchListener != null){
			if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
				customTouchListener.onStartTouch();
			} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE){
				//NOTHING
			} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP || pSceneTouchEvent.getAction() == TouchEvent.ACTION_CANCEL){
				customTouchListener.onStopTouch();
			}
		}
		this.mClickDetector.onSceneTouchEvent(null, pSceneTouchEvent);
		return super.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}

	@Override
	protected void onUpdateControlKnob(final float pRelativeX, final float pRelativeY) {
		if(pRelativeX * pRelativeX + pRelativeY * pRelativeY <= 0.25f) {
			super.onUpdateControlKnob(pRelativeX, pRelativeY);
		} else {
			final float angleRad = MathUtils.atan2(pRelativeY, pRelativeX);
			super.onUpdateControlKnob((float)Math.cos(angleRad) * 0.5f, (float)Math.sin(angleRad) * 0.5f);
		}
	}
	
	public void setCustomTouchListener(CustomTouchListener customTouchListener){
		this.customTouchListener = customTouchListener;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

//	public interface IMyControllerListener extends IOnScreenControlListener {
//		// ===========================================================
//		// Constants
//		// ===========================================================
//
//		// ===========================================================
//		// Methods
//		// ===========================================================
//
//		public void onControlClick(final MyController pAnalogOnScreenControl);
//	}
	
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		return super.onSceneTouchEvent(pScene, pSceneTouchEvent);
	}
}
