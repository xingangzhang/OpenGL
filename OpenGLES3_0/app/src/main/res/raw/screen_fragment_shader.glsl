#version 300 es
precision mediump float;
layout(location = 0) out vec4 outColor;
uniform samplerCube u_TextureUnit;
in vec3 a_Position;

void main()
{
	outColor = texture(u_TextureUnit, a_Position);
}
