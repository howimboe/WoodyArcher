package com.thewhitecoconutstudio.archeryproject.models.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Cross implements GenericObject {

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	private Sprite sprite;
	
	private float x,y;
	
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------

	public Cross(float pX, float pY)	{
		this.x = pX;
		this.y = pY;
	}
	
	public void reset(){
	}

	public void dispose(){
		this.sprite.dispose();
		this.sprite.setVisible(false);
		this.sprite.detachSelf();
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}

	@Override
	public void createPhysics(PhysicsWorld physicsWorld, ITextureRegion tr,
			VertexBufferObjectManager vbom) {
//		this.setX(this.entity.convertSceneCoordinatesToLocalCoordinates(pX, pY)[0]);
//		this.setY(this.entity.convertSceneCoordinatesToLocalCoordinates(pX, pY)[1]);
		this.sprite = new Sprite(this.x, this.y, tr, vbom);
		this.sprite.setScale((float) 0.8);
	}

	@Override
	public Sprite getSprite() {
		return this.sprite;
	}
}