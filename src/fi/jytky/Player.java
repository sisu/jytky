package fi.jytky;

import android.graphics.Color;

public class Player extends Unit {

	boolean shooting = false;
	float shootTime;
	Game game;

	public Player() {
		pos = new Vector(.5f, .5f);
		size = 0.05f;
	}

	@Override
	public int getColor() {
		return Color.rgb(0, 255, 0);
	}

	@Override
	void update(float dt) {
		super.update(dt);
		if (shooting) {
			shootTime -= dt;
			while (shootTime <= 0) {
				shootTime += .1f;
				
				Bullet b = new Bullet(.05f, Bullet.PLAYER);
				game.playerBullets.add(b);
			}
		}
	}
}
