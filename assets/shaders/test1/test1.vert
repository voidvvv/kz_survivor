#version 330 core
attribute vec4 a_position;
attribute vec3 a_instance_offset;

uniform mat4 u_projTrans;

varying vec4 v_position;
void main() {
    vec2 offset = vec2((a_instance_offset.xy * 35));
    vec4 tmp = vec4(a_position.xy + offset, a_position.zw);
    gl_Position = u_projTrans * tmp ;
//    gl_Position.xy += ;
    v_position = a_position;
}
