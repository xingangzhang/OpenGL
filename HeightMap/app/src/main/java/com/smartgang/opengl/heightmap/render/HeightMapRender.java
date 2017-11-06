package com.smartgang.opengl.heightmap.render;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLSurfaceView;

import com.smartgang.opengl.heightmap.R;
import com.smartgang.opengl.heightmap.object.HeightMap;
import com.smartgang.opengl.heightmap.program.HeigntmapShaderProgram;
import com.smartgang.opengl.heightmap.util.MatrixHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.scaleM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static com.smartgang.opengl.heightmap.R.drawable.heightmap;

/**
 * Created by zhangxingang on 2017/10/23.
 * Email:zhangxg92@qq.com
 */

public class HeightMapRender implements GLSurfaceView.Renderer {
    private final Context mContext;
    private HeightMap heightMap;
    private HeigntmapShaderProgram heightmapProgram;

    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewMatrixForSkybox = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] tempMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private float xRotation;
    private float yRotation;

    public HeightMapRender(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        heightmapProgram = new HeigntmapShaderProgram(mContext, R.raw.heightmap_vertex_shader, R.raw.heightmap_fragment_shader);
        heightMap = new HeightMap(((BitmapDrawable)mContext.getResources().getDrawable(heightmap, null)).getBitmap());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 100f);
        updateViewMatrices();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        drawHeightmap();
    }
    private void drawHeightmap() {
        setIdentityM(modelMatrix, 0);
        // Expand the heightmap's dimensions, but don't expand the height as
        // much so that we don't get insanely tall mountains.
        scaleM(modelMatrix, 0, 100f, 10f, 100f);
        updateMvpMatrix();

        heightmapProgram.useProgram();
        heightmapProgram.setUniforms(modelViewProjectionMatrix);
        heightMap.bindData(heightmapProgram);
        heightMap.draw();
    }

    private void updateViewMatrices() {
        setIdentityM(viewMatrix, 0);
        rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
        rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
        System.arraycopy(viewMatrix, 0, viewMatrixForSkybox, 0, viewMatrix.length);

        // We want the translation to apply to the regular view matrix, and not
        // the skybox.
        translateM(viewMatrix, 0, 0, -1.5f, -5f);
    }
    private void updateMvpMatrix() {
        multiplyMM(tempMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, tempMatrix, 0);
    }

    public void handleTouchDrag(float deltaX, float deltaY) {
        xRotation += deltaX / 16f;
        yRotation += deltaY / 16f;

        if (yRotation < -90) {
            yRotation = -90;
        } else if (yRotation > 90) {
            yRotation = 90;
        }

        // Setup view matrix
        updateViewMatrices();
    }
}
