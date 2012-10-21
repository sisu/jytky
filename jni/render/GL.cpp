#include "GL.hpp"
#include "util/Log.hpp"
#include <cassert>
#include <cstdlib>
//#include <GL/glu.h>
#include <cstring>

LOG_CHANNEL(GL);

GL::GL() {
	memset(enabled,0,sizeof(enabled));
	memset(clientState,0,sizeof(clientState));
	program = 0;
}

void GL::enable(GLenum cap) {
	assert(cap < sizeof(enabled));
	if (!enabled[cap]) {
		glEnable(cap);
		enabled[cap] = 1;
	}
}
void GL::disable(GLenum cap) {
	assert(cap < sizeof(enabled));
	if (enabled[cap]) {
		glDisable(cap);
		enabled[cap] = 0;
	}
}
void GL::bindBuffer(GLenum target, GLuint buffer) {
	if (buffers[target] != buffer) {
//		LOGI("binding buffer %d %d\n", target, buffer);
		glBindBuffer(target, buffer);
		buffers[target] = buffer;
	}
}
void GL::useProgram(GLuint prog) {
	if (prog == program) return;
	program = prog;
	glUseProgram(program);
}

void GL::check(const char* file, int line) {
	GLenum err = glGetError();
	if (err) {
//		LOG<<"GL error @ "<<file<<' '<<line<<" : "<<gluErrorString(err);
		LOG<<"GL error @ "<<file<<' '<<line<<" : "<<err;
		abort();
	}
}

GL gl;
