package com.voidvvv.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class ShaderTest implements ApplicationListener {
    ShaderProgram shaderProgram;
    Mesh mesh;
    OrthographicCamera camera;

//    float[] indices;
    @Override
    public void create() {
        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/test1/test1.vert"), Gdx.files.internal("shaders/test1/test1.frag"));
        mesh = new Mesh(false, 4, // 顶点数量，1就是vertices中只能存在一个顶点，无法构图，最少要3个，为了缓存可以多设置一些
            6, VertexAttribute.Position()
        );
//        indices = new float[6];
        mesh.setVertices(new float[] {
            0f, 0f, 0,  // 左下角
            200f, 0f, 0,   // 右下角
            200f, 200f, 0,    // 右上角
            20f, 200f, 0     // 左上角
        });
        mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});

        mesh.enableInstancedRendering(false, 10, new VertexAttribute(1000,  3 , "a_instance_offset"));
        mesh.setInstanceData(new float[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30});
        camera = new OrthographicCamera();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    float time;
    float[] floats = new float[3];
    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1); // 清屏，设置背景色为黑色
        shaderProgram.bind();
        // 设置shader program的uniform变量
        shaderProgram.setUniformMatrix("u_projTrans" , camera.combined);
        // iResolution  iTime
        floats[0] = 600;
        floats[1] = 480;
        floats[2] = 1;
        shaderProgram.setUniform3fv("iResolution", floats, 0, 3);
        time += Gdx.graphics.getDeltaTime();
        shaderProgram.setUniformf("iTime", time);
        // 设置mesh数组
//        mesh.setVertices()

        mesh.render(shaderProgram, GL20.GL_TRIANGLES);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
