package fi.jytky;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new GameView(this);
		//		setContentView(R.layout.main);
		setContentView(view);
	}

	@Override
	protected void onPause() {
		super.onPause();
		view.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		view.onResume();
	}
	private GLSurfaceView view;
}

class GameView extends GLSurfaceView {

	GameView(Context ctx) {
		super(ctx);
		game = new Game();
		renderer = new GameRenderer(game);
		setRenderer(renderer);
		setDebugFlags(DEBUG_CHECK_GL_ERROR);
	}

	@Override
	public boolean onTouchEvent(final MotionEvent evt) {
		log(evt.toString());
		printSamples(evt);
		queueEvent(new Runnable() {

			public void run() {
				if (evt.getAction() == MotionEvent.ACTION_UP) {
					game.player.vel = new Vector(0, 0);
				} else {
					Vector v = new Vector(evt.getX() / getWidth(), (getHeight() - evt.getY()) / getWidth());
					Vector mmid = renderer.touchMid;
					float dist = renderer.touchSize;
					if (v.dist(mmid) < 3 * dist) {
						Vector d = v.sub(mmid).div(dist);
						if (d.length2() > 1) {
							d = d.normalize();
						}
						game.player.vel = d.mult(0.8f);
					} else {
						game.player.vel = new Vector(0, 0);
					}
				}
			}
		});
		return true;
	}

	void printSamples(MotionEvent ev) {
		final int historySize = ev.getHistorySize();
		final int pointerCount = ev.getPointerCount();
		for (int h = 0; h < historySize; h++) {
			log("At time %d:", ev.getHistoricalEventTime(h));
			for (int p = 0; p < pointerCount; p++) {
				log("  pointer %d: (%f,%f)",
						ev.getPointerId(p), ev.getHistoricalX(p, h), ev.getHistoricalY(p, h));
			}
		}
		System.out.printf("At time %d:", ev.getEventTime());
		for (int p = 0; p < pointerCount; p++) {
			log("  pointer %d: (%f,%f)",
					ev.getPointerId(p), ev.getX(p), ev.getY(p));
		}
	}

	void log(String s, Object... args) {
		Log.i("asd", String.format(s, args));
	}
	GameRenderer renderer;
	Game game;
}
