package fi.jytky;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;


public class Jytky extends Activity {

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
		printSamples(evt);
		queueEvent(new Runnable() {

			public void run() {
				game.player.vel = Vec2.zero;
				game.player.shooting = false;
				if (evt.getActionMasked()==MotionEvent.ACTION_UP) return;

				for(int i=0; i<evt.getPointerCount(); ++i) {
					if (evt.getActionMasked() == MotionEvent.ACTION_POINTER_UP && evt.getActionIndex()==i) continue;
					Vec2 v = new Vec2(evt.getX(i) / getWidth(), (getHeight() - evt.getY(i)) / getWidth());
					if (v.dist(renderer.touchMid) < 4 * renderer.touchSize) {
						Vec2 d = v.sub(renderer.touchMid).div(renderer.touchSize);
						if (d.length2() > 1) d = d.normalize();
						game.player.vel = d.mult(0.6f);
					} else if (v.dist(renderer.buttonMid) < 1.2*renderer.buttonSize) {
//						log("shooting");
						game.player.shooting = true;
					}
				}
			}
		});
		return true;
	}

	void printSamples(MotionEvent ev) {
		log(ev.toString());
		final int pointerCount = ev.getPointerCount();
//		final int historySize = ev.getHistorySize();
//		for (int h = 0; h < historySize; h++) {
//			log("At time %d:", ev.getHistoricalEventTime(h));
//			for (int p = 0; p < pointerCount; p++) {
//				log("  pointer %d: (%f,%f)",
//						ev.getPointerId(p), ev.getHistoricalX(p, h), ev.getHistoricalY(p, h));
//			}
//		}
//		log("At time %d:", ev.getEventTime());
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
