package com.smartgang.opengl.opengles3_0.load;

import android.content.Context;

import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_TEXTURE_CUBE_MAP;
import static android.opengl.GLES30.GL_TRIANGLES;
import static android.opengl.GLES30.GL_UNSIGNED_BYTE;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glDrawElements;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetUniformLocation;
import static android.opengl.GLES30.glUniform1i;
import static android.opengl.GLES30.glUniformMatrix4fv;
import static android.opengl.GLES30.glVertexAttribPointer;

/**
 * Created by zhangxingang on 2017/9/27.
 * Email:zhangxg92@qq.com
 */

public class ScreenCubemapProgram extends ShaderProgram {
    private final static int FLOATSIZE = 4;
    private final String V_POSITION = "v_Position";
    private final String U_MATRIX = "u_Matrix";
    private final String U_TEXTUREUNIT = "u_TextureUnit";
    private final int vPositionLocation;
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;
    private float VERTEX[] = {
            1, 1, 1, // top right near
            -1, 1, 1,// top left near
            -1, -1, 1,// bottom left near
            1, -1, 1,// bottom right near
            1, 1, -1, // top right far
            -1, 1, -1,// top left far
            -1, -1, -1,// bottom left far
            1, -1, -1,// bottom right far
    };

    private byte INDEX[] = {
            0, 1, 2, 0, 2, 3,//front
            5, 4, 7, 5, 7, 6,//back
            1, 5, 6, 1, 6, 2,//left
            4, 0, 3, 4, 3, 7,//right
            4, 5, 1, 4, 1, 0,//top
            3, 2, 6, 3, 6, 7//bottom
    };
    private FloatBuffer vertexFloatBuffer;
    private ByteBuffer indexByteBuffer;

    public ScreenCubemapProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        vertexFloatBuffer = ByteBuffer.allocateDirect(VERTEX.length * FLOATSIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX);
        vertexFloatBuffer.position(0);
        indexByteBuffer = ByteBuffer.allocateDirect(INDEX.length)
                .order(ByteOrder.nativeOrder())
                .put(INDEX);
        indexByteBuffer.position(0);
        vPositionLocation = glGetAttribLocation(program, V_POSITION);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTUREUNIT);
    }
    public void setUniforms(int textureId, float[] matrix) {
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureId);
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform1i(uTextureUnitLocation, 0);
        vertexFloatBuffer.position(0);
        glVertexAttribPointer(vPositionLocation, 3, GL_FLOAT, false, 0, vertexFloatBuffer);
        glEnableVertexAttribArray(vPositionLocation);
    }
    public void draw(int textureId) {
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureId);
        glDrawElements(GL_TRIANGLES, INDEX.length, GL_UNSIGNED_BYTE, indexByteBuffer);
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }
}
