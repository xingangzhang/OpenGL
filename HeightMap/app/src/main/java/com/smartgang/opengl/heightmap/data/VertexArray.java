package com.smartgang.opengl.heightmap.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glVertexAttribPointer;
import static com.smartgang.opengl.heightmap.Constant.BYTES_PER_FLOAT;

/**
 * Created by zhangxingang on 2017/9/30.
 * Email:zhangxg92@qq.com
 */

public class VertexArray {
    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }
    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
                                       int componentCount, int stride){
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }

    /**
     * Updates the float buffer with the specified vertex data, assuming that
     * the vertex data and the float buffer are the same size.
     */
    public void updateBuffer(float[] vertexData, int start, int count) {
        floatBuffer.position(start);
        floatBuffer.put(vertexData, start, count);
        floatBuffer.position(0);
    }
}
