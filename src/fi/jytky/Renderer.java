package fi.jytky;

import android.opengl.GLES11;
import android.opengl.GLU;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public class Renderer {

	ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	GL10 gl;
	float width, height;

	void draw(Drawable d) {
		drawables.add(d);
	}

	void finish() {
		Log.i("ASD", "objects: "+drawables.size());
		setProj();
		GLES11.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		makeBuffers();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		GLES11.glDrawArrays(GL10.GL_TRIANGLES, 0, 3 * drawables.size());
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

	private void setProj() {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, width, 0, height);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	void init(GL10 gl) {
		this.gl = gl;
		drawables.clear();
	}

	private void makeBuffers() {
		int cnt = drawables.size();
		vertexBuffer = floatBuffer(vertexBuffer, 2 * 3 * cnt);
		colorBuffer = floatBuffer(colorBuffer, 4 * 3 * cnt);
		for (Drawable d : drawables) {
			int color = d.getColor();
			Vec2 pos = d.getPos();
			float size = d.getSize();

			int b = color & 0xff, g = (color >>> 8) & 0xff, r = (color >>> 16) & 0xff;
			for (int i = 0; i < 3; ++i) {
				colorBuffer.put(r / 255.f);
				colorBuffer.put(g / 255.f);
				colorBuffer.put(b / 255.f);
				colorBuffer.put(1.f);
			}

			addVertex(pos.add(-size, -size));
			addVertex(pos.add(size, -size));
			addVertex(pos.add(0, size));
		}
		vertexBuffer.position(0);
		colorBuffer.position(0);
	}

	void addVertex(Vec2 v) {
		vertexBuffer.put(v.x);
		vertexBuffer.put(v.y);
	}

	static FloatBuffer floatBuffer(FloatBuffer old, int size) {
		FloatBuffer buf = old!=null && old.capacity()>=size ? old : floatBuffer(size*3/2);
		buf.limit(size);
		buf.position(0);
		return buf;
	}
	static FloatBuffer floatBuffer(int size) {
		return ByteBuffer.allocateDirect(4 * size).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	FloatBuffer vertexBuffer;
	FloatBuffer colorBuffer;
}
