/**
 * 
 */
package com.thewhitecoconutstudio.archeryproject.models.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.thewhitecoconutstudio.archeryproject.models.SessionModel;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Arrow;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Cross;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Crosshair;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Shelf;
import com.thewhitecoconutstudio.archeryproject.models.gameobjects.Target;
import com.thewhitecoconutstudio.archeryproject.models.listeners.HUDModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.models.listeners.SessionModelChangeListener;
import com.thewhitecoconutstudio.archeryproject.models.listeners.TrajectoryListener;

/**
 * @author Paul
 *
 */
public class SessionModelImpl extends HUDModelImpl implements SessionModel {

	private Crosshair crosshair;
	private java.util.Map<Target, String> targets;
	//	private float gravity;
	private final Collection<Sprite> spritesToRecycle = new ArrayList<Sprite>();
	private final Collection<Body> bodiesToRecycle = new ArrayList<Body>();
	private Sprite mainTarget;

	//	private SceneResources sceneResources;

	//Graphics
	//	private final static String TARGET_CARTON = "carton";
	//	private final static String TARGET_EXTERN = "cible_externe";
	//	private final static String TARGET_MIDDLE = "cible_moyenne";
	//	private final static String TARGET_INTERN = "cible_interne";
	//	private final static String CROSS1 = "croix";
	//	private final static String CROSS2 = "croix2";
	//	private final static String CROSS3 = "croix3";
	//	private final static String CROSSHAIR = "crosshair";
	//	private final static String ARROW = "ball";
	//	private final static String SHELF = "shelf";
	//	private final static String TARGET = "target";

	private SessionModelChangeListener sessionModelChangeListener;

	public SessionModelImpl(){
		super();

		targets = new HashMap<Target, String>();
	}

	public void setSessionModelChangeListener(SessionModelChangeListener sessionModelChangeListener){
		this.sessionModelChangeListener = sessionModelChangeListener;
	}

	public void setHUDModelChangeListener(HUDModelChangeListener hudModelChangeListener){
		super.setHUDModelChangeListener(hudModelChangeListener);
	}

	//	public SessionModelImpl(GameScene scene, GameActivity activity, float gravity, VertexBufferObjectManager vbom, PhysicsWorld physicsWorld, TrajectoryListener trajectoryListener){
	//		this.trajectoryListener = trajectoryListener;
	//		this.vbom = vbom;
	//		this.scene = scene;
	//		this.physicsWorld = physicsWorld;
	//		this.gravity = gravity;
	//		this.targets = new HashMap<Target, String>();
	//		this.spritesToRecycle = new ArrayList<Sprite>();
	//		this.bodiesToRecycle = new ArrayList<Body>();
	//		
	//		sceneResources = new SceneResources(SessionModelImpl.class.getSimpleName(), 1024, activity);
	//		sceneResources.createGraphics(
	//				TARGET_CARTON, TARGET_EXTERN, TARGET_MIDDLE, TARGET_INTERN,
	//				CROSS1, CROSS2, CROSS3,SHELF,TARGET,
	//				CROSSHAIR
	//				);
	//		sceneResources.addTiledGraphics(ARROW, 2, 1);
	//		sceneResources.loadResources();
	//	}

	//	public WorldObjectModelImpl(){
	//		this.targets = new HashMap<Sprite, String>();
	//		this.spritesToRecycle = new ArrayList<Sprite>();
	//		this.bodiesToRecycle = new ArrayList<Body>();
	/**
	 * @param x
	 * @param y
	 */

