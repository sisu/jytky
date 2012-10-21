#include "projection.hpp"
#include <cmath>
#if 0
Matrix4 perspectiveM(float fovy, float aspect, float zNear, float zFar) {
	float f = 1.0 / tan(fovy / 2.0);
	return [f/aspect,0,0,0,
		   0,f,0,0,
		   0,0,(zFar+zNear)/(zNear-zFar),-1,
		   0,0,(2*zFar*zNear)/(zNear-zFar),0];
}
#endif

Matrix4 orthoM(float left, float right, float bottom, float top, float near, float far) {
	float tx = -(right+left)/(right-left);
	float ty = -(top+bottom)/(top-bottom);
	float tz = -(far+near)/(far-near);
	return initMatrix(
		2/(right-left), 0, 0, 0,
		0, 2/(top-bottom), 0, 0,
		0, 0, -2/(far-near), 0,
		tx, ty, tz, 1);
}
