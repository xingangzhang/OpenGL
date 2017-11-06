package com.smartgang.opengl.heightmap.load;

import android.content.Context;
import android.opengl.GLES30;

import com.smartgang.opengl.heightmap.program.ShaderProgram;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by zhangxingang on 2017/10/16.
 * Email:zhangxg92@qq.com
 */

public class ParticlesShaderProgram extends ShaderProgram{
    private final String APOSITION = "a_Position";
    private final String AOLOR = "a_Color";
    private final String ADIRECTIONVECTOR = "a_DirectionVector";
    private final String APARTICLESTARTTIME = "a_ParticleStartTime";
    private final String UMATRIX = "u_Matrix";
    private final String UTIME = "u_Time";
    private final String UTEXTUREUNIT = "u_TextureUnit";
    private final int aPosition;
    private final int aColor;
    private final int aDirectionVector;
    private final int aParticleStartTime;
    private final int uMatrix;
    private final int uTime;
    private final int uTextureUnit;
    public ParticlesShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        aPosition = glGetAttribLocation(program, APOSITION);
        aColor = glGetAttribLocation(program, AOLOR);
        aDirectionVector = glGetAttribLocation(program, ADIRECTIONVECTOR);
        aParticleStartTime = glGetAttribLocation(program, APARTICLESTARTTIME);
        uMatrix = glGetUniformLocation(program, UMATRIX);
        uTime = glGetUniformLocation(program, UTIME);
        uTextureUnit = glGetUniformLocation(program, UTEXTUREUNIT);
    }

    public void setUniforms(float[] matrix, float elapsedTime, int textureId) {
        glUniformMatrix4fv(uMatrix, 1, false, matrix, 0);
        glUniform1f(uTime, elapsedTime);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnit, 0);
    }


    public int getPositionAttrib() {
        return aPosition;
    }

    public int getColorAttrib() {
        return aColor;
    }

    public int getDirectionVectorAttrib() {
        return aDirectionVector;
    }

    public int getParticleStartTimeAttrib() {
        return aParticleStartTime;
    }
}
