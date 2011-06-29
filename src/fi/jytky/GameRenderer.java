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

	public GameRenderer(Game game) {
		this.game = game;
	}

	Game game;
	float touchSize = 0.15f;
	Vector touchMid = new Vector(1 - 1.3f * touchSize, 1.3f * touchSize);

	public void onSurfaceCreated(GL10 gl, EGLConfig eglc) {
		makeBufs();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		game.width = 1;
		game.height = (float) height / width;
	}

	public void onDrawFrame(GL10 gl) {
		game.update();

		setProj(gl);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		draw(gl, game.player);
		for(Bot b : game.bots) draw(gl, b);
		for(Bullet b : game.bullets) draw(gl, b);
		drawTouch(gl);
	}

	private void drawTouch(GL10 gl) {
		int n = 16;
		float[] vs = new float[2 * n];
		for (int i = 0; i < n; ++i) {
			float a = (float) (2 * Math.PI * i / n);
			vs[2 * i] = (float) (touchMid.x + touchSize * Math.cos(a));
			vs[2 * i + 1] = (float) (touchMid.y + touchSize * Math.sin(a));
		}
//		Log.i("asd", vs[0]+" "+vs[1]);
		gl.glColor4f(1, 0, 0, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, makeBuf(vs));
		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, n);
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
		Vector pos = d.getPos();
		gl.glTranslatef(pos.x, pos.y, 0);
		float s = d.getSize();
		gl.glScalef(s, s, s);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, triBuf);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glPopMatrix();
	}
	FloatBuffer triBuf;

	private void makeBufs() {
		float[] vs = new float[]{-1, -1, 1, -1, 0, 1};		
		triBuf = makeBuf(vs);
	}
}
