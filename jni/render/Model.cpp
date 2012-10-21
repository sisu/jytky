#include "Model.hpp"
#include "GL.hpp"
#include <util/Log.hpp>

Model::Model(): vbuf(GL_ARRAY_BUFFER), ibuf(GL_ELEMENT_ARRAY_BUFFER)
{
}

void Model::bind(GLuint prog) {
	vbuf.bind(prog);
	CHECK_GL();
//	LOGI("setting vertex pointer %d\n", vbuf.index[0]);
//	glBufferData(GL_ARRAY_BUFFER, verts.size*sizeof(Vec3), &verts[0], GL_STATIC_DRAW);
#if 0
	gl.vertexPointer(3, GL_FLOAT, sizeof(Vec3), (void*)vbuf.index[0]);
	if (vbuf.size(1))
		gl.normalPointer(GL_FLOAT, sizeof(Vec3), (void*)vbuf.index[1]);
	if (vbuf.size(2))
		gl.texCoordPointer(2, GL_FLOAT, sizeof(Vec2), (void*)vbuf.index[2]);
#endif

	ibuf.bind(0);
}

void Model::load() {
#if 1
//	Log()<<normals;
	for(auto i=vattrs.begin(); i!=vattrs.end(); ++i) {
		vbuf.add(i->second, i->first);
	}
	vbuf.load();
#else
	vbuf.bind();
	glBufferData(GL_ARRAY_BUFFER, verts.size*sizeof(Vec3), &verts[0], GL_STATIC_DRAW);
#endif
	CHECK_GL();

//	for(int i=0; i<indices.size; ++i) LOGI("%d %d\n",i,indices[i]);
	ibuf.add(indices,"idx");
	ibuf.load();
	CHECK_GL();
}
