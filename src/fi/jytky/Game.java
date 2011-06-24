package fi.jytky;

import java.util.ArrayList;

public class Game {

	public Game() {
		player = new Player();
		prevT = System.currentTimeMillis();
	}

	long prevT;
	float width, height;

	void update() {
		long time = System.currentTimeMillis();
		float dt = (time - prevT) / 1000.f;
		prevT = time;
		
		spawnBots(dt);
		player.update(dt);
		player.pos = new Vector(
				Math.max(0, Math.min(width, player.pos.x)),
				Math.max(0, Math.min(height, player.pos.y)));
		
		for(Bot b : bots) {
			b.runAI(this, dt);
			b.update(dt);
		}
		
		updateBullets(dt);
	}
	
	float spawnTime = 1;
	void spawnBots(float dt) {
		spawnTime -= dt;
		if (spawnTime > 0) return;
		spawnTime += 1;
		Bot b = new Bot();
		b.pos = new Vector(-1.f, height*2/3);
		b.vel = new Vector(0.6f, (float)Math.random()-.5f);
		bots.add(b);
//		Log.i("asd", "Spawning bot; "+b.pos+" "+b.vel);
	}

	Player player;
	ArrayList<Bot> bots = new ArrayList<Bot>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	private void updateBullets(float dt) {
		for(Bullet b : bullets) {
			b.update(dt);
		}
	}
}
