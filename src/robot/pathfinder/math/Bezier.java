package robot.pathfinder.math;

/**
 * A class representing a cubic Bezier curve.
 * @author Tyler
 *
 */
public class Bezier {
	Vec2D[] controlPoints;
	
	/**
	 * Constructs a new cubic Bezier with the specified control points.
	 * @param a - The first control point
	 * @param b - The second control point
	 * @param c - The third control point
	 * @param d - The last control point
	 */
	public Bezier(Vec2D a, Vec2D b, Vec2D c, Vec2D d) {
		controlPoints = new Vec2D[4];
		controlPoints[0] = a;
		controlPoints[1] = b;
		controlPoints[2] = c;
		controlPoints[3] = d;
	}
	
	/**
	 * Returns a new Bezier with the specified start and end points, and derivatives at those points.
	 * @param at0 - The starting control point
	 * @param at1 - The ending control point
	 * @param derivAt0 - The derivative at the starting control point
	 * @param derivAt1 - The derivative at the ending control point
	 * @return
	 */
	public static Bezier getFromHermite(Vec2D at0, Vec2D at1, Vec2D derivAt0, Vec2D derivAt1) {
		Vec2D p1 = at0.add(derivAt0.multiply(1.0 / 3.0));
		Vec2D p2 = at1.add(derivAt1.multiply(-1.0 / 3.0));
		return new Bezier(at0, p1, p2, at1);
	}
	
	/**
	 * Returns the value of this Bezier curve at the specified time.
	 * @param t - A positive real number in the range 0 to 1
	 * @return The value of the curve at the specified time
	 */
	public Vec2D at(double t) {
		double u = 1 - t;
		double uu = u * u;
		double uuu = u * u * u;
		double tt = t * t;
		double ttt = t * t * t;
		return Vec2D.addVecs(controlPoints[0].multiply(uuu), controlPoints[1].multiply(3 * uu * t),
				controlPoints[2].multiply(3 * u * tt), controlPoints[3].multiply(ttt));
	}
	/**
	 * Returns the derivative of this Bezier curve at the specified time.
	 * @param t - A positive real number in the range 0 to 1
	 * @return The derivative of the curve at the specified time
	 */
	public Vec2D derivAt(double t) {
		double u = 1 - t;
		double uu = u * u;
		double tt = t * t;
		return Vec2D.addVecs(controlPoints[1].add(controlPoints[0].multiply(-1)).multiply(3 * uu),
				controlPoints[2].add(controlPoints[1].multiply(-1)).multiply(6 * u * t),
				controlPoints[3].add(controlPoints[2].multiply(-1)).multiply(3 * tt));
	}
	/**
	 * Returns the second derivative of this Bezier curve at the specified time.
	 * @param t - A positive real number in the range 0 to 1
	 * @return The second derivative of the curve at the specified time
	 */
	public Vec2D secondDerivAt(double t) {
		double u = 1 - t;
		return Vec2D.addVecs(Vec2D.addVecs(controlPoints[2], controlPoints[1].multiply(-2), controlPoints[0]).multiply(6 * u),
				Vec2D.addVecs(controlPoints[3], controlPoints[2].multiply(-2), controlPoints[1]).multiply(6 * t));
	}
}
