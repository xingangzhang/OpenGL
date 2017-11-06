package com.smartgang.opengl.heightmap.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_ARRAY_BUFFER;
import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_STATIC_DRAW;
import static android.opengl.GLES30.glBindBuffer;
import static android.opengl.GLES30.glBufferData;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glGenBuffers;
import static android.opengl.GLES30.glVertexAttribPointer;
import static com.smartgang.opengl.heightmap.Constant.BYTES_PER_FLOAT;

/**
 * Created by zhangxingang on 2017/10/23.
 * Email:zhangxg92@qq.com
 */

public class VertexBuffer {
    private final int mArrayBufferId;

    public VertexBuffer(float[] vertexData) {
        // Allocate a buffer.
        final int buffers[] = new int[1];
        glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0) {
            throw new RuntimeException("Could not create a new vertex buffer object.");
        }
        mArrayBufferId = buffers[0];

        // Bind to the buffer.
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBufferId);
        // Transfer data to native memory.
        FloatBuffer vertexArray = ByteBuffer
                .allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexArray.position(0);
        // Transfer data from native memory to the GPU buffer.
        glBufferData(GL_ARRAY_BUFFER, vertexArray.capacity() * BYTES_PER_FLOAT,
                vertexArray, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
                                       int componentCount, int stride) {
        glBindBuffer(GL_ARRAY_BUFFER, mArrayBufferId);
        // This call is slightly different than the glVertexAttribPointer we've
        // used in the past: the last parameter is set to dataOffset, to tell OpenGL
        // to begin reading data at this position of the currently bound buffer.
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT,
                false, stride, dataOffset);
        glEnableVertexAttribArray(attributeLocation);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
