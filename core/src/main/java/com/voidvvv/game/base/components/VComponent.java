package com.voidvvv.game.base.components;

import com.badlogic.ashley.core.Component;

public interface VComponent extends Component {
    void init();

    void update(float delta);

    void dispose();


}
