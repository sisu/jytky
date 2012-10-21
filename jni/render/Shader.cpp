#include "Shader.hpp"
#include <util/Log.hpp>
#include <util/file.hpp>
#include <cstdlib>
#include <cassert>
LOG_CHANNEL(Shader);

Shader::Shader(GLenum type): type(type), id(0) {}

void Shader::loadFile(const char* file) {
	std::string s = ::loadFile(file);
	loadSource(s.c_str());
}

void Shader::loadSource(const char* str) {
	CHECK_GL();
	if (!id) id = glCreateShader(type);
	glShaderSource(id, 1, &str, 0);
	glCompileShader(id);
	CHECK_GL();

	int status;
	glGetShaderiv(id, GL_COMPILE_STATUS, &status);
	if (!status) {
		GLsizei elen;
		const int MAX_SIZE=1<<16;
		char buf[MAX_SIZE];
		glGetShaderInfoLog(id, MAX_SIZE, &elen, buf);
		LOG<<"Compiling shader failed: "<<buf;
		LOG<<"Shader:\n"<<str;
		abort();
	}
	CHECK_GL();
}
