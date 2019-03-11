package com.arctos6135.robotpathfinder.core.trajectory;

import com.arctos6135.robotpathfinder.core.RobotSpecs;
import com.arctos6135.robotpathfinder.core.JNITrajectoryParams;
import com.arctos6135.robotpathfinder.core.path.JNIPath;

/**
 * A class that represents a trajectory (motion profile).
 * <p>
 * This is the interface implemented by all trajectory classes. A trajectory not only defines the path the 
 * robot will go through, it also provides information about the velocity, acceleration and direction at
 * every point in time. Using this information, a robot can implement a feedback loop to follow this trajectory.
 * </p> 
 * <h2>Technical Details</h2>
 * <p>
 * Trajectories are generated using numerical integration. This means that it is impossible to have a completely
 * accurate trajectory. However, with enough segments, the error is easily negligible. Trajectories are generated
 * with an algorithm based on the one shown by Team 254 (The Cheesy Poofs) in their video on motion profiling.
 * </p>
 * @author Tyler Tian
 *
 */
public interface JNITrajectory extends AutoCloseable {

	public void free();
	/**
	 * Retrieves all the moments generated by this trajectory.
	 * @return An array of all generated moments
	 */
	public Moment[] getMoments();
	public void clearMomentsCache();
	/**
	 * Retrieves the moment object associated with a given time. 
	 * <p>
	 * This method will first look through all generated methods to try and find one that matches the given
	 * time exactly. If there is no such moment, it will try to approximate the data using the nearest 2 matches.
	 * </p>
	 * @param t The time
	 * @return The moment object associated with the given time
	 */
	public Moment get(double t);
	/**
	 * Retrieves the path of this trajectory.
	 * @return The path of the trajectory
	 */
	public JNIPath getPath();
	public void clearPathCache();
	/**
	 * Retrieves the total amount of time it would take for a robot to finish this trajectory.
	 * @return The total time for the robot to finish the trajectory
	 */
	public double totalTime();
	
	/**
	 * Retrieves the {@link com.arctos6135.robotpathfinder.core.RobotSpecs RobotSpecs} object used to generate the trajectory.
	 * @return The {@link com.arctos6135.robotpathfinder.core.RobotSpecs RobotSpecs} object used to generate the trajectory
	 */
	public RobotSpecs getRobotSpecs();
	/**
	 * Retrieves the {@link com.arctos6135.robotpathfinder.core.JNITrajectoryParams JNITrajectoryParams} object used to generate the trajectory.
	 * @return The {@link com.arctos6135.robotpathfinder.core.JNITrajectoryParams JNITrajectoryParams} object used to generate the trajectory
	 */
	public JNITrajectoryParams getGenerationParams();
	
	/**
	 * Returns a modified trajectory in which every left turn becomes a right turn. Note that is operation is not 
	 * the same as reflecting across the Y axis, unless the first waypoint has a heading of pi/2.
	 * @return The mirrored trajectory
	 */
	public JNITrajectory mirrorLeftRight();
	/**
	 * Returns a modified trajectory, in which every forward movement becomes a backward movement. Note that this 
	 * operation is not the same as reflecting across the X axis, unless the first waypoint has a heading of
	 * pi/2.
	 * @return The mirrored trajectory
	 */
	public JNITrajectory mirrorFrontBack();
	/**
	 * Returns a trajectory that, when driven, will retrace the steps of this trajectory exactly.
	 * @return The retraced trajectory
	 */
	public JNITrajectory retrace();
}
