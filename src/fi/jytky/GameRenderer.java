package fi.jytky;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
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
		drawPlayer(gl);
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

	void drawPlayer(GL10 gl) {
		gl.glPushMatrix();
		Player player = game.player;
		gl.glTranslatef(player.pos.x, player.pos.y, 0);
		float s = .1f;
		gl.glScalef(s, s, s);
		float[] vs = new float[]{-1, -1, 1, -1, 0, 1};
		gl.glColor4f(0, 1, 0, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, makeBuf(vs));
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glPopMatrix();
	}

	private static FloatBuffer makeBuf(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(4 * arr.length);
		FloatBuffer fb = bb.order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
}
