#include "Program.hpp"
#include <util/Log.hpp>
LOG_CHANNEL(Program);

Program::Program(): id(0), vertex(GL_VERTEX_SHADER), fragment(GL_FRAGMENT_SHADER) {}

void Program::load() {
	if (id) return;
	id = glCreateProgram();
	if (vertex.id) glAttachShader(id, vertex.id);
	if (fragment.id) glAttachShader(id, fragment.id);
	glLinkProgram(id);
	CHECK_GL();
}

Program Program::fromFiles(const char* sfile, const char* ffile) {
	Program p;
	if (sfile) {
		p.vertex.loadFile(sfile);
	}
	if (ffile) {
		p.fragment.loadFile(ffile);
	}
	p.load();
	return p;
}
