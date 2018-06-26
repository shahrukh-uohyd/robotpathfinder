package robot.pathfinder.core.path;

import java.util.ArrayList;

import robot.pathfinder.core.Waypoint;
import robot.pathfinder.core.spline.Spline;
import robot.pathfinder.math.MathUtils;
import robot.pathfinder.math.Vec2D;
import robot.pathfinder.util.Pair;

abstract public class Path {
	
	Waypoint[] waypoints;
	Spline[] segments;

	double totalLen = Double.NaN;
	double totalLenLeft = Double.NaN, totalLenRight = Double.NaN;
	ArrayList<Pair<Double, Double>> s2tLookupTable = null;
	
	//If set to true, the locations of left and right wheels are reversed
	boolean drivingBackwards = false;
	double baseRadius;
	
	/**
	 * Sets the base plate radius (distance from the center of the robot to the wheels) of the robot following
	 * this path. This value is used to compute the result from {@link BezierPath#wheelsAt(double) wheelsAt()}.
	 * @param b The new base radius
	 */
	public void setBaseRadius(double b) {
		baseRadius = b;
	}
	/**
	 * Retrieves the base radius (distance from the center of the robot to the wheels) of the robot following
	 * this path. This value is used to compute the result from {@link BezierPath#wheelsAt(double) wheelsAt()}.
	 * @return The base radius
	 */
	public double getBaseRaidus() {
		return baseRadius;
	}
	
	/**
	 * Returns the position at a specified time in the path.
	 * @param t A positive real number in the range 0 to 1
	 * @return The position of the path at the specified time
	 */
	public Vec2D at(double t) {
		if(t >= 1) {
			return segments[segments.length - 1].at(1);
		}
		
		t *= segments.length;
		return segments[(int) Math.floor(t)].at(t % 1.0);
	}
	/**
	 * Returns the derivative at a specified time in the path.
	 * @param t A positive real number in the range 0 to 1
	 * @return The derivative of the path at the specified time
	 */
	public Vec2D derivAt(double t) {
		if(t >= 1) {
			return segments[segments.length - 1].derivAt(1);
		}
		
		t *= segments.length;
		return segments[(int) Math.floor(t)].derivAt(t % 1.0);
	}
	/**
	 * Returns the second derivative at a specified time in the path.
	 * @param t A positive real number in the range 0 to 1
	 * @return The second derivative of the path at the specified time
	 */
	public Vec2D secondDerivAt(double t) {
		if(t >= 1) {
			return segments[segments.length - 1].secondDerivAt(1);
		}
		
		t *= segments.length;
		return segments[(int) Math.floor(t)].secondDerivAt(t % 1.0);
	}
	
	/**
	 * Returns the position of the wheels at a specified time in the path.
	 * @param t A positive real number in the range 0 to 1
	 * @return The position of the wheels at the specified time; element 0 is the left wheel, element 1 is the right wheel.
	 */
	public Vec2D[] wheelsAt(double t) {
		Vec2D position = at(t);
		Vec2D derivative = derivAt(t);
		
		double heading = Math.atan2(derivative.getY(), derivative.getX());
		double sinHeading = Math.sin(heading);
		double cosHeading = Math.cos(heading);
		Vec2D left = new Vec2D(position.getX() - (!drivingBackwards ? baseRadius * sinHeading : -baseRadius * sinHeading),
				position.getY() + (!drivingBackwards ? baseRadius * cosHeading : -baseRadius * cosHeading));
		Vec2D right = new Vec2D(position.getX() + (!drivingBackwards ? baseRadius * sinHeading : -baseRadius * sinHeading),
				position.getY() - (!drivingBackwards ? baseRadius * cosHeading : -baseRadius * cosHeading));
		
		return new Vec2D[] { left, right };
	}
	
	public void prepareS2T(int points) {
		computePathLength(points);
	}
	public double computePathLength(int points) {
		double dt = 1.0 / (points - 1);
		
		Vec2D last = at(0);
		totalLen = 0;
		s2tLookupTable = new ArrayList<>(points);
		s2tLookupTable.add(new Pair<>(0.0, 0.0));
		for(int i = 1; i < points; i ++) {
			Vec2D current = at(i * dt);
			totalLen += last.distTo(current);
			s2tLookupTable.add(new Pair<>(totalLen, i * dt));
			last = current;
		}
		return totalLen;
	}
	public double getPathLength() {
		return totalLen;
	}
	public double s2T(double s) {
		double dist = s * totalLen;
		
		int start = 0;
		int end = s2tLookupTable.size() - 1;
		int mid;
		
		if(dist > s2tLookupTable.get(s2tLookupTable.size() - 1).getElem1()) {
			return 1;
		}
		while(true) {
			mid = (start + end) / 2;
			double midDist = s2tLookupTable.get(mid).getElem1();
			
			if(midDist == dist) {
				if(s == 2.0e-4) {
					System.out.println(mid);
				}
				return s2tLookupTable.get(mid).getElem2();
			}
			if(mid == s2tLookupTable.size() - 1) {
				return 1;
			}
			
			double nextDist = s2tLookupTable.get(mid + 1).getElem1();
			if(midDist <= dist && dist <= nextDist) {
				
				double f = (dist - midDist) / (nextDist - midDist);
				return MathUtils.lerp(s2tLookupTable.get(mid).getElem2(), 
						s2tLookupTable.get(mid + 1).getElem2(), f);
			}
			//Check if we are at the first element after checking if we are between two elements
			//Otherwise, in the case of a value between the first and second, the lerp will not happen
			if(mid == 0) {
				return 0;
			}
			
			if(midDist < dist) {
				start = mid;
			}
			else if(midDist > dist) {
				end = mid;
			}
		}
	}
	
	/**
	 * Retrieves the waypoints used to construct this path.
	 * @return The waypoints that were used to construct this path
	 */
	public Waypoint[] getWaypoints() {
		return waypoints;
	}
	
	/**
	 * Sets whether the robot that drives this path is driving backwards or not.
	 * If this is set to true, the locations of the left and right wheels will be reversed.
	 * @param drivingBackwards Whether this path should be driven backwards
	 */
	public void setDrivingBackwards(boolean drivingBackwards) {
		this.drivingBackwards = drivingBackwards;
	}
}
