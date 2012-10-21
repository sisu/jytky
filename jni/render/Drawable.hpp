#pragma once
#include "Model.hpp"
#include "util/Matrix.hpp"
#include <memory>

struct Drawable {
	ModelPtr model;
	virtual Matrix4 transform() {
		return Matrix4(Identity());
	}

	Drawable() {}
};
typedef std::shared_ptr<Drawable> DrawablePtr;
