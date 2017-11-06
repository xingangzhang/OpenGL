package com.smartgang.opengl.opengles3_0.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.smartgang.opengl.opengles3_0.R;
import com.smartgang.opengl.opengles3_0.load.ScreenCubemapProgram;
import com.smartgang.opengl.opengles3_0.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES30.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES30.GL_CULL_FACE;
import static android.opengl.GLES30.GL_FRONT;
import static android.opengl.GLES30.glClear;
import static android.opengl.GLES30.glClearColor;
import static android.opengl.GLES30.glCullFace;
import static android.opengl.GLES30.glDisable;
import static android.opengl.GLES30.glEnable;
import static android.opengl.GLES30.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.perspectiveM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

/**
 * Created by zhangxingang on 2017/9/27.
 * Email:zhangxg92@qq.com
 */

public class SmartGLSurfaceViewRenderScreen implements GLSurfaceView.Renderer{
    private Context mContext;
    private ScreenCubemapProgram mCubemapProgram;
    private float[] mMvpMatrix = new float[16];
    private int mTextureId;
    private int mThisWindowWidth = 0;
    private int mThisWindowHeight = 0;
    private float xRotation;
    private float yRotation;

    public SmartGLSurfaceViewRenderScreen(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        mCubemapProgram = new ScreenCubemapProgram(mContext, R.raw.screen_vertex_shader, R.raw.screen_fragment_shader);
        mCubemapProgram.useProgram();
        int[] bitmaps = {
                R.drawable.my,
                R.drawable.my,
                R.drawable.my,
                R.drawable.my,
                R.drawable.my,
                R.drawable.my
        };
        mTextureId = TextureHelper.loadCubeMap(mContext, bitmaps);
        setIdentityM(mMvpMatrix, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        mThisWindowWidth = width;
        mThisWindowHeight = height;
        //setLookAtM(mMvpMatrix ,0,0,0,0,0,0,-1f,0,1,0);
        perspectiveM(mMvpMatrix, 0, 45, (float) width / height, 0.1f, 100f);
        translateM(mMvpMatrix, 0, 0f, 0f, -10);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClearColor(0.7f,0.7f,0.7f,0);
        glClear(GL_COLOR_BUFFER_BIT);
        drawSkybox();
    }

    private void drawSkybox() {
        glViewport(mThisWindowWidth / 2 - 600, mThisWindowHeight / 2 - 600, 1200, 1200);
        float[] mViewProjectionMatrix = new float[16];
        float[] mViewMatrix = new float[16];
        setIdentityM(mViewMatrix, 0);
        rotateM(mViewMatrix, 0, -yRotation, 1f, 0f, 0f);
        rotateM(mViewMatrix, 0, -xRotation, 0f, 1f, 0f);
        multiplyMM(mViewProjectionMatrix, 0, mMvpMatrix, 0, mViewMatrix, 0);
        mCubemapProgram.setUniforms(mTextureId, mViewProjectionMatrix);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_FRONT);
        mCubemapProgram.draw(mTextureId);
        glDisable(GL_CULL_FACE);
    }

    public void handleTouchDrag(float deltaX, float deltaY) {
        xRotation += deltaX / 16f;
        yRotation += deltaY / 16f;
        if (yRotation < -360) {
            yRotation %= 360;
        } else if (yRotation > 360) {
            yRotation %= 360;
        }
    }
}
