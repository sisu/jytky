package fi.jytky;

import java.util.ArrayList;

public class Game {

	public Game() {
		player = new Player();
		prevT = System.currentTimeMillis();
	}

	long prevT;
	float width, height;

	void update() {
		long time = System.currentTimeMillis();
		float dt = (time - prevT) / 1000.f;
		prevT = time;
		player.update(dt);
		player.pos = new Vector(
				Math.max(0, Math.min(width, player.pos.x)),
				Math.max(0, Math.min(height, player.pos.y)));
	}

	Player player;
	ArrayList<Bot> bots;
}
