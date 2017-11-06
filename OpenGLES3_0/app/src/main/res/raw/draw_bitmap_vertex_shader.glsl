#version 300 es
layout(location = 0) in vec4 v_Position;
layout(location = 1) in vec2 a_texCoord;
out vec2 v_texCoord;
void main() {
    gl_Position = v_Position;
    v_texCoord = a_texCoord;
}