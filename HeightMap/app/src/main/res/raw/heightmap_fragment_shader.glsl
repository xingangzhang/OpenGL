#version 300 es
precision mediump float;
layout(location = 0)out vec4 outColor;

in vec3 v_Color;

void main() {
    outColor = vec4(v_Color, 1.0);
}