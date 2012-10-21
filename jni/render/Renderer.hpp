#pragma once

#include "Program.hpp"
#include <util/Matrix.hpp>
class Drawable;

struct Renderer {
	void reset();
	void add(Drawable& drawable);
	void finish();

	Program program;
	Matrix4 transform;
private:
	Array<Drawable*> drawables;

	void draw(Drawable& d);
};
