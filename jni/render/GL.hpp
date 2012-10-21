#pragma once

#ifdef ANDROID
#include <GLES2/gl2.h>
#else
#define GL_GLEXT_PROTOTYPES
#include <GL/gl.h>
//#include <GLES2/gl2.h>
#endif

#include "util/ArrayMap.hpp"

struct GL {
	GL();

	void enable(GLenum cap);
	void disable(GLenum cap);
	void bindBuffer(GLenum target, GLuint buffer);
	void useProgram(GLuint program);

	void check(const char* file, int line);

private:
	bool enabled[1<<16];
	bool clientState[1<<16];
	ArrayMap<GLenum,GLuint> buffers;
	GLuint program;
};

extern GL gl;

#define CHECK_GL() gl.check(__FILE__, __LINE__)
