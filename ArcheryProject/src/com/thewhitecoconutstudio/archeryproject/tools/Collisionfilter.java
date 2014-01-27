package com.thewhitecoconutstudio.archeryproject.tools;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Collisionfilter {

	/* The categories. */
    public static final short CATEGORYBIT_ALL = 1;
    public static final short CATEGORYBIT_FREE = 2;
    public static final short CATEGORYBIT_OTHER = 4;

    /* And what should collide with what. */
    public static final short MASKBITS_ALL = CATEGORYBIT_ALL + CATEGORYBIT_FREE + CATEGORYBIT_OTHER;
    public static final short MASKBITS_FREE = CATEGORYBIT_FREE; // Missing: CATEGORYBIT_CIRCLE
    public static final short MASKBITS_OTHER = CATEGORYBIT_ALL + CATEGORYBIT_OTHER; // Missing: CATEGORYBIT_BOX

    public static final FixtureDef ALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, CATEGORYBIT_ALL, MASKBITS_ALL, (short)0);
    public static final FixtureDef FREE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_FREE, MASKBITS_FREE, (short)0);
    public static final FixtureDef OTHER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_OTHER, MASKBITS_OTHER, (short)0);

}
