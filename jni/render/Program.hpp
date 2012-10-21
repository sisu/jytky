#ifndef ZARD_PROGRAM_HPP
#define ZARD_PROGRAM_HPP

#include "Shader.hpp"

struct Program {
	GLuint id;
	Shader vertex, fragment;

	Program();
	void load();

	static Program fromFiles(const char* sfile, const char* ffile);
};

#endif
