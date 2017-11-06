package com.smartgang.opengl.opengles3_0.load;

import android.content.Context;

import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

import static android.opengl.GLES30.GL_TEXTURE0;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.glActiveTexture;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glUniform1i;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetUniformLocation;
import static android.opengl.GLES30.glUniformMatrix4fv;

/**
 * Created by zhangxingang on 2017/10/9.
 * Email:zhangxg92@qq.com
 */

public class TextureShaderProgram extends ShaderProgram{
    private final int aPosition;
    private final int aTextureCoordinates;
    private final int aMatrix;
    private static final String A_POSITION = "a_Position";
    private static final String A_TEXTURECOORDINATES = "a_TextureCoordinates";
    private static final String A_MATRIX = "a_Matrix";
    public TextureShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        aPosition = glGetAttribLocation(program, A_POSITION);
        aTextureCoordinates = glGetAttribLocation(program, A_TEXTURECOORDINATES);
        aMatrix = glGetUniformLocation(program, A_MATRIX);
    }
    public int getPositionAttribLocation(){
        return aPosition;
    }

    public int getTextureCoordinatesAttribLocation(){
        return aTextureCoordinates;
    }

    public void setUniforms(float[] matrix, int textureId){
        // Pass the matrix into the shader program.
        glUniformMatrix4fv(aMatrix, 1, false, matrix, 0);

        // Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0);

        // Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        glUniform1i(aTextureCoordinates, 0);
    }
}
