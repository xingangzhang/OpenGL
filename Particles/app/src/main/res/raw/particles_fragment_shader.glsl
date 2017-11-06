#version 300 es
precision mediump float;
layout(location = 0)out vec4 outColor;
uniform sampler2D u_TextureUnit;
in vec3 v_Color;
in float v_ElapsedTime;

void main() {

//        float xDistance = 0.5 - gl_PointCoord.x;
//        float yDistance = 0.5 - gl_PointCoord.y;
//        float distanceFromCenter =
//            sqrt(xDistance * xDistance + yDistance * yDistance);
//        outColor = vec4(v_Color / v_ElapsedTime, 1.0);
//
//        if (distanceFromCenter > 0.5) {
//            discard;
//        } else {
//            outColor = vec4(v_Color / v_ElapsedTime, 1.0);
//        }

        outColor = vec4(v_Color / v_ElapsedTime, 1.0) * texture(u_TextureUnit, gl_PointCoord);
}

