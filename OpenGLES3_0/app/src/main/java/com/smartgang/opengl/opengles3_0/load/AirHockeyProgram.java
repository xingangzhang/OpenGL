package com.smartgang.opengl.opengles3_0.load;

import android.content.Context;
import android.opengl.GLES30;

import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_LINES;
import static android.opengl.GLES30.GL_POINTS;
import static android.opengl.GLES30.GL_TRIANGLES;
import static android.opengl.GLES30.GL_TRIANGLE_FAN;
import static android.opengl.GLES30.glDrawArrays;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glGetUniformLocation;
import static android.opengl.GLES30.glUniform4f;
import static android.opengl.GLES30.glVertexAttribPointer;
import static android.opengl.GLES30.glGetAttribLocation;

/**
 * Created by zhangxingang on 2017/9/28.
 * Email:zhangxg92@qq.com
 */

public class AirHockeyProgram extends ShaderProgram{
    private final static int FLOATSIZE = 4;
    private final static int POSITION_COMPONENT_COUNT = 2;
    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    private final int aPositionLocation;
    private final int uColorLocation;
    private float VERTEX[] = {
            // Triangle 1
            -0.5f, -0.5f,
            0.5f,  0.5f,
            -0.5f,  0.5f,

            // Triangle 2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f,  0.5f,

            // Line 1
            -0.5f, 0f,
            0.5f, 0f,

            // Mallets
            0f, -0.25f,
            0f,  0.25f
    };
    private FloatBuffer vertexFloatBuffer;
    public AirHockeyProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        vertexFloatBuffer = ByteBuffer.allocateDirect(VERTEX.length * FLOATSIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX);
        vertexFloatBuffer.position(0);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
    }
    public void setUniforms() {
        vertexFloatBuffer.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexFloatBuffer);
        glEnableVertexAttribArray(aPositionLocation);
    }
    public void draw() {
        // Draw the table.
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        // Draw the center dividing line.
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);

        // Draw the first mallet blue.
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

        // Draw the second mallet red.
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
