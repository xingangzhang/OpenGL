package com.smartgang.opengl.opengles3_0.object;

import com.smartgang.opengl.opengles3_0.data.VertexArray;
import com.smartgang.opengl.opengles3_0.load.AirHockeyTextureProgram;
import com.smartgang.opengl.opengles3_0.load.TextureShaderProgram;
import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static com.smartgang.opengl.opengles3_0.Constant.BYTES_PER_FLOAT;
import static com.smartgang.opengl.opengles3_0.Constant.TEXTURE_COORDINATES_COMPONENT_COUNT;

/**
 * Created by zhangxingang on 2017/9/30.
 * Email:zhangxg92@qq.com
 */

public class Table{
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT
            + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, S, T

            // Triangle Fan
            0f,    0f, 0.5f, 0.5f,
            -0.5f, -0.8f,   0f, 0.9f,
            0.5f, -0.8f,   1f, 0.9f,
            0.5f,  0.8f,   1f, 0.1f,
            -0.5f,  0.8f,   0f, 0.1f,
            -0.5f, -0.8f,   0f, 0.9f };

    private final VertexArray vertexArray;

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttribLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatesAttribLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
