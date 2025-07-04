package com.voidvvv.game.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.voidvvv.game.viewport.SubWindowViewport;

public class CameraManager implements BaseManager{
    OrthographicCamera mainCamera;
    OrthographicCamera uiCamera;

    PerspectiveCamera _3dCamera;
    Viewport worldViewPort;
    Viewport screenViewport;
    Viewport _3dViewPort;

    public CameraManager() {
        mainCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        _3dCamera = new PerspectiveCamera();
        worldViewPort = new StretchViewport(800, 600, mainCamera);
//        worldViewPort = new SubWindowViewport(0.1f, 0.1f, 0.5f, 0.5f, 800,600,mainCamera);
//        screenViewport = new ScreenViewport(uiCamera);
        screenViewport = new StretchViewport(800, 600, uiCamera);
        _3dViewPort = new StretchViewport(800, 600, _3dCamera);
    }

    @Override
    public void init() {
        mainCamera.setToOrtho(false, 800, 600);
        uiCamera.setToOrtho(false, 800, 600);
        _3dCamera.position.set(10f, 10f, 10f);
        _3dCamera.lookAt(0,0,0);
        _3dCamera.near = 0.1f;
        _3dCamera.far = 300f;
        _3dCamera.update();
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

    public Viewport get_3dViewPort () {
        return _3dViewPort;
    }

    public PerspectiveCamera get_3dCamera() {
        return _3dCamera;
    }

    public Viewport getScreenViewport() {
        return screenViewport;
    }

    @Override
    public void dispose() {
    }
}
