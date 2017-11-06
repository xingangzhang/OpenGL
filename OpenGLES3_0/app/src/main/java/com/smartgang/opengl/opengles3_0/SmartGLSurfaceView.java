package com.smartgang.opengl.opengles3_0;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.smartgang.opengl.opengles3_0.render.AirHockeyRender;
import com.smartgang.opengl.opengles3_0.render.AirHockeyRenderTouched;
import com.smartgang.opengl.opengles3_0.render.SmartGLSurfaceViewRender;
import com.smartgang.opengl.opengles3_0.render.SmartGLSurfaceViewRenderAirHockey;
import com.smartgang.opengl.opengles3_0.render.SmartGLSurfaceViewRenderScreen;

/**
 * Created by zhangxingang on 2017/9/26.
 */

public class SmartGLSurfaceView extends GLSurfaceView{
    private SmartGLSurfaceViewRender mSmartGLSurfaceViewRender;
    private SmartGLSurfaceViewRenderScreen mSmartGLSurfaceViewRenderScreen;
    private SmartGLSurfaceViewRenderAirHockey mSmartGLSurfaceViewRenderAirHockey;
    private AirHockeyRender mAirHockeyRender;
    private AirHockeyRenderTouched mAirHockeyRenderTouched;
    private Context mContext;
    public SmartGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        try{
            setEGLContextClientVersion(2);
            mSmartGLSurfaceViewRender = new SmartGLSurfaceViewRender(mContext);
            mSmartGLSurfaceViewRenderScreen = new SmartGLSurfaceViewRenderScreen(mContext);
            mSmartGLSurfaceViewRenderAirHockey = new SmartGLSurfaceViewRenderAirHockey(mContext);
            mAirHockeyRender = new AirHockeyRender(mContext);
            setRenderer(mAirHockeyRender);
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }catch (Exception e){
            Log.e("MojingGLSurfaceView", "Unable to create GLSurfaceView context!", e);
            e.printStackTrace();
        }
    }
    public void handleTouchDrag(float deltaX, float deltaY) {
        mSmartGLSurfaceViewRenderScreen.handleTouchDrag(deltaX, deltaY);
    }
}
