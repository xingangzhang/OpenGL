package com.smartgang.opengl.heightmap.object;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES30;

import com.smartgang.opengl.heightmap.data.IndexBuffer;
import com.smartgang.opengl.heightmap.data.VertexBuffer;
import com.smartgang.opengl.heightmap.program.HeigntmapShaderProgram;

import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glDrawElements;

/**
 * Created by zhangxingang on 2017/10/23.
 * Email:zhangxg92@qq.com
 */

public class HeightMap {
    private static final int POSITION_COMPONENT_COUNT = 3;

    private final int width;
    private final int height;
    private final int numElements;
    private final VertexBuffer vertexBuffer;
    private final IndexBuffer indexBuffer;

    public HeightMap(Bitmap bitmap) {
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        if (width * height > 65536) {
            throw new RuntimeException("Heightmap is too large for the index buffer.");
        }
        numElements = calculateNumElement();
        vertexBuffer = new VertexBuffer(loadBitmap(bitmap));
        indexBuffer = new IndexBuffer(createIndexData());
    }

    private short[] createIndexData() {
        final short[] indexData = new short[numElements];
        int offset = 0;
        for (int row = 0; row < height - 1; row++){
            for (int col = 0; col < width - 1; col++) {
                short topLeftIndexNum = (short) (row * height + col);
                short topRightIndexNum = (short) (row * height + col + 1);
                short bottomLeftIndexNum = (short) ((row + 1) * height + col);
                short bottomRightIndexNum = (short) ((row + 1) * height + col + 1);

                indexData[offset++] = topLeftIndexNum;
                indexData[offset++] = bottomLeftIndexNum;
                indexData[offset++] = bottomRightIndexNum;

                indexData[offset++] = topRightIndexNum;
                indexData[offset++] = bottomLeftIndexNum;
                indexData[offset++] = bottomRightIndexNum;
            }
        }
        return indexData;
    }

    private float[] loadBitmap(Bitmap bitmap) {
        final int[] pixels = new int[width * height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
        bitmap.recycle();
        final float[] heightmapVertex = new float[width * height * POSITION_COMPONENT_COUNT];
        int offset = 0;
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++) {
                final float xPosition = ((float) col / (float) (width - 1)) - 0.5f;
                final float zPosition = ((float) row / (float) (height - 1)) - 0.5f;
                final float yPosition = (float) Color.red(pixels[row * height + col]) / (float) 255;

                heightmapVertex[offset++] = xPosition;
                heightmapVertex[offset++] = yPosition;
                heightmapVertex[offset++] = zPosition;
            }
        }
        return heightmapVertex;
    }

    private int calculateNumElement() {
        return (width - 1) * (height - 1) * 2 * 3;
    }

    public void bindData(HeigntmapShaderProgram heightmapProgram) {
        vertexBuffer.setVertexAttribPointer(0,
                heightmapProgram.getaPositionLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getmElementArrayBufferId());
        glDrawElements(GL_TRIANGLES, numElements, GL_UNSIGNED_SHORT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
