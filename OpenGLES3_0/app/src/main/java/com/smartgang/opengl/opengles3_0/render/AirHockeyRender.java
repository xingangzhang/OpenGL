package com.smartgang.opengl.opengles3_0.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.smartgang.opengl.opengles3_0.R;
import com.smartgang.opengl.opengles3_0.load.ColorShaderProgram;
import com.smartgang.opengl.opengles3_0.load.ColorShaderProgram1;
import com.smartgang.opengl.opengles3_0.load.TextureShaderProgram;
import com.smartgang.opengl.opengles3_0.object.Mallet;
import com.smartgang.opengl.opengles3_0.object.MalletObject;
import com.smartgang.opengl.opengles3_0.object.Puck;
import com.smartgang.opengl.opengles3_0.object.Table;
import com.smartgang.opengl.opengles3_0.util.MatrixHelper;
import com.smartgang.opengl.opengles3_0.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

/**
 * Created by zhangxingang on 2017/10/10.
 * Email:zhangxg92@qq.com
 */

public class AirHockeyRender implements GLSurfaceView.Renderer{
    private Context mContext;
    private ColorShaderProgram1 mColorShaderProgram;
    private TextureShaderProgram mTextureShaderProgram;
    private Table mTable;
    private MalletObject mMallet;
    private Puck mPuck;
    private int mTextureObjectId;
    private float[] viewMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private float[] modelViewProjectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];


    public AirHockeyRender(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mColorShaderProgram = new ColorShaderProgram1(mContext,
                R.raw.color_vertex_shader, R.raw.color1_fragment_shader);
        mMallet = new MalletObject(0.08f, 0.15f, 32);
        mTextureShaderProgram = new TextureShaderProgram(mContext, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        mTextureObjectId = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);
        mTable = new Table();
        mPuck = new Puck(0.06f, 0.02f, 32);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 50, (float) width
                / (float) height, 1f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix,
                0, viewMatrix, 0);
//        translateM(mMvpMatrix, 0, 0f, 0f, -2.5f);
//        rotateM(mMvpMatrix, 0, -60f, 1f, 0f, 0f);
//        final float[] temp = new float[16];
//        multiplyMM(temp, 0, projectionMatrix, 0, mMvpMatrix, 0);
//        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        positionTableInScene();
        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(modelViewProjectionMatrix, mTextureObjectId);
        mTable.bindData(mTextureShaderProgram);
        mTable.draw();
        positionObjectInScene(0f, mPuck.mHeight / 2f, 0f);
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        mPuck.bindData(mColorShaderProgram);
        mPuck.draw();
        positionObjectInScene(0f, mMallet.mHeight / 2f, -0.4f);
        mColorShaderProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();
        positionObjectInScene(0f, mMallet.mHeight / 2f, 0.4f);
        mColorShaderProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();
    }
    private void positionTableInScene() {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to lie flat on the XZ plane.
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
}
