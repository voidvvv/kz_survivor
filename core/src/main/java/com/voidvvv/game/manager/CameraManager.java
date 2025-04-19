package com.voidvvv.game.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraManager implements BaseManager{
    OrthographicCamera mainCamera;
    OrthographicCamera uiCamera;
    @Override
    public void init() {
        mainCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, 800, 600);

        uiCamera.setToOrtho(false, 800, 600);
    }

    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    @Override
    public void dispose() {
        if (mainCamera != null) {
            mainCamera = null;
        }
        if (uiCamera != null) {
            uiCamera = null;
        }
    }
}
