package fi.shmup;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class Shmup extends Activity {
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
		renderer = new GameRenderer();
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
					renderer.player.vel = new Vector(0, 0);
				} else {
					Vector v = new Vector(evt.getX()/getWidth(), (getHeight()-evt.getY())/getWidth());
					Vector mmid = renderer.touchMid;
					float dist = renderer.touchSize;
					if (v.dist(mmid) < 3*dist) {
						Vector d = v.sub(mmid).div(dist);
						if (d.length2()>1) d = d.normalize();
						renderer.player.vel = d.mult(0.8f);
					} else {
						renderer.player.vel = new Vector(0, 0);
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
}

class GameRenderer implements GLSurfaceView.Renderer {
	
	Player player;
	long prevT;
	int width, height;
	float areah;
	float touchSize = 0.15f;
	Vector touchMid = new Vector(1-1.3f*touchSize, 1.3f*touchSize);

	@Override
	public void onDrawFrame(GL10 gl) {
		update();
		setProj(gl);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		drawPlayer(gl);
		drawTouch(gl);
	}
	private void drawTouch(GL10 gl) {
		int n = 16;
		float[] vs = new float[2*n];
		for(int i=0; i<n; ++i) {
			float a = (float) (2*Math.PI*i/n);
			vs[2*i] = (float) (touchMid.x + touchSize * Math.cos(a));
			vs[2*i+1] = (float) (touchMid.y + touchSize * Math.sin(a));
		}
//		Log.i("asd", vs[0]+" "+vs[1]);
		gl.glColor4f(1, 0, 0, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, makeBuf(vs));
		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, n);
	}
	private void update() {
		long time = System.currentTimeMillis();
		float dt = (time - prevT) / 1000.f;
		prevT = time;
		player.update(dt);
		player.pos = new Vector(
				Math.max(0, Math.min(1, player.pos.x)),
				Math.max(0, Math.min(areah, player.pos.y)));
	}
	private void setProj(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, 1, 0, areah);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}
	void drawPlayer(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(player.pos.x, player.pos.y, 0);
		float s = .1f;
		gl.glScalef(s, s, s);
		float[] vs = new float[]{-1,-1,1,-1,0,1};
		gl.glColor4f(0, 1, 0, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, makeBuf(vs));
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glPopMatrix();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		this.width = width;
		this.height = height;
		areah = (float)height/width;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		player = new Player();
		player.pos = new Vector(.5f, .5f);
		prevT = System.currentTimeMillis();
	}
	
	private static FloatBuffer makeBuf(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(4 * arr.length);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
}