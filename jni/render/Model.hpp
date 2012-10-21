#ifndef ZARD_MODEL
#define ZARD_MODEL

#include "util/Array.hpp"
#include "util/Vector.hpp"
#include "util/nocopy.hpp"
#include "Buffer.hpp"
#include <memory>
#include <cassert>
#include <map>
#include <string>

struct Model: Noncopyable {
	Array<short> indices;

	void addTriangle(short a, short b, short c) {
		indices.add(a);
		indices.add(b);
		indices.add(c);
	}
#if 0
	void addTriangle(Vec3 a, Vec3 b, Vec3 c) {
		assert(!texCoords.size);
		addTriangle(verts.size, verts.size+1, verts.size+2);
		verts.add(a);
		verts.add(b);
		verts.add(c);
		Vec3 normal = normalize(cross(b-a,c-a));
		normals.add(normal);
		normals.add(normal);
		normals.add(normal);
	}
#endif

	void bind(GLuint prog);
	void load();

	Model();

	template<int N>
	void setAttr(std::string name, const Array<Vector<float,N>>& arr) {
		setAttr(name, &arr[0], arr.size);
	}
	template<int N>
	void setAttr(std::string name, const Vector<float,N>* arr, int k) {
		Array<float> v(N*k);
		for(int i=0; i<k; ++i)
			for(int j=0; j<N; ++j)
				v[N*i+j] = arr[i][j];
		vattrs[name] = v;
	}

private:
	Buffer vbuf;
	Buffer ibuf;
	std::map<std::string,Array<float> > vattrs;
};
typedef std::shared_ptr<Model> ModelPtr;

#endif
