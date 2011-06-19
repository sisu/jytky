package fi.shmup;

public class Player {

	Vector pos;
	Vector vel;
	
	Player() {
		pos = new Vector(0,0);
		vel = new Vector(0,0);
	}
	void update(float dt) {
		pos = pos.add(vel.mult(dt));
	}
}
