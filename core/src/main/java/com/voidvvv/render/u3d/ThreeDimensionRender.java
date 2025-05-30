package com.voidvvv.render.u3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.ScreenUtils;
import com.voidvvv.game.Main;
import com.voidvvv.game.screen.UpdateScreen;

public class ThreeDimensionRender implements UpdateScreen {
    ModelBatch modelBatch;

    ModelInstance instance;
    ModelInstance mi2;


    Environment environment;

    CameraInputController cameraInputController;
    Model model;
    Model model2;
    @Override
    public void show() {
        this.init();
    }

    public ThreeDimensionRender() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1.f));

        DirectionalLight d = new DirectionalLight();
        d.color.set(Color.WHITE);
        d.set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
        environment.add(d);
    }

    public void init () {

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5, 5, 5,
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        camera = Main.getInstance().getCameraManager().get_3dCamera();
        instance = new ModelInstance(model);
        modelBatch = new ModelBatch();
        ModelLoader loader = new ObjLoader();
        model2 = loader.loadModel(Gdx.files.internal("model/prop_floor_barrel.obj"));

        cameraInputController = new CameraInputController(Main.getInstance().getCameraManager().get_3dCamera());
        Main.getInstance().addInputProcessor(cameraInputController);

        mi2 = new ModelInstance(model2);
    }

    @Override
    public void render(float delta) {
        this.render();
    }

    @Override
    public void resize(int width, int height) {
        Main.getInstance().getCameraManager().get_3dViewPort().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Main.getInstance().removeInputProcessor(cameraInputController);
        model.dispose();
    }
    PerspectiveCamera camera;
    public void render () {
//        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        ScreenUtils.clear(0.3f, 0.3f, 0.3f, 1f);
//        instance.transform.rotate(1,1,1,0.1f);

        modelBatch.begin(camera);

//        modelBatch.render(instance, environment);
        modelBatch.render(mi2, environment);

        modelBatch.end();
    }

    @Override
    public void update(float delta) {
        cameraInputController.update();
    }
}
