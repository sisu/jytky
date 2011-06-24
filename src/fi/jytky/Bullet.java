package fi.jytky;

public class Bullet extends Unit {
	
	static final int PLAYER = 1, BOT = 2;
	
	float rad;
	int shooter;
	
	Bullet(float rad, int shooter) {
		this.rad = rad;
		this.shooter = shooter;
	}
}