	public Shelf createAShelf(float x, float y){
		Shelf shelf = new Shelf(x, y);
		this.sessionModelChangeListener.onShelfCreated(shelf);
		return shelf;

	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param name
	 */
	public Target createATarget(float x, float y, String name){
		Target target =  new Target(x,y, 3);
		this.targets.put(target, name);
		this.sessionModelChangeListener.onTargetCreated(target);
		return target;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	@Override
	public Crosshair createTheCrosshair(float x, float y) {
		crosshair =  new Crosshair(x, y);
		this.sessionModelChangeListener.onCrosshairCreated(crosshair);
		return crosshair;
	}


	/**
	 * 
	 * @param x
	 * @param y
	 */
	@Override
	public Arrow createAnArrow(float x, float y, TrajectoryListener trajectoryListener) {

		Arrow arrow = new Arrow(x,y, trajectoryListener);
		this.sessionModelChangeListener.onArrowCreated(arrow);
		if(crosshair != null){
			arrow.move(crosshair.getX(), crosshair.getY());
		}
		return arrow; 
	}

	//	@Override
	//	public Sprite createATarget(float x, float y) {
	//		//		Log.d(DEBUG_TAG, "createATarget X : " + x + " Y : " + y);
	//
	//		//		Sprite sprite_extern = new Sprite(x , y, resourcesManager.target_extern_region, vbom);
	//		//		Sprite sprite_middle = new Sprite(
	//		//				resourcesManager.target_extern_region.getWidth() / 2, 
	//		//				resourcesManager.target_extern_region.getHeight() / 2, 
	//		//				resourcesManager.target_middle_region, vbom);
	//		//		Sprite sprite_intern = new Sprite(
	//		//				resourcesManager.target_extern_region.getWidth() / 2 ,
	//		//				resourcesManager.target_extern_region.getHeight() / 2,
	//		//				resourcesManager.target_intern_region, vbom);
	//
	//		this.modelChangeListeners.
	//	}

	@Override
	public Cross createACross(float x, float y, int i) {

		//		Sprite sprite = null;
		//
		//		//		float xInTarget = this.mainTarget.convertSceneCoordinatesToLocalCoordinates(x, y)[0];
		//		//		float yInTarget = this.mainTarget.convertSceneCoordinatesToLocalCoordinates(x, y)[1];
		//
		//		float xInTarget = x;
		//		float yInTarget = y;
		//
		//		if(i ==1){
		//			sprite =new Sprite(xInTarget , yInTarget , this.sceneResources.getTextureRegion(CROSS2), vbom); 
		//		} else if(i ==2){
		//			sprite =new Sprite(xInTarget , yInTarget , this.sceneResources.getTextureRegion(CROSS3), vbom); 
		//		} else {
		//			sprite = new Sprite(xInTarget , yInTarget , this.sceneResources.getTextureRegion(CROSS1), vbom); 
		//		}
		//
		//		sprite.setScale(0.5f);
		//		//		this.mainTarget.attachChild(sprite);
		//		scene.attachChild(sprite);
		//		this.spritesToRecycle.add(sprite);
		//		return sprite;
		Cross cross = new Cross(x, y);
		this.sessionModelChangeListener.onCrossCreated(cross);
		return cross;
	}

	@Override
	public Crosshair getCrosshair() {
		return this.crosshair;
	}

	/**
	 * 
	 * @param sprite
	 */
	@Override
	public java.util.Collection<Target> computeCollision(Sprite sprite) {
		Collection<Target> returnCollection = new ArrayList<Target>();
		Iterator<Target> iter = this.targets.keySet().iterator();
		while(iter.hasNext()){
			Target test = iter.next();
			if(test.isAlive()){
				if(this.collidesWith(sprite,test.getSprite())){
					returnCollection.add(test);
				}
			}
		}
		Log.d("SessionModelImpl", "Collide with : " + returnCollection.size() + " items");
		return returnCollection;
	}

	@Override
	public void hitObject(Sprite sprite) {
		//		sprite.registerEntityModifier(new ScaleModifier(0.1f,1.0f, 1.2f));
		//		sprite.registerEntityModifier(new ScaleModifier(0.2f,1.2f, 1.0f));
	}


	// There is no need to use Sprites, we will use the superclass Entity
	private boolean collidesWith(Entity circle1, Entity circle2){
		final float[] xy1 = circle2.convertLocalCoordinatesToSceneCoordinates(circle2.getWidth()/2, circle2.getHeight()/2);
		final float x1 = xy1[0];
		final float y1 = xy1[1];
		//		Log.d("DIM", "Circle 2 : getX = " + x1 + " | getY = " + y1);
		final float[] xy2 = circle1.convertLocalCoordinatesToSceneCoordinates(circle1.getWidth()/2, circle1.getHeight()/2);
		final float x2 = xy2[0];
		final float y2 = xy2[1];

		//		Log.d("DIM", "Circle 1 : " + x2 + " | " + y2);
		final float xDifference = x2 - x1;
		final float yDifference = y2 - y1;
		//		Log.d("DIM", "Delta : " + xDifference + " | " + yDifference);
		// The ideal would be to provide a radius, but as
		// we assume they are perfect circles, half the
		// width will be just as good
		final float radius1 = circle2.getWidth()/2 * circle2.getScaleX() * circle2.getParent().getScaleX() - (circle1.getWidth()/2 * circle1.getScaleX());
		//		Log.d("DIM", "radius1 : " + radius1);
		final float radius2 = 0;

		// Note we are using inverseSqrt but not normal sqrt,
		// please look below to see a fast implementation of it.
		// Using normal sqrt would not need "1.0f/", is more precise
		// but less efficient
		final float euclideanDistance = 1.0f/inverseSqrt(
				xDifference*xDifference +
				yDifference*yDifference);
		//		final float euclideanDistance = (float) Math.sqrt(xDifference*xDifference +
		//				yDifference*yDifference);

		return euclideanDistance < (radius1+radius2);
	}

	/**
	 * Gets an aproximation of the inverse square root with float precision.
	 * @param x float to be square-rooted
	 * @return an aproximation to sqrt(x)
	 */
	public static float inverseSqrt(float x) {
		float xhalf = 0.5f*x;
		int i = Float.floatToIntBits(x);
		i = 0x5f3759df - (i>>1);
		x = Float.intBitsToFloat(i);
		x = x*(1.5f - xhalf*x*x);
		return x;
	}

	@Override
	public Body retrieveBody(Sprite sprite) {
		//		return this.targets.get(sprite);
		return null;
	}

	public void dispose(){

		Iterator<Body> bodiesIterator = this.bodiesToRecycle.iterator();

		while(bodiesIterator.hasNext()){
			Body body = bodiesIterator.next();
			body.setActive(false);
		}
		Iterator<Sprite> spritesIterator = this.spritesToRecycle.iterator();

		while(spritesIterator.hasNext()){
			Sprite sprite = spritesIterator.next();
			sprite.setVisible(false); 
			try{
				sprite.dispose();
			} catch (Exception e){

			}
		}


	}

	/**
	 * 
	 * @param target
	 * @param pX
	 * @param pY
	 */
	public int hitATarget(Target target, float pX, float pY){
		Log.d("SessionModelImpl", "addACrossToTarget");
		Cross cross = new Cross(pX, pY);
		this.sessionModelChangeListener.onCrossAdded(cross, target);

		return target.hitTarget(cross);
	}

	@Override
	public Sprite getCurrentTarget() {
		return this.mainTarget;
	}

	public void displayGameOver() {
		//		this.scene.endOfGame();
	}

	@Override
	public void moveCrosshair(float vx, float vy) {
		if(crosshair != null){
			crosshair.setVx(vx);
			crosshair.setVy(vy);
			this.sessionModelChangeListener.onMovingCrosshair(crosshair);
		}
	}

	@Override
	public void disposeCrosshair() {
		if(crosshair!=null){
			crosshair.getSprite().setVisible(false);
			crosshair.dispose();
		}
	}

	@Override
	public boolean isThereAnyRemainingTarget() {
		//Iter on targets to see how many are alive
		Iterator<Target> iter = this.targets.keySet().iterator();
		while(iter.hasNext()){
			if(iter.next().isAlive()){
				return true;
			}
		}
		return false;
	}
}
