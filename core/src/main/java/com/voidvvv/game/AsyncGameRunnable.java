package com.voidvvv.game;

import com.voidvvv.game.Main;

public class AsyncGameRunnable implements Runnable{


    Main mainGame;

    public AsyncGameRunnable(Main mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void run() {
        while (true) {
            mainGame.update();
            try {
                Thread.sleep(16); // Sleep for 16ms to limit the frame rate to ~60 FPS
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
