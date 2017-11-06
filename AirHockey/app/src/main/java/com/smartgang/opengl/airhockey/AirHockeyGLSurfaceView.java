package com.smartgang.opengl.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.smartgang.opengl.airhockey.render.AirHockeyRender;

/**
 * Created by zhangxingang on 2017/10/11.
 * Email:zhangxg92@qq.com
 */

public class AirHockeyGLSurfaceView extends GLSurfaceView {
    private AirHockeyRender mAirHockeyRender;
    private Context mContext;
    public AirHockeyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        try{
            setEGLContextClientVersion(2);
            mAirHockeyRender = new AirHockeyRender(mContext);
            setRenderer(mAirHockeyRender);
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }catch (Exception e){
            Log.e("MojingGLSurfaceView", "Unable to create GLSurfaceView context!", e);
            e.printStackTrace();
        }
    }
    public void handleTouchDrag(float normalizadX, float normalizadY) {
        mAirHockeyRender.handleTouchDrag(normalizadX, normalizadY);
    }

    public void handleTouchPress(float normalizadX, float normalizadY) {
        mAirHockeyRender.handleTouchPress(normalizadX, normalizadY);
    }
}
