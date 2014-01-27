package com.thewhitecoconutstudio.archeryproject.models.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.thewhitecoconutstudio.archeryproject.tools.Collisionfilter;

public class Crosshair implements GenericObject{

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private Body body;
	private Sprite sprite;

	private float x,y;
	
	private float vx, vy;
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------

	public Crosshair(float pX, float pY){
		this.x = pX;
		this.y = pY;
	}
	
	public float getX(){
		return this.body.getPosition().x;
	}
	
	public float getY(){
		return this.body.getPosition().y;
	}	
	
	public float getvx(){
		return this.vx;
	}
	
	public float getvy(){
		return this.vy;
	}

	public void move(float vx, float vy){
		Log.d("Crosshair", "Vx : " + vx + " Vy : " + vy);
		float velocityX = 0;
		float velocityY = 0;
		
		velocityX = vx;
		velocityY = vy;
		
		body.setLinearVelocity(new Vector2(10*velocityX, 10*velocityY)); 
	}
	
	public void stop(){
		body.setLinearVelocity(new Vector2(0, 0)); 
	}

	private void createPhysics(PhysicsWorld physicsWorld){ 
		
		Log.d("Crosshair", "Create physics");
		body = PhysicsFactory.createBoxBody(
				physicsWorld,
				this.sprite,
				BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef(1f, 1f, 1f,
						false, Collisionfilter.CATEGORYBIT_FREE, Collisionfilter.MASKBITS_FREE, (short)0));

		body.setUserData("crosshair");
		body.setFixedRotation(true);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this.sprite, body, true, false));
		body.setLinearVelocity(new Vector2(10, 10)); 

	}

	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}
	
	public void dispose(){
		if(!this.sprite.isDisposed()){
			this.sprite.dispose();
			this.sprite.setVisible(false);
			this.sprite.detachSelf();
		}
	}

	@Override
	public void createPhysics(PhysicsWorld physicsWorld, ITextureRegion tr,
			VertexBufferObjectManager vbom) {
		this.sprite = new Sprite(this.x, this.y, tr, vbom);
		this.createPhysics(physicsWorld);
	}

	@Override
	public Sprite getSprite() {
		return this.sprite;
	}

	public void setVx(float vx){
		this.vx =  vx;
	}
	
	public void setVy(float vy){
		this.vy = vy;
	}
}