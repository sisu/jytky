#pragma once

#include "Unit.hpp"
#include "Player.hpp"
#include <render/Renderer.hpp>
#include <util/Array.hpp>

struct Game {
	Renderer renderer;
	Player player;
	Array<Unit*> units;

	void init(double time);
	void update(double time);
	void draw();

private:
	void moveUnits(double dt);
	double prevTime;
};

extern Game game;
