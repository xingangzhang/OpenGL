package com.smartgang.opengl.heightmap;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.smartgang.opengl.heightmap.render.HeightMapRender;

/**
 * Created by zhangxingang on 2017/10/23.
 * Email:zhangxg92@qq.com
 */

public class HeightMapGLSurfaceView extends GLSurfaceView{
    private HeightMapRender mHeightMapRender;
    private Context mContext;
    public HeightMapGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        try{
            setEGLContextClientVersion(2);
            mHeightMapRender = new HeightMapRender(mContext);
            setRenderer(mHeightMapRender);
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }catch (Exception e){
            Log.e("MojingGLSurfaceView", "Unable to create GLSurfaceView context!", e);
            e.printStackTrace();
        }
    }

    public void handleTouchDrag(float deltaX, float deltaY) {
        mHeightMapRender.handleTouchDrag(deltaX, deltaY);
    }
}
