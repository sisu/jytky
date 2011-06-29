package fi.jytky;

import android.graphics.Color;

public class Unit implements Drawable {

	Vector pos;
	Vector vel;
	float size;

	Unit() {
		pos = new Vector(0,0);
		vel = new Vector(0,0);
		size = 0;
	}

	void update(float dt) {
		pos = pos.add(vel.mult(dt));
	}

	public int getColor() {
		return Color.rgb(255,0,0);
	}
	public Vector getPos() {
		return pos;
	}
	public float getSize() {
		return 1.5f*size;
	}
}
