package fi.jytky;

import java.util.ArrayList;
import java.util.List;

public class Game {
	public Game() {
		player = new Player();
		player.game = this;
		prevT = System.currentTimeMillis();
		stage = new FirstStage();
//		stage = new TestStage();
	}

	Stage stage;
	long prevT;
	float width, height;

	void update() {
		long time = System.currentTimeMillis();
		float dt = (time - prevT) / 1000.f;
		prevT = time;

		stage.update(this, dt);
//		spawnBots(dt);
		player.update(dt);
		player.pos = new Vector(
				Math.max(0, Math.min(width, player.pos.x)),
				Math.max(0, Math.min(height, player.pos.y)));

		updateBots(dt);
		updateBullets(dt);
		removeDead(bots);
		removeDead(bullets);
		removeDead(playerBullets);
	}

	float spawnTime = 1;
	void spawnBots(float dt) {
		spawnTime -= dt;
		if (spawnTime > 0) return;
		spawnTime += 1;
		Bot b = new Bot();
		b.pos = new Vector(-.1f, height*3/4);
		b.vel = new Vector(0.6f, .3f*((float)Math.random()-.5f));
		b.size = 0.1f;
		bots.add(b);
//		Log.i("asd", "Spawning bot; "+b.pos+" "+b.vel);
	}

	Player player;
	ArrayList<Bot> bots = new ArrayList<Bot>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();

	private void updateBullets(float dt) {
		for(Bullet b : bullets) {
			b.update(dt);
			b.hit |= b.hits(player);
		}
		for(Bullet b : playerBullets) {
			b.update(dt);
			for(Bot x : bots) {
				if (b.hits(x)) {
					b.hit = true;
					x.health -= .26f;
					break;
				}
			}
		}
	}

	private void updateBots(float dt) {
		for(Bot b : bots) {
			b.runAI(this, dt);
			b.update(dt);
		}
	}

	private static <T extends Unit> void removeDead(List<T> v) {
		for(int i=0; i<v.size(); ) {
			T t = v.get(i);
			if (!t.alive()) {
				v.set(i, v.get(v.size()-1));
				v.remove(v.size()-1);
			} else {
				++i;
			}
		}
	}
}
