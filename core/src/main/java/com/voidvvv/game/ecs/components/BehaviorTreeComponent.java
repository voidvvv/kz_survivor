package com.voidvvv.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class BehaviorTreeComponent implements Component, Pool.Poolable {
    public BehaviorTree tree;
    @Override
    public void reset() {
        if (tree != null) {
            Pools.free(tree);
            tree = null;
        }
    }
}
