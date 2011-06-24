package fi.jytky;

import android.graphics.Color;

public class Bot extends Unit {

	@Override
	public int getColor() {
		return Color.rgb(0,128,255);
	}
	
	float shootTime = 0;
	void runAI(Game game, float dt) {
		shootTime -= dt;
		if (shootTime > 0) return;
		Bullet b = new Bullet(.05f, Bullet.BOT);
		b.pos = pos;
		b.vel = game.player.pos.sub(pos).normalize().mult(2);
		game.bullets.add(b);
	}
}
