#include "Renderer.hpp"
#include "Drawable.hpp"
#include <cassert>

void Renderer::reset() {
	drawables.clear();
}
void Renderer::add(Drawable& drawable) {
	drawables.add(&drawable);
}
bool drawableCmp(Drawable* a, Drawable* b) {
	// TODO
	return a<b;
}
void Renderer::finish() {
	gl.useProgram(program.id);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	gl.enable(GL_DEPTH_TEST);

//	sort(models, modelCmp);
	for(int i=0; i<drawables.size; ++i) {
//		LOGI("drawing drawable %d\n", i);
		Drawable& d = *drawables[i];
		draw(d);
	}
	glFlush();
	CHECK_GL();
}
void Renderer::draw(Drawable& d) {
	GLint idx = glGetUniformLocation(program.id, "trans");
	glUniformMatrix4fv(idx, 1, 0, (transform * d.transform()).ptr());

	Model& m = *d.model;
	assert(&m);
	m.bind(program.id);
	CHECK_GL();

	glDrawElements(GL_TRIANGLES, m.indices.size, GL_UNSIGNED_SHORT, 0);
}
