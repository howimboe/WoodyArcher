package com.thewhitecoconutstudio.archeryproject.models.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public interface GenericObject {

	public void createPhysics(PhysicsWorld physicsWorld, ITextureRegion tr, VertexBufferObjectManager vbom);
	
	public Sprite getSprite();
}
