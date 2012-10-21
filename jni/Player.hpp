#pragma once
#include "Unit.hpp"

struct Player: Unit {
	virtual void update(float dt);
	Vec2 move;
};
