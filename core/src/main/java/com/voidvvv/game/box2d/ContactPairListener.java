package com.voidvvv.game.box2d;

/**
 * ContactPairListener is an interface for listening to contact pairs in a Box2D world.
 */
public interface ContactPairListener {
    /**
     * This method is called when a contact pair is created or destroyed.
     * the pair is created when two bodies start touching each other, pair contains this fixture and other fixture
     * this fixture has user data VACtor
     * other fixture should have user data VActor or other object
     * @param pair the CollisionPair object containing the two fixtures involved in the contact
     * @param b true if the contact pair is created (bodies are touching), false if the contact pair is destroyed (bodies are no longer touching)
     */
    void contact(CollisionPair pair, boolean b);
}
