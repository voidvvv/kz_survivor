package com.voidvvv.game.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class VBox2dComponent {

    Body flatBody;

    Fixture bottomFixture;

    Fixture faceFixture;

    public Fixture getFaceFixture() {
        return faceFixture;
    }

    public void setFaceFixture(Fixture faceFixture) {
        this.faceFixture = faceFixture;
    }

    public Fixture getBottomFixture() {
        return bottomFixture;
    }

    public void setBottomFixture(Fixture bottomFixture) {
        this.bottomFixture = bottomFixture;
    }

    public Body getFlatBody() {
        return flatBody;
    }

    public void setFlatBody(Body flatBody) {
        this.flatBody = flatBody;
    }

    public void dispose () {
        this.flatBody.getWorld().destroyBody(this.flatBody);
    }
}
