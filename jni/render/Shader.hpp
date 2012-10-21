#pragma once

#include "GL.hpp"

struct Shader {
	Shader(GLenum type);
	void loadFile(const char* file);
	void loadSource(const char* file);

	GLenum type;
	GLuint id;
};
