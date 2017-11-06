package com.smartgang.opengl.particles.render;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.provider.Settings;

import com.smartgang.opengl.particles.R;
import com.smartgang.opengl.particles.load.ParticlesShaderProgram;
import com.smartgang.opengl.particles.object.ParticleFireworkExplosion;
import com.smartgang.opengl.particles.object.ParticleShooter;
import com.smartgang.opengl.particles.object.ParticleSystem;
import com.smartgang.opengl.particles.util.Geometry;
import com.smartgang.opengl.particles.util.MatrixHelper;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.glBlendFunc;
import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glEnable;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static com.smartgang.opengl.particles.util.TextureHelper.loadTexture;

/**
 * Created by zhangxingang on 2017/10/16.
 * Email:zhangxg92@qq.com
 */

public class ParticlesRender implements GLSurfaceView.Renderer{
    private Context mContext;
    private ParticlesShaderProgram mParticlesShaderProgram;
    private ParticleSystem mParticleSystem;
    private long globalStartTime;
    private ParticleShooter redParticleShooter;
    private ParticleShooter blueParticleShooter;
    private ParticleShooter greenParticleShooter;
    private float[] projectionMatrix;
    private float[] viewMatrix;
    private float[] viewProjectionMatrix;

    // Maximum saturation and value.
    private final float[] hsv = {0f, 1f, 1f};
    private final float angleVarianceInDegree = 10f;
    private final float speedVariance = 1f;
    private int mTextureObjectId;
    private ParticleFireworkExplosion mParticleFireworkExplosion;
    private Random mRandom;
    public ParticlesRender(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        projectionMatrix = new float[16];
        viewMatrix = new float[16];
        viewProjectionMatrix = new float[16];
        mParticlesShaderProgram = new ParticlesShaderProgram(mContext, R.raw.particles_vertex_shader, R.raw.particles_fragment_shader);
        mParticleSystem = new ParticleSystem(1000);
        mParticleFireworkExplosion = new ParticleFireworkExplosion();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        globalStartTime = System.nanoTime();

        mTextureObjectId = loadTexture(mContext, R.drawable.particle_texture);

        final Geometry.Vector particleDiretion = new Geometry.Vector(0f, 3f, 0f);
        redParticleShooter = new ParticleShooter(
                new Geometry.Point(-1f,0f,0f),
                particleDiretion,
                Color.rgb(255,50,5),
                angleVarianceInDegree,
                speedVariance
        );
        greenParticleShooter = new ParticleShooter(
                new Geometry.Point(0f,0f,0f),
                particleDiretion,
                Color.rgb(25,20,25),
                angleVarianceInDegree,
                speedVariance
        );
        blueParticleShooter = new ParticleShooter(
                new Geometry.Point(1f,0f,0f),
                particleDiretion,
                Color.rgb(5,20,255),
                angleVarianceInDegree,
                speedVariance
        );

        mParticleFireworkExplosion = new ParticleFireworkExplosion();

        mRandom = new Random();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 90, (float) width
                / (float) height, 1f, 10f);
        setIdentityM(viewMatrix, 0);
        translateM(viewMatrix, 0, 0, -1.5f, -5f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix,
                0, viewMatrix, 0);
    }
    private int count = 0;
    @Override
    public void onDrawFrame(GL10 gl) {
        if (count % 1 == 0) {
            glClear(GL_COLOR_BUFFER_BIT);
            float current = (System.nanoTime() - globalStartTime) / 1000000000f;
            redParticleShooter.addParticles(mParticleSystem, current, 5);
            greenParticleShooter.addParticles(mParticleSystem, current, 5);
            blueParticleShooter.addParticles(mParticleSystem, current, 5);

        if (mRandom.nextFloat() < 0.02f) {
            hsv[0] = mRandom.nextInt(360);

            mParticleFireworkExplosion.addExplosion(
                mParticleSystem,
                new Geometry.Point(
                    -1f + mRandom.nextFloat() * 2f,
                     3f + mRandom.nextFloat() / 2f,
                    -1f + mRandom.nextFloat() * 2f),
                Color.HSVToColor(hsv),
                globalStartTime);
        }
            mParticlesShaderProgram.useProgram();
            mParticlesShaderProgram.setUniforms(viewProjectionMatrix, current, mTextureObjectId);
            mParticleSystem.bindData(mParticlesShaderProgram);
            mParticleSystem.draw();
        }
        count++;
    }
}
