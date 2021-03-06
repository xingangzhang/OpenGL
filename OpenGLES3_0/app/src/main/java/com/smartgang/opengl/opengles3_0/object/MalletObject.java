package com.smartgang.opengl.opengles3_0.object;

import com.smartgang.opengl.opengles3_0.data.VertexArray;
import com.smartgang.opengl.opengles3_0.load.ColorShaderProgram1;
import com.smartgang.opengl.opengles3_0.util.Geometry;

import java.util.List;

/**
 * Created by zhangxingang on 2017/10/11.
 * Email:zhangxg92@qq.com
 */

public class MalletObject {
    private final static int POSITION_COMPONENT_COUNT = 3;
    public final float mRadius, mHeight;
    private final VertexArray mVertexData;
    private final List<ObjectBuilder.DrawCommand> mDrawList;

    public MalletObject(float radius, float height, int numPoints) {
        ObjectBuilder.GenerateData generateData = ObjectBuilder.createMallet(
                new Geometry.Cylinder(new Geometry.Point(0, 0, 0), radius, height), numPoints);
        this.mRadius = radius;
        this.mHeight = height;
        mVertexData = new VertexArray(generateData.getVertexData());
        this.mDrawList = generateData.getDrawList();
    }

    public void bindData(ColorShaderProgram1 colorShaderProgram) {
        mVertexData.setVertexAttribPointer(
                0,
                colorShaderProgram.getPositionAttribLocation(),
                POSITION_COMPONENT_COUNT,
                0);
    }
    public void draw() {
        for (ObjectBuilder.DrawCommand command : mDrawList) {
            command.draw();
        }
    }
}
