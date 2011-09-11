package fi.jytky;

/**
 * Vec2 is a simple immutable geometric 2D-vector.
 */
public class Vec2 {

	/** Coordinates of the vector. */
	final float x,y;

	/** Creates a vector. */
	public Vec2(float x, float y) {
		this.x=x;
		this.y=y;
	}

	/** Sums two vectors together. */
	public Vec2 add(Vec2 v) {
		return new Vec2(x+v.x, y+v.y);
	}
	/** Sums vector to another vector represented by numbers. */
	public Vec2 add(float a, float b) {
		return new Vec2(x+a, y+b);
	}

	/** Subtacts another vector from this vector. */
	public Vec2 sub(Vec2 v) {
		return new Vec2(x-v.x, y-v.y);
	}
	/** Subtacts another vector from this vector. */
	public Vec2 sub(float a, float b) {
		return new Vec2(x-a, y-b);
	}

	/** Multiplies this vector by a constant. */
	public Vec2 mult(float a) {
		return new Vec2(a*x, a*y);
	}
	/** Divides this vector by a constant. */
	public Vec2 div(float a) {
		return new Vec2(x/a, y/a);
	}
	/** Negates this vector; this is same as multiplying by -1. */
	public Vec2 neg() {
		return new Vec2(-x,-y);
	}

	/** Calculates the dot product between vectors. */
	public float dot(Vec2 v) {
		return x*v.x + y*v.y;
	}
	/** Calculates the z-component of the cross product between vectors.
	 * The cross product between 2 vectors at xy-axes is always parallel to
	 * z-axis so this is the only information needed about cross product.
	 */
	public float cross(Vec2 v) {
		return x*v.y - y*v.x;
	}
	/** Calculates the z-component of the cross product between vectors.
	 * The cross product between 2 vectors at xy-axes is always parallel to
	 * z-axis so this is the only information needed about cross product.
	 */
	public float cross(float vx, float vy) {
		return x*vy - y*vx;
	}

	/** Returns squared length of this vector. */
	public float length2() {
		return dot(this);
	}
	/** Returns the length of this vector. */
	public float length() {
		return (float)Math.sqrt(length2());
	}
	/** Returns this vector normalized.
	 * If this vector is a null vector, resulting vector may have NaN-values.
	 */
	public Vec2 normalize() {
		return div(length());
	}

	public float dist2(Vec2 v) {
		return sub(v).length2();
	}
	public float dist(Vec2 v) {
		return sub(v).length();
	}
	public float angle() {
		return (float) Math.atan2(y, x);
	}

	@Override
	public String toString() {
		return "("+x+","+y+")";
	}

	static Vec2 polar(float len, float ang) {
		return new Vec2(len * (float)Math.cos(ang), len * (float)Math.sin(ang));
	}

	public static Vec2 zero = new Vec2(0,0);
}
