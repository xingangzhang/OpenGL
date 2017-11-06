package com.smartgang.opengl.opengles3_0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SmartGLSurfaceView mSmartGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSmartGLSurfaceView = (SmartGLSurfaceView) findViewById(R.id.gl_surfaceview);
        mSmartGLSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            float previousX, previousY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event != null) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        previousX = event.getX();
                        previousY = event.getY();
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        final float deltaX = event.getX() - previousX;
                        final float deltaY = event.getY() - previousY;

                        previousX = event.getX();
                        previousY = event.getY();

                        mSmartGLSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mSmartGLSurfaceView.handleTouchDrag(
                                        deltaX, deltaY);
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
