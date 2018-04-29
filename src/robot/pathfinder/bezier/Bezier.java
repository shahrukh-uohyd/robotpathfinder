package robot.pathfinder.bezier;

import robot.pathfinder.Vec2D;

public class Bezier {
	Vec2D[] controlPoints;
	
	public Bezier(Vec2D a, Vec2D b, Vec2D c, Vec2D d) {
		controlPoints = new Vec2D[4];
		controlPoints[0] = a;
		controlPoints[1] = b;
		controlPoints[2] = c;
		controlPoints[3] = d;
	}
	
	public static Bezier getFromHermite(Vec2D at0, Vec2D at1, Vec2D derivAt0, Vec2D derivAt1) {
		Vec2D p1 = at0.addTo(derivAt0.multiplyBy(1.0 / 3.0));
		Vec2D p2 = at1.addTo(derivAt1.multiplyBy(-1.0 / 3.0));
		return new Bezier(at0, p1, p2, at1);
	}
	
	public Vec2D at(double t) {
		double u = 1 - t;
		double uu = u * u;
		double uuu = u * u * u;
		double tt = t * t;
		double ttt = t * t * t;
		return Vec2D.addVecs(controlPoints[0].multiplyBy(uuu), controlPoints[1].multiplyBy(3 * uu * t),
				controlPoints[2].multiplyBy(3 * u * tt), controlPoints[3].multiplyBy(ttt));
	}
}
