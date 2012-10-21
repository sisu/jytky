#include "Buffer.hpp"
#include <util/Log.hpp>
LOG_CHANNEL(Buffer);

Buffer::~Buffer() {
	glDeleteBuffers(1, &id);
}

void Buffer::load(GLenum usage) {
	bind(0);
	glBufferData(target, index.back(), 0, usage);
	LOG<<"created buffer of size "<<index.back();
	for(int i=0; i<ptrs.size; ++i) {
		LOG<<"writing to indices "<<index[i]<<" - "<<index[i+1];
		glBufferSubData(target, index[i], index[i+1]-index[i], ptrs[i]);
	}
	CHECK_GL();
}
void Buffer::bind(GLuint prog) {
	if (!id) glGenBuffers(1, &id);
	gl.bindBuffer(target, id);
	CHECK_GL();
	if (!prog) return;

	for(int i=0; i<names.size; ++i) {
		GLint idx = glGetAttribLocation(prog, names[i].c_str());
		if (idx<0) continue;
		glEnableVertexAttribArray(idx);
		// FIXME: assuming 3 elements and floats
		glVertexAttribPointer(idx, 3, GL_FLOAT, 0, 0, (void*)index[i]);
	}
}
