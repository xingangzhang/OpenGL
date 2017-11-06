package com.smartgang.opengl.heightmap.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import static android.opengl.GLES30.GL_ARRAY_BUFFER;
import static android.opengl.GLES30.GL_STATIC_DRAW;
import static android.opengl.GLES30.glBindBuffer;
import static android.opengl.GLES30.glBufferData;
import static android.opengl.GLES30.glGenBuffers;
import static com.smartgang.opengl.heightmap.Constant.BYTES_PER_SHORT;

/**
 * Created by zhangxingang on 2017/10/23.
 * Email:zhangxg92@qq.com
 */

public class IndexBuffer {
    private final int mElementArrayBufferId;

    public IndexBuffer(short[] indexData) {
        // Allocate a buffer.
        final int buffers[] = new int[1];
        glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0) {
            throw new RuntimeException("Could not create a new vertex buffer object.");
        }
        mElementArrayBufferId = buffers[0];

        // Bind to the buffer.
        glBindBuffer(GL_ARRAY_BUFFER, mElementArrayBufferId);
        // Transfer data to native memory.
        ShortBuffer indexArray = ByteBuffer
                .allocateDirect(indexData.length * BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(indexData);
        indexArray.position(0);
        // Transfer data from native memory to the GPU buffer.
        glBufferData(GL_ARRAY_BUFFER, indexArray.capacity() * BYTES_PER_SHORT,
                indexArray, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getmElementArrayBufferId() {
        return mElementArrayBufferId;
    }
}
