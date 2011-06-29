package fi.jytky;

import android.graphics.Color;

public class Bot extends Unit {

	public Bot() {
		size = .05f;
	}

	@Override
	public int getColor() {
		return Color.rgb(255,128,0);
	}
	
	float lifeTime = 2.f;
	float shootTime = 0.5f;
	void runAI(Game game, float dt) {
		lifeTime -= dt;
		shootTime -= dt;
		if (shootTime > 0) return;
		shootTime += 1.2f;
		
		Bullet b = new Bullet(.02f, Bullet.BOT);
		b.pos = pos;
		b.vel = game.player.pos.sub(pos).normalize().mult(.4f);
		game.bullets.add(b);
	}
	
	boolean alive() {
		return lifeTime > 0;
	}
}
