package fi.jytky;

/**
 * Vector is a simple immutable geometric 2D-vector.
 */
public class Vector {

	/** Coordinates of the vector. */
	final float x,y;

	/** Creates a vector. */
	public Vector(float x, float y) {
		this.x=x;
		this.y=y;
	}

	/** Sums two vectors together. */
	public Vector add(Vector v) {
		return new Vector(x+v.x, y+v.y);
	}
	/** Sums vector to another vector represented by numbers. */
	public Vector add(float a, float b) {
		return new Vector(x+a, y+b);
	}

	/** Subtacts another vector from this vector. */
	public Vector sub(Vector v) {
		return new Vector(x-v.x, y-v.y);
	}
	/** Subtacts another vector from this vector. */
	public Vector sub(float a, float b) {
		return new Vector(x-a, y-b);
	}

	/** Multiplies this vector by a constant. */
	public Vector mult(float a) {
		return new Vector(a*x, a*y);
	}
	/** Divides this vector by a constant. */
	public Vector div(float a) {
		return new Vector(x/a, y/a);
	}
	/** Negates this vector; this is same as multiplying by -1. */
	public Vector neg() {
		return new Vector(-x,-y);
	}

	/** Calculates the dot product between vectors. */
	public float dot(Vector v) {
		return x*v.x + y*v.y;
	}
	/** Calculates the z-component of the cross product between vectors.
	 * The cross product between 2 vectors at xy-axes is always parallel to
	 * z-axis so this is the only information needed about cross product.
	 */
	public float cross(Vector v) {
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
	public Vector normalize() {
		return div(length());
	}

	public float dist2(Vector v) {
		return sub(v).length2();
	}
	public float dist(Vector v) {
		return sub(v).length();
	}

	public String toString() {
		return "("+x+","+y+")";
	}
}
