package fi.jytky;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.Collections;

public class FirstStage implements Stage {

	private void init() {
		float prev = 0;
		for(int i=0; i<20; ++i) {
			Bot b = new Bot();
			boolean odd = (i&1)!=0;
			if (i>=10) odd = i>=15;
			b.pos = new Vector(odd ? 1.05f : -.05f, 1.3f);
			b.vel = new Vector((odd ? -1 : 1)*.4f, .2f*((float)Math.random()-.5f));
			b.lifeTime = 2.5f;
			prev += i<10 ? 1.5f : .5f;
			spawns.add(new Spawn(b, prev));
		}
		prev += 3.f;
		spawns.add(new Spawn(new CircleShoot(false), prev));
		prev += 3.f;
		spawns.add(new Spawn(new CircleShoot(false), prev));
		spawns.add(new Spawn(new CircleShoot(true), prev));
		prev += 3.f;
		spawns.add(new Spawn(new CircleShoot(false), prev));
		spawns.add(new Spawn(new CircleShoot(true), prev));
		prev += 1.f;
		spawns.add(new Spawn(new CircleShoot(false), prev));
		spawns.add(new Spawn(new CircleShoot(true), prev));
	}

	class CircleShoot extends Bot {

		private CircleShoot(boolean odd) {
			pos = new Vector(odd ? 1.05f : -.05f, 1.2f);
			vel = new Vector((odd ? -1 : 1)*.3f, 0);
			health = 2;
			lifeTime = 3;
			shootTime = 1;
		}

		@Override
		void runAI(Game game, float dt) {
			shootTime -= dt;
			if (shootTime > 0) return;
			shootTime += 1;

			int n = 16;
			for(int i=0; i<n; ++i) {
				Bullet b = new Bullet(.03f, Bullet.BOT);
				float a = (float) (2*Math.PI* i/n);
				b.pos = pos;
				b.vel = Vector.polar(.3f, a);
				game.bullets.add(b);
			}
		}

		@Override
		public int getColor() {
			return Color.rgb(255, 50, 255);
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
		while(nextSpawn < spawns.size() && spawns.get(nextSpawn).time <= curTime) {
			game.bots.add(spawns.get(nextSpawn++).bot);
		}
	}

}
