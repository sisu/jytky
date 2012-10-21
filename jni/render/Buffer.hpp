#pragma once

#include "GL.hpp"
#include "util/Array.hpp"
#include "util/nocopy.hpp"
#include <string>

struct Buffer: Noncopyable {
	void load(GLenum usage = GL_STATIC_DRAW);
	void bind(GLuint prog);

	template<class T>
	void add(const Array<T>& arr, std::string name) {
		ptrs.add(&arr[0]);
		index.add(index.back() + arr.size * sizeof(T));
		names.add(name);
	}
	int size(int i) { return index[i+1] - index[i]; }

	Buffer(GLenum target): target(target), id(0) { index.add(0); }
	~Buffer();

private:
	Array<const void*> ptrs;
	Array<std::string> names;
	Array<size_t> index;
	GLenum target;
	GLuint id;
};
