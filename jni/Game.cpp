#include "Game.hpp"
#include "modelgen.hpp"
#include <util/math.hpp>
#include <render/projection.hpp>
Game game;
const double FRAME_TIME = 1./60;

void Game::update(double time) {
	while(prevTime < time) {
		prevTime += FRAME_TIME;
		moveUnits(FRAME_TIME);
	}
}
void Game::moveUnits(double dt) {
	for(int i=0; i<units.size; ++i)
		units[i]->update(dt);
	player.pos[0] = clamp(player.pos[0], 0, 1);
	player.pos[1] = clamp(player.pos[1], 0, 1);
}
void Game::draw() {
	renderer.reset();
	renderer.add(player);
	renderer.transform = orthoM(0, 1, 0, 1, -2, 100);
	renderer.finish();
}
void Game::init(double time) {
	player.model = makeCube();
	player.model->load();
	units.add(&player);
	prevTime = time;

	renderer.program = Program::fromFiles("t.vert", "t.frag");
}
