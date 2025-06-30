package com.voidvvv.game.actor.items;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.ecs.components.ContactTypeComponent;
import com.voidvvv.game.ecs.components.MoveComponent;
import com.voidvvv.game.ecs.components.sign.DropSignComponent;

public class DropItem extends VWorldActor {

    @Override
    public void init() {
        super.init();
        ContactTypeComponent contactType = Pools.obtain(ContactTypeComponent.class);
        contactType.type = ContactTypeComponent.ITEM;
        this.entity.add(contactType);
//        this.entity.add(Pools.obtain(MoveComponent.class));
//        this.entity.add(Pools.obtain(VRectaaaaaaBoundComponent.class));
        this.entity.add(new DropSignComponent());
    }
}
