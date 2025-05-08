package com.voidvvv.game.manager;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;

public class BehaviorTreeManager {
    BehaviorTreeLibrary baseLibrary;

    public BehaviorTreeLibrary getBaseLibrary() {
        return baseLibrary;
    }

    public void setBaseLibrary(BehaviorTreeLibrary baseLibrary) {
        this.baseLibrary = baseLibrary;
    }

    public void init () {
        baseLibrary = new BehaviorTreeLibrary();
        baseLibrary.registerArchetypeTree("", new BehaviorTree<>());
    }
}
