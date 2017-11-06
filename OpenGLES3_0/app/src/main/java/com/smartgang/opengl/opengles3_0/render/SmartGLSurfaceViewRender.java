package com.smartgang.opengl.opengles3_0.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.smartgang.opengl.opengles3_0.R;
import com.smartgang.opengl.opengles3_0.load.DrawBitmapProgram;
import com.smartgang.opengl.opengles3_0.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glViewport;

/**
 * Created by zhangxingang on 2017/9/26.
 */

public class SmartGLSurfaceViewRender implements GLSurfaceView.Renderer {
    private Context mContext;
    private DrawBitmapProgram mDrawBitmapProgram;
    private int mTextureId;
    private int mThisWindowWidth = 0;
    private int mThisWindowHeight = 0;

    public SmartGLSurfaceViewRender(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        mDrawBitmapProgram = new DrawBitmapProgram(mContext, R.raw.draw_bitmap_vertex_shader, R.raw.draw_bitmap_fragment_shader);
        mDrawBitmapProgram.useProgram();
        mTextureId = TextureHelper.loadTexture(mContext, R.drawable.my);
        mDrawBitmapProgram.setUniforms(mTextureId);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        mThisWindowWidth = width;
        mThisWindowHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glViewport(mThisWindowWidth / 2 - 200, mThisWindowHeight / 2 - 200, 400, 400);
        mDrawBitmapProgram.draw(mTextureId);
    }
}
