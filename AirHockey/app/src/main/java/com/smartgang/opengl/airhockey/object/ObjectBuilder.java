package com.smartgang.opengl.airhockey.object;


import com.smartgang.opengl.airhockey.util.Geometry.Cylinder;
import com.smartgang.opengl.airhockey.util.Geometry.Circle;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES30.GL_TRIANGLE_FAN;
import static android.opengl.GLES30.GL_TRIANGLE_STRIP;
import static android.opengl.GLES30.glDrawArrays;
import static com.smartgang.opengl.airhockey.Constant.BYTES_PER_FLOAT;
import static com.smartgang.opengl.airhockey.Constant.FLOATS_PER_VERTEX;

/**
 * Created by zhangxingang on 2017/10/10.
 * Email:zhangxg92@qq.com
 */

public class ObjectBuilder {
    private final float[] mVertexData;
    private final List<DrawCommand> mDrawList = new ArrayList<>();

    public ObjectBuilder(int sizeInVertices) {
        mVertexData = new float[BYTES_PER_FLOAT * sizeInVertices];
    }


    static interface DrawCommand {
        void draw();
    }

    static class GenerateData {
        private final float[] mVertexData;
        private final List<DrawCommand> mDrawList;

        public GenerateData(float[] vertexData, List<DrawCommand> drawList) {
            this.mVertexData = vertexData;
            this.mDrawList = drawList;
        }

        public float[] getVertexData() {
            return mVertexData;
        }

        public List<DrawCommand> getDrawList() {
            return mDrawList;
        }
    }

    static GenerateData createMallet(Cylinder mallet, int numPoints) {
        //total points of the geometry
        int size = 2 * (sizeOfCircleInVertices(numPoints) + sizeOfCylinderInVertices(numPoints));
        ObjectBuilder builder = new ObjectBuilder(size);
        float height = mallet.height;

        Circle baseCircle = new Circle(mallet.center.translateY(-0.25f * height), mallet.radius);
        Cylinder baseCylinder = new Cylinder(mallet.center.translateY(-0.125f * height), mallet.radius, 0.25f * height);
        builder.appendCircle(baseCircle, numPoints);
        builder.appendOpenCylinder(baseCylinder, numPoints);
        Circle handleCircle = new Circle(mallet.center.translateY(0.5f * height), mallet.radius / 3);
        Cylinder handleCylinder = new Cylinder(mallet.center.translateY(0.125f * height), mallet.radius / 3, 0.75f * height);
        builder.appendCircle(handleCircle, numPoints);
        builder.appendOpenCylinder(handleCylinder, numPoints);
        return builder.build();
    }

    static GenerateData createPuck(Cylinder puck, int numPoints) {
        //total points of the geometry
        int size = sizeOfCircleInVertices(numPoints) + sizeOfCylinderInVertices(numPoints);

        ObjectBuilder builder = new ObjectBuilder(size);

        Circle puckTop = new Circle(puck.center.translateY(puck.height / 2), puck.radius);

        builder.appendCircle(puckTop, numPoints);
        builder.appendOpenCylinder(puck, numPoints);

        return builder.build();
    }

    private int offset = 0;

    private void appendOpenCylinder(Cylinder cylinder, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCylinderInVertices(numPoints);
        final float yStart = cylinder.center.y - (cylinder.height / 2f);
        final float yEnd = cylinder.center.y + (cylinder.height / 2f);
        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = ((float)i / (float)numPoints) * (float)Math.PI * 2f;
            float xPosition = cylinder.center.x + cylinder.radius * (float)Math.cos(angleInRadians);
            float zPosition = cylinder.center.z + cylinder.radius * (float)Math.sin(angleInRadians);
            mVertexData[offset++] = xPosition;
            mVertexData[offset++] = yStart;
            mVertexData[offset++] = zPosition;
            mVertexData[offset++] = xPosition;
            mVertexData[offset++] = yEnd;
            mVertexData[offset++] = zPosition;
        }
        mDrawList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    private void appendCircle(Circle puckTop, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCircleInVertices(numPoints);

        //Center point of fan
        mVertexData[offset++] = puckTop.center.x;
        mVertexData[offset++] = puckTop.center.y;
        mVertexData[offset++] = puckTop.center.z;

        // Fan around center point. <= is used because we want to generate
        // the point at the starting angle twice to complete the fan.
        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = ((float)i / (float)numPoints) * (float)Math.PI * 2f;
            mVertexData[offset++] = puckTop.center.x + puckTop.radius * (float)Math.cos(angleInRadians);
            mVertexData[offset++] = puckTop.center.y;
            mVertexData[offset++] = puckTop.center.z + puckTop.radius * (float)Math.sin(angleInRadians);
        }
        mDrawList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices);
            }
        });
    }

    private static int sizeOfCircleInVertices(int numPoints) {
        return 1 + (numPoints + 1);
    }

    private static int sizeOfCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }

    private GenerateData build() {
        return new GenerateData(mVertexData, mDrawList);
    }

}
