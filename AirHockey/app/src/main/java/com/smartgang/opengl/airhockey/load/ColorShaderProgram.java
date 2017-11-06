package com.smartgang.opengl.airhockey.load;

import android.content.Context;
import com.smartgang.opengl.airhockey.program.ShaderProgram;

import static android.opengl.GLES30.glUniform4f;
import static android.opengl.GLES30.glUniformMatrix4fv;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetUniformLocation;

/**
 * Created by zhangxingang on 2017/10/9.
 * Email:zhangxg92@qq.com
 */

public class ColorShaderProgram extends ShaderProgram{
    private final int aPosition;
    private final int uColor;
    private final int aMatrix;
    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    private static final String A_MATRIX = "a_Matrix";
    public ColorShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        aPosition = glGetAttribLocation(program, A_POSITION);
        uColor = glGetUniformLocation(program, U_COLOR);
        aMatrix = glGetUniformLocation(program, A_MATRIX);
    }
    public int getPositionAttribLocation(){
        return aPosition;
    }

    public void setUniforms(float[] matrix, float r, float g, float b){
        glUniformMatrix4fv(aMatrix, 1, false, matrix, 0);
        glUniform4f(uColor, r, g, b, 1f);
    }
}
