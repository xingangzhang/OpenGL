package com.smartgang.opengl.heightmap.program;

import android.content.Context;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by zhangxingang on 2017/10/23.
 * Email:zhangxg92@qq.com
 */

public class HeigntmapShaderProgram extends ShaderProgram{
    private final String UMATRIX = "u_Matrix";
    private final int uMatrixLocation;
    private final String APOSITION = "a_Position";
    private final int aPositionLocation;

    public HeigntmapShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        aPositionLocation = glGetAttribLocation(program, APOSITION);
        uMatrixLocation = glGetUniformLocation(program, UMATRIX);
    }

    public void setUniforms(float[] matrix) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getaPositionLocation() {
        return aPositionLocation;
    }
}
