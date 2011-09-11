package fi.jytky;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameRenderer implements GLSurfaceView.Renderer {

	Renderer render = new Renderer();

	public GameRenderer(Game game) {
		this.game = game;
	}

	Game game;
	float touchSize = 0.1f;
	Vec2 touchMid = new Vec2(.9f - 1.5f * touchSize, .1f + 1.5f * touchSize);
	Vec2 buttonMid = new Vec2(.15f, 1.2f);
	float buttonSize = .12f;

	public void onSurfaceCreated(GL10 gl, EGLConfig eglc) {
		makeBufs();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		game.width = 1;
		game.height = (float) height / width;
	}

	int frames=0;
	long ftime=System.currentTimeMillis()/1000;

	public void onDrawFrame(GL10 gl) {
		long ctime = System.currentTimeMillis() / 1000;
		while(ftime < ctime) {
			Log.i("ASD", "FPS: "+frames);
			++ftime;
			frames=0;
		}
		++frames;
		game.update();

		render.width = game.width;
		render.height = game.height;
		render.init(gl);
		render.draw(game.player);
		for(Bot b : game.bots) render.draw(b);
		for(Bullet b : game.bullets) render.draw(b);
		for(Bullet b : game.playerBullets) render.draw(b);
		render.finish();
		drawButtons(gl);
	}

	private void drawButtons(GL10 gl) {
		gl.glColor4f(1, 0, 0, 1);
		drawCircle(gl, touchMid, touchSize);
		gl.glColor4f(0, 0, 1, 1);
		drawCircle(gl, buttonMid, buttonSize);
	}

	private void setProj(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, game.width, 0, game.height);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	private static FloatBuffer makeBuf(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(4 * arr.length);
		FloatBuffer fb = bb.order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	void draw(GL10 gl, Drawable d) {
		int color = d.getColor();
//		Log.i("asd", "color: "+color);
		int b = color&0xff, g = (color>>>8)&0xff, r = (color>>>16)&0xff;
		gl.glColor4f(r/255.f, g/255.f, b/255.f, 1);
//		gl.glColor4x(color&0xff, (color>>>8)&0xff, (color>>>16)&0xff, 255);
//		gl.glColor4f(0, 1, 0, 1);
		gl.glPushMatrix();
		Vec2 pos = d.getPos();
		gl.glTranslatef(pos.x, pos.y, 0);
		float s = d.getSize();
		gl.glScalef(s, s, s);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, triBuf);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glPopMatrix();
	}
	FloatBuffer triBuf;
	FloatBuffer circleBuf;
	final int circleVs = 16;

	private void makeBufs() {
		triBuf = makeBuf(new float[]{-1, -1, 1, -1, 0, 1});
		circleBuf = makeCircle(circleVs);
	}

	private FloatBuffer makeCircle(int n) {
		float[] vs = new float[2 * n];
		for (int i = 0; i < n; ++i) {
			float a = (float) (2 * Math.PI * i / n);
			vs[2*i] = (float)Math.cos(a);
			vs[2*i+1] = (float)Math.sin(a);
		}
		return makeBuf(vs);
	}

	private void drawCircle(GL10 gl, Vec2 touchMid, float touchSize) {
		gl.glPushMatrix();
		gl.glTranslatef(touchMid.x, touchMid.y, 0);
		gl.glScalef(touchSize, touchSize, touchSize);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, circleBuf);
		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, circleVs);
		gl.glPopMatrix();
	}
}
