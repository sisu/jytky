package fi.jytky;

import android.graphics.Color;

public class Unit implements Drawable {

	Vec2 pos;
	Vec2 vel;
	float size;

	Unit() {
		pos = new Vec2(0,0);
		vel = new Vec2(0,0);
		size = 0;
	}

	void update(float dt) {
		pos = pos.add(dt*vel.x, dt*vel.y);
//		pos = pos.add(vel.mult(dt));
	}
	boolean alive() {
		return true;
	}

	public int getColor() {
		return Color.rgb(255,0,0);
	}
	public Vec2 getPos() {
		return pos;
	}
	public float getSize() {
		return 1.5f*size;
	}
}
