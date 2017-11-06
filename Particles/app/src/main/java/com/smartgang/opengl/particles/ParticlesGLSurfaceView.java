package com.smartgang.opengl.particles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.smartgang.opengl.particles.render.ParticlesRender;

/**
 * Created by zhangxingang on 2017/10/16.
 * Email:zhangxg92@qq.com
 */

public class ParticlesGLSurfaceView extends GLSurfaceView{
    private ParticlesRender mParticlesRender;
    private Context mContext;
    public ParticlesGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        try{
            setEGLContextClientVersion(2);
            mParticlesRender = new ParticlesRender(mContext);
            setRenderer(mParticlesRender);
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }catch (Exception e){
            Log.e("MojingGLSurfaceView", "Unable to create GLSurfaceView context!", e);
            e.printStackTrace();
        }
    }
}
