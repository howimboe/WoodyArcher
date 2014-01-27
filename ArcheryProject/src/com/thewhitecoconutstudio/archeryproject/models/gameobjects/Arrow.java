package com.thewhitecoconutstudio.archeryproject.models.gameobjects;

import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.thewhitecoconutstudio.archeryproject.models.listeners.TrajectoryListener;
import com.thewhitecoconutstudio.archeryproject.tools.Collisionfilter;

public class Arrow implements GenericObject {

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private Body body;
	private Sprite sprite;
	
	private float x;
	private float y;
	
	/**
	 * @return the body
	 */
	public Body getBody() {
		return this.body;
	}
	
	public Sprite getSprite(){
		return this.sprite;
	}

	private boolean isMoving = false;
	private int time = 2;
	private TrajectoryListener trajectoryListener;
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------

	public Arrow(float pX, float pY, TrajectoryListener trajectoryListener)	{
		this.x = pX;
		this.y = pY;
		this.trajectoryListener = trajectoryListener;
	}
	
	public void reset(){
	}

	public void move(float x, float y){
		this.launch(x, y);
	}

	public void launch(float xTarget, float yTarget){

		this.body.setLinearVelocity((xTarget - this.body.getPosition().x)/time, (yTarget - this.body.getPosition().y)/time);
		this.body.applyLinearImpulse(0, -1, body.getWorldCenter().x, body.getWorldCenter().y);
		this.isMoving = true;

	}

	@Override
	public void createPhysics(PhysicsWorld physicsWorld, ITextureRegion tr, VertexBufferObjectManager vbom){        
		this.sprite = new Sprite(this.x, this.y, tr, vbom);
		
		body = PhysicsFactory.createBoxBody(
				physicsWorld,
				this.sprite,
				BodyType.DynamicBody, 
				PhysicsFactory.createFixtureDef(1.1f, 1f, 1f,
						false, Collisionfilter.CATEGORYBIT_FREE, Collisionfilter.MASKBITS_OTHER, (short)0));
		body.setUserData(Arrow.class);
		body.setFixedRotation(true);

		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this.sprite, body, true, false)
		{
			private float second = 0;
			
			@Override
			public void onUpdate(float pSecondsElapsed)
			{
				super.onUpdate(pSecondsElapsed);
				
				if(isMoving){
					Arrow.this.sprite.registerEntityModifier(new ScaleModifier(time, 5f, 0.5f));
						if(second ==  0){
							second += pSecondsElapsed;
							
						} else {
							second += pSecondsElapsed;
							if(second > time){
								//STOP
								Log.d("TIME", "Seconds : " + second);
								body.setLinearVelocity(0, 0);
								if(trajectoryListener != null){
									trajectoryListener.onEnd(Arrow.this);
								}
								
								isMoving = false;
								second = 0;
							} else {
								//NOTHING
							}
						}
				}
			}
		});

	}

	public void dispose(){
		this.sprite.dispose();
		this.sprite.setVisible(false);
		this.sprite.detachSelf();
	}

}