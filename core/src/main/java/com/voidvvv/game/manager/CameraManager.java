package com.voidvvv.game.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;

public class CameraManager implements BaseManager{
    OrthographicCamera mainCamera;
    OrthographicCamera uiCamera;

    Viewport worldViewPort;
    Viewport screenViewport;

    public CameraManager() {
        mainCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        worldViewPort = new StretchViewport(800, 600, mainCamera);
//        screenViewport = new ScreenViewport(uiCamera);
        screenViewport = new StretchViewport(800, 600, uiCamera);
    }

    @Override
    public void init() {
        mainCamera.setToOrtho(false, 800, 600);
        uiCamera.setToOrtho(false, 800, 600);
    }

    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public Viewport getWorldViewPort() {
        return worldViewPort;
    }

    public Viewport getScreenViewport() {
        return screenViewport;
    }

    @Override
    public void dispose() {
    }
}
