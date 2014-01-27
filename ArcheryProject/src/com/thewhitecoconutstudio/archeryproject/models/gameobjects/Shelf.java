package com.thewhitecoconutstudio.archeryproject.models.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.thewhitecoconutstudio.archeryproject.tools.Collisionfilter;

public class Shelf implements GenericObject {

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private Body body;
	private Sprite sprite;
	
	private float x,y;
	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}

	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------

	public Shelf(float pX, float pY)	{
		this.x = pX;
		this.y = pY;
	}
	
	public void reset(){
	}

	private void createPhysics(PhysicsWorld physicsWorld)
	{        
		body = PhysicsFactory.createBoxBody(
				physicsWorld,
				this.sprite,
				BodyType.StaticBody, 
				PhysicsFactory.createFixtureDef(1.1f, 1f, 1f,
						false, Collisionfilter.CATEGORYBIT_ALL, Collisionfilter.MASKBITS_OTHER, (short)0));
		body.setUserData("shelf");
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
		this.createPhysics(physicsWorld);
	}

	@Override
	public Sprite getSprite() {
		return this.sprite;
	}
}