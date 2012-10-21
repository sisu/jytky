#pragma once
#include <util/Vector.hpp>

struct Camera {
	Vec3 pos;
	Vec3 front;

	void apply();
};
