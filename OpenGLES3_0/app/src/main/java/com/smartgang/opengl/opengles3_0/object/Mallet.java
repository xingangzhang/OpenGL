package com.smartgang.opengl.opengles3_0.object;

import com.smartgang.opengl.opengles3_0.data.VertexArray;
import com.smartgang.opengl.opengles3_0.load.ColorShaderProgram;

import static android.opengl.GLES30.GL_POINTS;
import static android.opengl.GLES30.glDrawArrays;
import static com.smartgang.opengl.opengles3_0.Constant.BYTES_PER_FLOAT;

/**
 * Created by zhangxingang on 2017/9/30.
 * Email:zhangxg92@qq.com
 */

public class Mallet {
    private final static int POSITION_COMPONENT_COUNT = 2;
    private final static int COLOR_COMPONENT_COUNT = 3;
    private final static int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, R, G, B
            0f, -0.4f, 0f, 0f, 1f,
            0f,  0.4f, 1f, 0f, 0f };
    private final VertexArray vertexArray;

    public Mallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                colorProgram.getPositionAttribLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                colorProgram.getColorAttribLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, 2);
    }
}
