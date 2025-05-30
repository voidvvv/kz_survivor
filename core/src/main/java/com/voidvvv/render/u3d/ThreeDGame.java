package com.voidvvv.render.u3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.voidvvv.game.Main;

public class ThreeDGame extends ApplicationAdapter {
    ModelBatch modelBatch;

    ModelInstance instance;

    Environment environment;

    CameraInputController cameraInputController;

    PerspectiveCamera camera;


    public void show() {
    }

    public void init() {

        ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createBox(5, 5, 5,
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        {
            camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camera.position.set(10, 10, 10);
            camera.lookAt(0, 0, 0);
            camera.near = 1f;
            camera.far = 300f;
            camera.update();
        }
        instance = new ModelInstance(model);
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1.f));

        DirectionalLight d = new DirectionalLight();
        d.color.set(Color.WHITE);
        d.set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
        environment.add(d);

        cameraInputController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraInputController);
    }

    @Override
    public void create() {
        this.init();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        cameraInputController.update();
        modelBatch.begin(camera);
        //modelBatch.render( instance );
        modelBatch.render(instance, environment);
        modelBatch.end();
    }
}
