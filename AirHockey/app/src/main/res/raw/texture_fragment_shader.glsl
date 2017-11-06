#version 300 es
precision mediump float;
uniform sampler2D s_texture;
in vec2 v_TextureCoordinates;
layout(location = 0) out vec4 outColor;
void main() {
    outColor = texture(s_texture, v_TextureCoordinates);
}
