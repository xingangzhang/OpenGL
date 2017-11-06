package com.smartgang.opengl.heightmap.program;

import android.content.Context;
import android.opengl.GLES30;

import com.smartgang.opengl.heightmap.util.ShaderHelper;
import com.smartgang.opengl.heightmap.util.TextResourceReader;

import static android.opengl.GLES30.glUseProgram;

/**
 * Created by zhangxingang on 2017/9/26.
 */

public class ShaderProgram {
    // Shader program
    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId,
                            int fragmentShaderResourceId) {
        // Compile the shaders and link the program.
        program = ShaderHelper.buildProgram(
                TextResourceReader
                        .readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader
                        .readTextFileFromResource(context, fragmentShaderResourceId));
    }

    public void useProgram() {
        // Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }

}
