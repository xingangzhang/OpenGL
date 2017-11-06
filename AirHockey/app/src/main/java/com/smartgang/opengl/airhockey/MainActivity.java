package com.smartgang.opengl.airhockey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private AirHockeyGLSurfaceView mAirHockeyGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAirHockeyGLSurfaceView = (AirHockeyGLSurfaceView) findViewById(R.id.gl_surfaceview);
        mAirHockeyGLSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event != null) {
                    // Convert touch coordinates into normalized device
                    // coordinates, keeping in mind that Android's Y
                    // coordinates are inverted.
                    final float normalizadX = (event.getX() / (float) v.getWidth()) * 2 - 1;
                    final float normalizadY = -((event.getY() / (float) v.getHeight()) * 2 - 1);
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mAirHockeyGLSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mAirHockeyGLSurfaceView.handleTouchPress(normalizadX, normalizadY);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        mAirHockeyGLSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mAirHockeyGLSurfaceView.handleTouchDrag(normalizadX, normalizadY);
                            }
                        });
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
