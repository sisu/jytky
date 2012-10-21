#include "Unit.hpp"
#include <util/Log.hpp>
LOG_CHANNEL(Unit);

void Unit::update(float dt) {
	pos += vel*dt;
}

Matrix4 Unit::transform() {
	const float s = 0.1;
	return Matrix4(translate(pos)) * Matrix4(scale(s,s));
}
