package com.voidvvv.game.event;


import com.badlogic.ashley.core.Entity;
import com.voidvvv.game.base.VActor;

public interface VEvent {
    VActor getFrom();
    VActor getTo();

    void apply();
}
