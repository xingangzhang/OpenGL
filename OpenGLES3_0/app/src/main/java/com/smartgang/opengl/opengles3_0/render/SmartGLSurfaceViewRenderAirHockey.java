package com.smartgang.opengl.opengles3_0.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.smartgang.opengl.opengles3_0.R;
import com.smartgang.opengl.opengles3_0.load.AirHockey3DProgram;
import com.smartgang.opengl.opengles3_0.load.AirHockeyOrthoProgram;
import com.smartgang.opengl.opengles3_0.load.AirHockeyProgram;
import com.smartgang.opengl.opengles3_0.load.AirHockeyProgram1;
import com.smartgang.opengl.opengles3_0.load.ColorShaderProgram;
import com.smartgang.opengl.opengles3_0.load.TextureShaderProgram;
import com.smartgang.opengl.opengles3_0.object.Mallet;
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
import static android.opengl.Matrix.orthoM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by zhangxingang on 2017/9/28.
 * Email:zhangxg92@qq.com
 */

public class SmartGLSurfaceViewRenderAirHockey implements GLSurfaceView.Renderer{
    private Context mContext;
    private AirHockeyProgram mAirHockeyProgram;
    private AirHockeyProgram1 mAirHockeyProgram1;
    private AirHockeyOrthoProgram mAirHockeyOrthoProgram;
    private AirHockey3DProgram mAirHockey3DProgram;
    private ColorShaderProgram mColorShaderProgram;
    private TextureShaderProgram mTextureShaderProgram;
    private Mallet mMallet;
    private Table mTable;
    private float[] mMvpMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private int mTextureObjectId;


    public SmartGLSurfaceViewRenderAirHockey(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        setIdentityM(mMvpMatrix, 0);
        setIdentityM(projectionMatrix, 0);
        //AirHockeyProgram
//        mAirHockeyProgram = new AirHockeyProgram(mContext, R.raw.airhockey_vertex_shader, R.raw.airhockey_fragment_shader);
//        mAirHockeyProgram.useProgram();
//        mAirHockeyProgram.setUniforms();
        //mAirHockeyProgram1
//        mAirHockeyProgram1 = new AirHockeyProgram1(mContext, R.raw.airhockey1_vertex_shader, R.raw.airhockey1_fragment_shader);
//        mAirHockeyProgram1.useProgram();
//        mAirHockeyProgram1.setUniforms();
        //mAirHockeyOrthoProgram
//        mAirHockeyOrthoProgram = new AirHockeyOrthoProgram(mContext,
//                R.raw.airhockey_ortho_vertex_shader, R.raw.airhockey_ortho_fragment_shader);
//        mAirHockeyOrthoProgram.useProgram();
//        mAirHockeyOrthoProgram.setUniforms();
        //mAirHockey3DProgram
//        mAirHockey3DProgram = new AirHockey3DProgram(mContext,
//                R.raw.airhockey_ortho_vertex_shader, R.raw.airhockey_ortho_fragment_shader);
//        mAirHockey3DProgram.useProgram();
//        mAirHockey3DProgram.setUniforms();
        mColorShaderProgram = new ColorShaderProgram(mContext,
                R.raw.color_vertex_shader, R.raw.color_fragment_shader);
        mMallet = new Mallet();
        mTextureShaderProgram = new TextureShaderProgram(mContext, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        mTable = new Table();
        mTextureObjectId = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 50, (float) width
                / (float) height, 1f, 10f);
        translateM(mMvpMatrix, 0, 0f, 0f, -2.5f);
        rotateM(mMvpMatrix, 0, -60f, 1f, 0f, 0f);
        final float[] temp = new float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, mMvpMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        //mAirHockeyProgram.draw();
        //mAirHockeyProgram1.draw();
        //mAirHockey3DProgram.draw(projectionMatrix);
        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(projectionMatrix, mTextureObjectId);
        mTable.bindData(mTextureShaderProgram);
        mTable.draw();
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(projectionMatrix);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();
    }
}
