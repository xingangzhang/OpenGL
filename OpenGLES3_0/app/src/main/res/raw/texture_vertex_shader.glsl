#version 300 es
layout(location = 0) in vec4 a_Position;
layout(location = 1) in vec2 a_TextureCoordinates;
uniform mat4 a_Matrix;

out vec2 v_TextureCoordinates;
void main() {
    v_TextureCoordinates = a_TextureCoordinates;
    gl_Position = a_Matrix * a_Position;
}