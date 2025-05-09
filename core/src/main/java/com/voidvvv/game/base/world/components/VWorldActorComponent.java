package com.voidvvv.game.base.world.components;

import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.components.VComponent;
import com.voidvvv.game.base.world.VWorldActor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VWorldActorComponent implements VComponent {
    public List<VWorldActor> actors;
    public List<VWorldActor> toAdd;
    public List<VWorldActor> toRemove;

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
        flushActors();
        actors.forEach(actor -> {
            if (actor != null) {
                actor.update(delta);
            }
        });
    }

    public List<? extends VWorldActor> allActors() {
        return actors;
    }


    private void flushActors() {
        for (VWorldActor actor : toRemove) {
            actor.dispose();
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
        disposeActors();
    }

    private void disposeActors() {
        Gdx.app.log("VWorldActorComponent", "emptyIterator actor");
        emptyIterator(actors);
        Gdx.app.log("VWorldActorComponent", "emptyIterator toAdd");

        emptyIterator(toAdd);
        Gdx.app.log("VWorldActorComponent", "emptyIterator toRemove");

        toRemove.clear(); // to remove actor is already in actors. so there is no need to dispose them repeatedly.
        actors = null;
        toAdd = null;
        toRemove = null;
    }

    private void emptyIterator (Iterable<? extends VActor> iterable) {
        Iterator<? extends VActor> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            iterator.next().dispose();
            iterator.remove();
        }
    }

    public void resetActor(VWorldActor actor) {
        toRemove.add(actor);
    }

    public void addActor (VWorldActor actor) {
        if (actor != null) {
            toAdd.add(actor);
        }
    }
}
