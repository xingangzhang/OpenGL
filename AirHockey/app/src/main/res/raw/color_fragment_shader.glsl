#version 300 es
precision mediump float;
layout(location = 0) out vec4 outColor;
in vec4 v_Color;
void main() {
    outColor = v_Color;
}

