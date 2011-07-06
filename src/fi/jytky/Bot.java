package fi.jytky;

import android.graphics.Color;

public class Bot extends Unit {

	public Bot() {
		size = .03f;
	}

	@Override
	public int getColor() {
		return Color.rgb(255,128,0);
	}

	float health = 1.f;

	float lifeTime = 2.f;
	float shootTime = 0.5f;
	void runAI(Game game, float dt) {
		shootTime -= dt;
		if (shootTime > 0) return;
		shootTime += 0.7f;

		Bullet b = new Bullet(.01f, Bullet.BOT);
		b.pos = pos;
		b.vel = game.player.pos.sub(pos).normalize().mult(.4f);
		game.bullets.add(b);
	}

	@Override
	void update(float dt) {
		lifeTime -= dt;
		super.update(dt);
	}

	@Override
	boolean alive() {
		return lifeTime > 0 && health > 0;
	}

	@Override
	public float getSize() {
		return size;
	}
}
