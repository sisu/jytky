package fi.jytky;

import android.graphics.Color;

public class Bullet extends Unit {
	
	static final int PLAYER = 1, BOT = 2;
	
	int shooter;
	
	Bullet(float rad, int shooter) {
		this.size = rad;
		this.shooter = shooter;
	}
	
	boolean hits(Unit u) {
		float d = size + u.size;
		return pos.dist2(u.pos) < d*d;
	}

	@Override
	public int getColor() {
		return Color.rgb(255,0,0);
	}
}
