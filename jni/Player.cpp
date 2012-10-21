#include "Player.hpp"

void Player::update(float dt) {
	vel = 0.5f*move;
	Unit::update(dt);
}
