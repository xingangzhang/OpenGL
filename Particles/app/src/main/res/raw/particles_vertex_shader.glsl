#version 300 es
uniform mat4 u_Matrix;
uniform float u_Time;

layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec3 a_Color;
layout(location = 2) in vec3 a_DirectionVector;
layout(location = 3) in float a_ParticleStartTime;

out vec3 v_Color;
out float v_ElapsedTime;

void main()
{
    v_Color = a_Color;
    v_ElapsedTime = u_Time - a_ParticleStartTime;
    float gravityFactor = v_ElapsedTime * v_ElapsedTime;
    vec3 currentPosition = a_Position + (a_DirectionVector * v_ElapsedTime);
    currentPosition.y -= gravityFactor;
    gl_Position = u_Matrix * vec4(currentPosition, 1.0);

    gl_PointSize = 10.0;

    //gl_PointSize = 25.0;
}
