package com.smartgang.opengl.airhockey.util;
import com.smartgang.opengl.airhockey.util.Geometry.Point;

import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMV;

/**
 * Created by zhangxingang on 2017/10/11.
 * Email:zhangxg92@qq.com
 */

public class CoordsHelper {
    public static Point getWorldCoords(Point vec, float[] projectionMatrix){
        final float[] invertedViewProjectionMatrix = new float[16];
        invertM(invertedViewProjectionMatrix, 0, projectionMatrix, 0);
        final float[] worldCoords = {vec.x, vec.y, vec.z, 1};
        final float[] outCoords = new float[4];
        multiplyMV(
                outCoords, 0, invertedViewProjectionMatrix, 0, worldCoords, 0);
        if (outCoords[3] != 0) {
            divideByW(outCoords);
            return new Point(outCoords[0],outCoords[1],outCoords[2]);
        }
        return null;
    }
    private static void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }
}
