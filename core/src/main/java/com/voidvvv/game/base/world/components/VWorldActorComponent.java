package com.voidvvv.game.base.world.components;

import com.voidvvv.game.base.VComponent;
import com.voidvvv.game.base.world.VWorldActor;

import java.util.ArrayList;
import java.util.List;

public class VWorldActorComponent implements VComponent {
    List<VWorldActor> actors;
    List<VWorldActor> toAdd;
    List<VWorldActor> toRemove;

    @Override
    public void init() {
        initActorList();
    }
    private void initActorList() {
        actors = new ArrayList<>();
        toAdd = new ArrayList<>();
        toRemove = new ArrayList<>();
    }
    @Override
    public void update(float delta) {

    }

    public List<? extends VWorldActor> allActors() {
        return actors;
    }


    private void flushActors() {
        for (VWorldActor actor : toRemove) {
            actors.remove(actor);
        }
        toRemove.clear();
        for (VWorldActor actor : toAdd) {
            actor.init();
            actors.add(actor);
        }
        toAdd.clear();
    }

    @Override
    public void dispose() {

    }

    private void disposeActors() {
        actors.clear();
        toAdd.clear();
        toRemove.clear();
    }

    public void resetActor(VWorldActor actor) {
        toRemove.add(actor);
    }
}
