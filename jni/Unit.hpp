#pragma once

#include <util/VecBase.hpp>
#include <render/Drawable.hpp>

struct Unit: Drawable {
	Vec2 pos;
	Vec2 vel;

	virtual ~Unit() {}
	virtual void update(float dt);

	Matrix4 transform();
};
