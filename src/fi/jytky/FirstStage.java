package fi.jytky;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.Collections;

public class FirstStage implements Stage {

	private void init() {
		float prev = 0;
		for (int i = 0; i < 20; ++i) {
			Bot b = new Bot();
			boolean odd = (i & 1) != 0;
			if (i >= 10) {
				odd = i >= 15;
			}
			b.pos = new Vector(odd ? 1.05f : -.05f, 1.3f);
			b.vel = new Vector((odd ? -1 : 1) * .4f, .2f * ((float) Math.random() - .5f));
			b.lifeTime = 2.5f;
			prev += i < 10 ? 1.5f : .5f;
			spawns.add(new Spawn(b, prev));
		}
		prev += 3.f;
		spawns.add(new Spawn(new CircleShoot(false), prev));
		for (int i = 0; i < 3; ++i) {
			prev += i < 2 ? 3.f : 0.6f;
			spawns.add(new Spawn(new CircleShoot(false), prev));
			spawns.add(new Spawn(new CircleShoot(true), prev));
		}

		prev += 9.f;
		spawns.add(new Spawn(new Boss(), prev));
	}

	private static class CircleShoot extends Bot {

		private CircleShoot(boolean odd) {
			pos = new Vector(odd ? 1.05f : -.05f, 1.2f);
			vel = new Vector((odd ? -1 : 1) * .3f, 0);
			health = 2;
			lifeTime = 3;
			shootTime = 1;
		}

		@Override
		void runAI(Game game, float dt) {
			shootTime -= dt;
			if (shootTime > 0) {
				return;
			}
			shootTime += 1;

			int n = 12;
			for (int i = 0; i < n; ++i) {
				Bullet b = new Bullet(.03f, Bullet.BOT);
				float a = (float) (2 * Math.PI * i / n);
				b.pos = pos;
				b.vel = Vector.polar(.2f, a);
				b.lifeTime = 8;
				game.bullets.add(b);
			}
		}

		@Override
		public int getColor() {
			return Color.rgb(255, 50, 255);
		}
	}

	private static class Boss extends Bot {

		public Boss() {
			pos = new Vector(.5f, 1.2f);
			vel = Vector.zero;
			size = .1f;
			lifeTime = 300;
			health = 20;
			shootTime = .5f;
		}
		float shootTime2;
		int bulletsLeft = 0;

		@Override
		void runAI(Game game, float dt) {
			shootTime -= dt;
			shootTime2 -= dt;
			if (shootTime < 0) {
				shootTime += 2.5f;
				shootTime2 = 0;
				bulletsLeft = 6;
			}
			if (shootTime2 <= 0 && bulletsLeft > 0) {
				shootTime2 += .3f;
				--bulletsLeft;

				float ang = game.player.pos.sub(pos).angle();
				int n = 16;
				for (int i = 0; i < n; ++i) {
					Bullet b = new Bullet(.02f, Bullet.BOT);
					float a = (float) (2 * Math.PI * i / n);
					a += .1f * ((float)Math.random()-.5f);
					b.pos = pos;
					b.vel = Vector.polar(.2f, ang + a);
					b.lifeTime = 8;
					game.bullets.add(b);
				}
			}
		}

		@Override
		public int getColor() {
			return Color.rgb(150, 150, 150);
		}
	}

	class Spawn implements Comparable<Spawn> {

		public Spawn(Bot bot, float time) {
			this.bot = bot;
			this.time = time;
		}
		Bot bot;
		float time;

		public int compareTo(Spawn s) {
			return Float.compare(time, s.time);
		}
	}
	ArrayList<Spawn> spawns = new ArrayList<Spawn>();
	int nextSpawn = 0;
	float curTime = 0;

	public FirstStage() {
		init();
		Collections.sort(spawns);
	}

	public void update(Game game, float dt) {
		curTime += dt;
		while (nextSpawn < spawns.size() && spawns.get(nextSpawn).time <= curTime) {
			game.bots.add(spawns.get(nextSpawn++).bot);
		}
	}
}
