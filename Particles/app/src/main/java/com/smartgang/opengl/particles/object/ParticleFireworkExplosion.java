package com.smartgang.opengl.particles.object;

import android.graphics.Color;

import com.smartgang.opengl.particles.util.Geometry;

import java.util.Random;

import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setRotateEulerM;

/**
 * Created by zhangxingang on 2017/10/17.
 * Email:zhangxg92@qq.com
 */

public class ParticleFireworkExplosion {
    private final Random random = new Random();

    private float[] rotationMatrix = new float[16];
    private float[] directionVector = { 0f, 0f, 1f, 1f };
    private float[] resultVector = new float[4];
    private float[] hsv = new float[3];

    public void addExplosion(ParticleSystem particleSystem, Geometry.Point position,
                             int color, long startTime) {
        final float currentTime = (System.nanoTime() - startTime) / 1000000000f;

        for (int trail = 0; trail < 50; trail++) {
            setRotateEulerM(rotationMatrix, 0,
                    random.nextFloat() * 360f,
                    random.nextFloat() * 360f,
                    random.nextFloat() * 360f);

            multiplyMV(resultVector, 0, rotationMatrix, 0,
                    directionVector, 0);

            float magnitude = 0.5f + (random.nextFloat() / 2f);
            float timeForThisStream = currentTime;
            Color.colorToHSV(color, hsv);

            for (int particle = 0; particle < 10; particle++) {
                particleSystem.addParticle(
                        position,
                        Color.HSVToColor(hsv),
                        new Geometry.Vector(resultVector[0] * magnitude,
                                resultVector[1] * magnitude + 0.5f,
                                resultVector[2] * magnitude),
                        timeForThisStream);
                timeForThisStream += 0.025f;
                hsv[2] *= 0.9f;
            }
        }
    }
}
