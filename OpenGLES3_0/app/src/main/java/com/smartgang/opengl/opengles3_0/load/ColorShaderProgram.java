package com.smartgang.opengl.opengles3_0.load;

import android.content.Context;
import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetUniformLocation;

/**
 * Created by zhangxingang on 2017/10/9.
 * Email:zhangxg92@qq.com
 */

public class ColorShaderProgram extends ShaderProgram{
    private final int aPosition;
    private final int aColor;
    private final int aMatrix;
    private static final String A_POSITION = "a_Position";
    private static final String A_COLOR = "a_Color";
    private static final String A_MATRIX = "a_Matrix";
    public ColorShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        aPosition = glGetAttribLocation(program, A_POSITION);
        aColor = glGetAttribLocation(program, A_COLOR);
        aMatrix = glGetUniformLocation(program, A_MATRIX);
    }
    public int getPositionAttribLocation(){
        return aPosition;
    }

    public int getColorAttribLocation(){
        return aColor;
    }

    public void setUniforms(float[] matrix){
        glUniformMatrix4fv(aMatrix, 1, false, matrix, 0);
    }

}
