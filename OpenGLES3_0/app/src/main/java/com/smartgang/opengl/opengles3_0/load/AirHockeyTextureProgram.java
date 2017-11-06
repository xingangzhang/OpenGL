package com.smartgang.opengl.opengles3_0.load;

import android.content.Context;

import com.smartgang.opengl.opengles3_0.program.ShaderProgram;

/**
 * Created by zhangxingang on 2017/9/30.
 * Email:zhangxg92@qq.com
 */

public class AirHockeyTextureProgram extends ShaderProgram{
    protected AirHockeyTextureProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
    }

    public int getPositionAttributeLocation() {
        return 0;
    }
}
