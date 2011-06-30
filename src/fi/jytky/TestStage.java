package fi.jytky;

public class TestStage implements Stage {

	float spawnTime = 1;
	public void update(Game game, float dt) {
		spawnTime -= dt;
		if (spawnTime > 0) return;
		spawnTime += 1;
		Bot b = new Bot();
		b.pos = new Vector(-.1f, game.height*3/4);
		b.vel = new Vector(0.6f, .3f*((float)Math.random()-.5f));
		b.size = 0.1f;
		game.bots.add(b);
	}

}
