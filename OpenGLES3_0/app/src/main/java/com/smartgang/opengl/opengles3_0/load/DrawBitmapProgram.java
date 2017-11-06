package com.smartgang.opengl.opengles3_0.load;

import android.content.Context;

import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_TEXTURE0;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.GL_TRIANGLES;
import static android.opengl.GLES30.GL_UNSIGNED_BYTE;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glClear;
import static android.opengl.GLES30.glClearColor;
import static android.opengl.GLES30.glDrawElements;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glUniform1i;
import static android.opengl.GLES30.glUniformMatrix4fv;
import static android.opengl.GLES30.glVertexAttribPointer;
import static android.opengl.GLES30.glActiveTexture;
import static android.opengl.GLES30.glGetUniformLocation;
import static android.opengl.GLES30.glGetAttribLocation;

/**
 * Created by zhangxingang on 2017/9/26.
 */

public class DrawBitmapProgram extends ShaderProgram {
    private final static String V_POSITION = "v_Position";
    private final static String A_TEXCOORD = "a_texCoord";
    private final static String S_TEXTURE = "s_texture";
    private final static String U_MATRIX = "u_Matrix";
    private float VERTEX[] = {
            1, 1,  // top right
            -1, 1,// top left
            -1, -1,// bottom left
            1, -1 // bottom right
    };
    private float U_VERTEX[] = {
            1, 0,  // bottom right
            0, 0,  // bottom left
            0, 1,  // top left
            1, 1,  // top right
    };
    private byte INDEX[] = {
            0, 1, 2, 0, 2, 3
    };
    private FloatBuffer vertexFloatBuffer;
    private FloatBuffer u_vertexFloatBuffer;
    private ByteBuffer indexByteBuffer;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private final int uTextureUnitLocation;
    private final int aPositionLocation;
    private final int aTexCoordLocation;
    private final int uMatrixLocation;

    public DrawBitmapProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        vertexFloatBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX);
        vertexFloatBuffer.position(0);
        u_vertexFloatBuffer = ByteBuffer.allocateDirect(U_VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(U_VERTEX);
        u_vertexFloatBuffer.position(0);
        indexByteBuffer = ByteBuffer.allocateDirect(INDEX.length)
                .order(ByteOrder.nativeOrder())
                .put(INDEX);
        indexByteBuffer.position(0);
        aPositionLocation = glGetAttribLocation(program, V_POSITION);
        aTexCoordLocation = glGetAttribLocation(program, A_TEXCOORD);
        uTextureUnitLocation = glGetUniformLocation(program, S_TEXTURE);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
    }
    public void setUniforms(int textureId) {
        glUniform1i(uTextureUnitLocation, 0);
        vertexFloatBuffer.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexFloatBuffer);
        glEnableVertexAttribArray(aPositionLocation);
        u_vertexFloatBuffer.position(0);
        glVertexAttribPointer(aTexCoordLocation, 2, GL_FLOAT, false, 0, u_vertexFloatBuffer);
        glEnableVertexAttribArray(aTexCoordLocation);
    }

    public void setUniforms(int textureId, float[] mMVPMatrix) {
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mMVPMatrix, 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);

        vertexFloatBuffer.position(0);
        glVertexAttribPointer(aPositionLocation, 3, GL_FLOAT, false, 0, vertexFloatBuffer);
        glEnableVertexAttribArray(aPositionLocation);
        u_vertexFloatBuffer.position(0);
        glVertexAttribPointer(aTexCoordLocation, 2, GL_FLOAT, false, 0, u_vertexFloatBuffer);
        glEnableVertexAttribArray(aTexCoordLocation);
    }

    public void draw(int textureId) {
        glBindTexture(GL_TEXTURE_2D, textureId);
        glClearColor(0.7f, 0.7f, 0.7f, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        glDrawElements(GL_TRIANGLES, INDEX.length, GL_UNSIGNED_BYTE, indexByteBuffer);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}
