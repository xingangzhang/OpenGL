package com.smartgang.opengl.airhockey.render;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.smartgang.opengl.airhockey.R;
import com.smartgang.opengl.airhockey.load.ColorShaderProgram;
import com.smartgang.opengl.airhockey.load.TextureShaderProgram;
import com.smartgang.opengl.airhockey.object.MalletObject;
import com.smartgang.opengl.airhockey.object.Puck;
import com.smartgang.opengl.airhockey.object.Table;
import com.smartgang.opengl.airhockey.util.Geometry;
import com.smartgang.opengl.airhockey.util.Geometry.Sphere;
import com.smartgang.opengl.airhockey.util.Geometry.Point;
import com.smartgang.opengl.airhockey.util.Geometry.Ray;
import com.smartgang.opengl.airhockey.util.Geometry.Vector;
import com.smartgang.opengl.airhockey.util.Geometry.Plane;

import com.smartgang.opengl.airhockey.util.MatrixHelper;
import com.smartgang.opengl.airhockey.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES30.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES30.glClear;
import static android.opengl.GLES30.glClearColor;
import static android.opengl.GLES30.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;
import static com.smartgang.opengl.airhockey.util.CoordsHelper.getWorldCoords;
import static com.smartgang.opengl.airhockey.util.Geometry.vectorBetween;

/**
 * Created by zhangxingang on 2017/10/11.
 * Email:zhangxg92@qq.com
 */

public class AirHockeyRender implements GLSurfaceView.Renderer {
    private Context mContext;
    private ColorShaderProgram mColorShaderProgram;
    private TextureShaderProgram mTextureShaderProgram;
    private Table mTable;
    private MalletObject mMallet;
    private Puck mPuck;
    private int mTextureObjectId;
    private float[] viewMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private float[] modelViewProjectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Point mBlueMalletPosition;
    private Point mPreviousBlueMalletPosition;
    private Point mRedMalletPosition;
    private Point mPuckPosition;
    private Vector mPuckVector;
    private boolean mMalletPressed = false;
    private final float leftBound = -0.5f;
    private final float rightBound = 0.5f;
    private final float nearBound = -0.8f;
    private final float farBound = 0.8f;


    public AirHockeyRender(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mColorShaderProgram = new ColorShaderProgram(mContext,
                R.raw.color_vertex_shader, R.raw.color1_fragment_shader);
        mMallet = new MalletObject(0.08f, 0.15f, 32);
        mTextureShaderProgram = new TextureShaderProgram(mContext, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        mTextureObjectId = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);
        mTable = new Table();
        mPuck = new Puck(0.06f, 0.02f, 32);
        mBlueMalletPosition = new Point(0f, mMallet.mHeight / 2f, 0.4f);
        mRedMalletPosition = new Point(0f, mMallet.mHeight / 2f, -0.4f);
        mPuckPosition = new Point(0f, mPuck.mHeight / 2f, 0f);
        mPuckVector = new Vector(0, 0, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 50, (float) width
                / (float) height, 1f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix,
                0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        mPuckPosition = mPuckPosition.translate(mPuckVector);
        if (mPuckPosition.x < leftBound + mPuck.mRadius || mPuckPosition.x > rightBound - mPuck.mRadius) {
            mPuckVector = new Vector(-mPuckVector.x, mPuckVector.y, mPuckVector.z);
//            mPuckVector = mPuckVector.scale(0.9f);
        }

        if (mPuckPosition.z < nearBound + mPuck.mRadius
                || mPuckPosition.z > farBound - mPuck.mRadius) {
            mPuckVector = new Vector(mPuckVector.x, mPuckVector.y, -mPuckVector.z);
//            mPuckVector = mPuckVector.scale(0.9f);
        }
        mPuckPosition = new Point(
                clamp(mPuckPosition.x, leftBound + mPuck.mRadius, rightBound - mPuck.mRadius),
                mPuckPosition.y,
                clamp(mPuckPosition.z, nearBound + mPuck.mRadius, farBound - mPuck.mRadius)
        );

        mPuckVector = mPuckVector.scale(0.9f);

        positionTableInScene();
        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(modelViewProjectionMatrix, mTextureObjectId);
        mTable.bindData(mTextureShaderProgram);
        mTable.draw();
        positionObjectInScene(mPuckPosition);
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        mPuck.bindData(mColorShaderProgram);
        mPuck.draw();
        positionObjectInScene(mRedMalletPosition);
        mColorShaderProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();
        positionObjectInScene(mBlueMalletPosition);
        mColorShaderProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();
    }

    private void positionTableInScene() {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to lie flat on the XZ plane.
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    private void positionObjectInScene(Point point) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, point.x, point.y, point.z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    private Ray convertNormalized2DPointToRay(float normalizadX, float normalizadY) {
        Point nearNormalized2DPoint = new Point(normalizadX, normalizadY, -1);
        Point farNormalized2DPoint = new Point(normalizadX, normalizadY, 1);
        Point near3DPoint = getWorldCoords(nearNormalized2DPoint, viewProjectionMatrix);
        Point far3DPoint = getWorldCoords(farNormalized2DPoint, viewProjectionMatrix);
        return new Ray(near3DPoint, vectorBetween(near3DPoint, far3DPoint));
    }

    public void handleTouchDrag(float normalizadX, float normalizadY) {
        if (mMalletPressed) {
            Ray ray = convertNormalized2DPointToRay(normalizadX, normalizadY);
            Plane plane = new Plane(new Point(0, 0, 0), new Vector(0, 1, 0));
            Point touchedPoint = Geometry.intersectionPoint(ray, plane);

            mPreviousBlueMalletPosition = mBlueMalletPosition;
//            mBlueMalletPosition =
//                    new Point(touchedPoint.x, mMallet.mHeight / 2f, touchedPoint.z);
            mBlueMalletPosition = new Point(
                    clamp(touchedPoint.x, leftBound + mMallet.mRadius, rightBound - mMallet.mRadius),
                    mMallet.mHeight / 2f,
                    clamp(touchedPoint.z, 0 + mMallet.mRadius, farBound - mMallet.mRadius)
            );
            float distance = vectorBetween(mBlueMalletPosition, mPuckPosition).length();
            if (distance < (mMallet.mRadius + mPuck.mRadius)) {
                mPuckVector = vectorBetween(mPreviousBlueMalletPosition, mBlueMalletPosition);
            }
        }
    }

    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }

    public void handleTouchPress(float normalizadX, float normalizadY) {
        Ray ray = convertNormalized2DPointToRay(normalizadX, normalizadY);
        Sphere malletBoundingSphere = new Sphere(new Point(mBlueMalletPosition.x, mBlueMalletPosition.y, mBlueMalletPosition.z), mMallet.mHeight / 2f);
        mMalletPressed = Geometry.intersects(malletBoundingSphere, ray);
    }
}
