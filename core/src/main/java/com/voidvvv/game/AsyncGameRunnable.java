package com.voidvvv.game;

import com.badlogic.gdx.Gdx;
import com.voidvvv.game.Main;

public class AsyncGameRunnable implements Runnable{


    Main mainGame;

    public AsyncGameRunnable(Main mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mainGame.update();
            } catch (Exception e) {
                Gdx.app.error("AsyncGameRunnable", "Error in game loop", e);
            }
            try {
                Thread.sleep(16); // Sleep for 16ms to limit the frame rate to ~60 FPS
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
