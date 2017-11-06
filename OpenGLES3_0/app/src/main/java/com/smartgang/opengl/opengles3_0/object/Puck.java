package com.smartgang.opengl.opengles3_0.object;

import com.smartgang.opengl.opengles3_0.data.VertexArray;
import com.smartgang.opengl.opengles3_0.load.ColorShaderProgram;
import com.smartgang.opengl.opengles3_0.load.ColorShaderProgram1;
import com.smartgang.opengl.opengles3_0.object.ObjectBuilder.GenerateData;
import com.smartgang.opengl.opengles3_0.object.ObjectBuilder.DrawCommand;
import com.smartgang.opengl.opengles3_0.util.Geometry.Cylinder;
import com.smartgang.opengl.opengles3_0.util.Geometry.Point;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxingang on 2017/10/10.
 * Email:zhangxg92@qq.com
 */

public class Puck {
    private final static int POSITION_COMPONENT_COUNT = 3;
    public final float mRadius, mHeight;
    private final VertexArray mVertexData;
    private final List<DrawCommand> mDrawList;

    public Puck(float radius, float height, int numPoints) {
        GenerateData generateData = ObjectBuilder.createPuck(
                new Cylinder(new Point(0, 0, 0), radius, height), numPoints);
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
        for (DrawCommand command : mDrawList) {
            command.draw();
        }
    }
}
