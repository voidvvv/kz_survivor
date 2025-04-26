package com.voidvvv.game.event;


import com.badlogic.ashley.core.Entity;

public interface VEvent {
    Entity getFrom();
    Entity getTo();

    void apply();
}
