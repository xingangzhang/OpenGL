#version 300 es
layout(location = 0) in vec3 v_Position;
out vec3 a_Position;
uniform mat4 u_Matrix;

void main() {
    a_Position = v_Position;
    a_Position.z = -a_Position.z;
    gl_Position = u_Matrix * vec4(a_Position, 1);
    gl_Position = gl_Position.xyww;
}
