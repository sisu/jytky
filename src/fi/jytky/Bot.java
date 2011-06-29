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
	
	float shootTime = 0;
	void runAI(Game game, float dt) {
		shootTime -= dt;
		if (shootTime > 0) return;
		shootTime += .5f;
		
		Bullet b = new Bullet(.02f, Bullet.BOT);
		b.pos = pos;
		b.vel = game.player.pos.sub(pos).normalize().mult(.5f);
		game.bullets.add(b);
	}
}
