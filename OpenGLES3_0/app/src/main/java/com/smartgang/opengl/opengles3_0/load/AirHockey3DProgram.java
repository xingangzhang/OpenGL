package com.smartgang.opengl.opengles3_0.load;

import android.content.Context;

import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_LINES;
import static android.opengl.GLES30.GL_POINTS;
import static android.opengl.GLES30.GL_TRIANGLE_FAN;
import static android.opengl.GLES30.glDrawArrays;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetUniformLocation;
import static android.opengl.GLES30.glUniformMatrix4fv;
import static android.opengl.GLES30.glVertexAttribPointer;

/**
 * Created by zhangxingang on 2017/9/29.
 * Email:zhangxg92@qq.com
 */

public class AirHockey3DProgram extends ShaderProgram {
    private final static int BYTES_PER_FLOAT = 4;
    private final static int POSITION_COMPONENT_COUNT = 2;
    private final static int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final String A_POSITION = "a_Position";
    private static final String A_COLOR = "a_Color";
    private static final String A_MATRIX = "a_Matrix";
    private final int aPositionLocation;
    private final int aColorLocation;
    private final int aMatrixLocation;
    /*
            float[] VERTEX = {
                // Order of coordinates: X, Y, Z, W, R, G, B

                // Triangle Fan
                   0f,    0f, 0f, 1.5f,   1f,   1f,   1f,
                -0.5f, -0.8f, 0f,   1f, 0.7f, 0.7f, 0.7f,
                 0.5f, -0.8f, 0f,   1f, 0.7f, 0.7f, 0.7f,
                 0.5f,  0.8f, 0f,   2f, 0.7f, 0.7f, 0.7f,
                -0.5f,  0.8f, 0f,   2f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0f,   1f, 0.7f, 0.7f, 0.7f,

                // Line 1
                -0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,
                 0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,

                // Mallets
                0f, -0.4f, 0f, 1.25f, 0f, 0f, 1f,
                0f,  0.4f, 0f, 1.75f, 1f, 0f, 0f
            };
            */
    private float VERTEX[] = {
            // Order of coordinates: X, Y, R, G, B

            // Triangle Fan
            0f, 0f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

            // Line 1
            -0.5f, 0f, 1f, 0f, 0f,
            0.5f, 0f, 1f, 0f, 0f,

            // Mallets
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    };
    private FloatBuffer vertexFloatBuffer;

    public AirHockey3DProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        vertexFloatBuffer = ByteBuffer.allocateDirect(VERTEX.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX);
        vertexFloatBuffer.position(0);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
        aMatrixLocation = glGetUniformLocation(program, A_MATRIX);
    }

    public void setUniforms() {
        vertexFloatBuffer.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexFloatBuffer);
        glEnableVertexAttribArray(aPositionLocation);
        vertexFloatBuffer.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexFloatBuffer);
        glEnableVertexAttribArray(aColorLocation);
    }

    public void draw(float[] projectionMatrix) {

        // Assign the matrix
        glUniformMatrix4fv(aMatrixLocation, 1, false, projectionMatrix, 0);

        // Draw the table.
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // Draw the center dividing line.
        glDrawArrays(GL_LINES, 6, 2);

        // Draw the first mallet blue.
        glDrawArrays(GL_POINTS, 8, 1);

        // Draw the second mallet red.
        glDrawArrays(GL_POINTS, 9, 1);
    }
}