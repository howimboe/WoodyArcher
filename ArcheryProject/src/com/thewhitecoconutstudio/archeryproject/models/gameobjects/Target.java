package com.thewhitecoconutstudio.archeryproject.models.gameobjects;

import java.util.ArrayList;
import java.util.Collection;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.thewhitecoconutstudio.archeryproject.tools.Collisionfilter;

public class Target implements GenericObject {

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private Body body;
	private Collection<Cross> crosses = new ArrayList<Cross>();
	private Sprite sprite;
	private boolean isAlive = true;
	private int nbTouch = 1;
	
	private float x,y;
	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * Called when the target is hit
	 */
	public int hitTarget(Cross cross){
		this.addCross(cross);
		this.nbTouch --;
		if(this.nbTouch == 0){
			this.isAlive = false;
			this.dispose();
		}
		
		//TODO compute score
		return 10;
	}
	
	/**
	 * True if available for collision detection
	 * 
	 * @return
	 */
	public boolean isAlive(){
		return this.isAlive;
	}
	
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------

	public Target(float pX, float pY, int nbTouch)	{
		this.x = pX;
		this.y = pY;
		this.nbTouch = nbTouch;
	}
	
	private Cross addCross(Cross cross){
		crosses.add(cross);
		return cross;
	}
	
	public void reset(){
	}

	private void createPhysics(PhysicsWorld physicsWorld){        
	
		body = PhysicsFactory.createCircleBody(
				physicsWorld,
				this.sprite,
				BodyType.DynamicBody, 
				PhysicsFactory.createFixtureDef(1.1f, 1f, 1f,
						false, Collisionfilter.CATEGORYBIT_ALL, Collisionfilter.MASKBITS_OTHER, (short)0));
		body.setUserData("target");
		body.setFixedRotation(true);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this.sprite, body, true, false));

	}

	public void dispose(){
		this.sprite.dispose();
		this.sprite.setVisible(false);
		this.sprite.detachSelf();
	}

	@Override
	public void createPhysics(PhysicsWorld physicsWorld, ITextureRegion tr,
			VertexBufferObjectManager vbom) {
		this.sprite = new Sprite(this.x, this.y, tr, vbom);
		this.sprite.setScale(1f);
		this.createPhysics(physicsWorld);
	}

	@Override
	public Sprite getSprite() {
		return this.sprite;
	}
}