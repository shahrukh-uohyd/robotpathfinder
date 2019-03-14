package com.arctos6135.robotpathfinder.core.trajectory;

import com.arctos6135.robotpathfinder.core.GlobalLibraryLoader;
import com.arctos6135.robotpathfinder.core.TrajectoryParams;
import com.arctos6135.robotpathfinder.core.Waypoint;
import com.arctos6135.robotpathfinder.core.RobotSpecs;
import com.arctos6135.robotpathfinder.core.lifecycle.GlobalLifeCycleManager;

/**
 * A class that represents a trajectory (motion profile) for a tank drive (aka
 * skid-steer or differential drive) robot.
 * <p>
 * A trajectory not only defines the path the robot will go through, it also
 * provides information about the velocity, acceleration and direction at every
 * point in time. Using this information, a robot can implement a feedback loop
 * to follow this trajectory.
 * </p>
 * <p>
 * Trajectories generated by this class assume a hypothetical robot that has
 * limited speed and acceleration, but unlimited jerk. Unlike
 * {@link BasicTrajectory}, this class breaks the overall movement down to the
 * individual movements of the left and right side wheels, and takes into
 * account the slowing down when turning.
 * </p>
 * <p>
 * Each Trajectory has a Java part (the object itself) and a part that resides
 * in native code (stored as a pointer casted into a {@code long}). Because
 * these objects contain handles to native resources that cannot be
 * automatically released by the JVM, the {@link #free()} or {@link #close()}
 * method must be called to free the native resource when the object is no
 * longer needed.
 * </p>
 * <p>
 * <em> Note: Almost all RobotPathfinder JNI classes have some kind of reference
 * counting. However, this reference count is only increased when an object is
 * created or copied by a method, and not when the reference is copied through
 * assignment. <br>
 * For example:
 * 
 * <pre>
 * Path p0 = someTrajectory.getPath();
 * Path p1 = someTrajectory.getPath();
 * p0.free();
 * p1.at(0); // This is valid, because the native resource was never freed due to
 *           // reference counting
 * </pre>
 * 
 * But:
 * 
 * <pre>
 * Path p0 = someTrajectory.getPath();
 * Path p1 = p0;
 * p0.free();
 * p1.at(0); // This will throw an IllegalStateException, since the native resource has
 *           // already been freed
 * </pre>
 * 
 * </em>
 * </p>
 * 
 * @see BasicTrajectory
 * @author Tyler Tian
 * @since 3.0.0
 */
public class TankDriveTrajectory extends Trajectory {

    static {
        GlobalLibraryLoader.load();
        GlobalLifeCycleManager.initialize();
    }

    private native void _construct(double maxV, double maxA, double baseWidth, boolean isTank, Waypoint[] waypoints,
            double alpha, int sampleCount, int type);

    /**
     * Creates a new {@link TankDriveTrajectory} with the specified robot
     * specifications and parameters.
     * 
     * @param specs  A {@link RobotSpecs} object providing robot information such as
     *               the maximum velocity.
     * @param params A {@link TrajectoryParams} object providing path/trajectory
     *               information such as the waypoints.
     * @throws TrajectoryGenerationException If the constraints set in the
     *                                       parameters cannot be met
     */
    public TankDriveTrajectory(RobotSpecs specs, TrajectoryParams params) {
        if (Double.isNaN(specs.getMaxVelocity())) {
            throw new IllegalArgumentException("Max velocity cannot be NaN");
        }
        if (Double.isNaN(specs.getMaxAcceleration())) {
            throw new IllegalArgumentException("Max acceleration cannot be NaN");
        }
        if (params.waypoints == null) {
            throw new IllegalArgumentException("Waypoints not set");
        }
        if (Double.isNaN(params.alpha)) {
            throw new IllegalArgumentException("Alpha cannot be NaN");
        }
        if (params.sampleCount < 1) {
            throw new IllegalArgumentException("Segment count must be greater than zero");
        }

        this.specs = specs;
        this.params = params;

        _construct(specs.getMaxVelocity(), specs.getMaxAcceleration(), specs.getBaseWidth(), true, params.waypoints,
                params.alpha, params.sampleCount, params.pathType.getJNIID());
        GlobalLifeCycleManager.register(this);
    }

    /**
     * Creates a new {@link TankDriveTrajectory} directly from a native pointer.
     * <p>
     * <b><em>This constructor is intended for internal use only. Use at your own
     * risk.</em></b>
     * </p>
     * 
     * @param specs  The specs of this trajectory
     * @param params The parameters of this trajectory
     * @param ptr    A pointer to the native resource
     */
    public TankDriveTrajectory(RobotSpecs specs, TrajectoryParams params, long ptr) {
        this.specs = specs;
        this.params = params;
        _nativePtr = ptr;
        GlobalLifeCycleManager.register(this);
    }

    @Override
    protected native void _destroy();

    @Override
    protected native int _getMomentCount();

    @Override
    protected native void _getMoments();

    protected TankDriveMoment[] momentsCache;

    /**
     * {@inheritDoc}
     */
    @Override
    public TankDriveMoment[] getMoments() {
        if (momentsCache == null) {
            momentsCache = new TankDriveMoment[_getMomentCount()];
            _getMoments();
        }
        return momentsCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearMomentsCache() {
        momentsCache = null;
    }

    @Override
    protected native TankDriveMoment _get(double t);

    /**
     * {@inheritDoc}
     */
    @Override
    public TankDriveMoment get(double t) {
        return (TankDriveMoment) super.get(t);
    }

    @Override
    protected native long _getPath();

    /**
     * {@inheritDoc}
     */
    @Override
    public native double totalTime();

    private native long _mirrorLeftRight();

    /**
     * {@inheritDoc}
     */
    @Override
    public TankDriveTrajectory mirrorLeftRight() {
        return new TankDriveTrajectory(specs, params, _mirrorLeftRight());
    }

    private native long _mirrorFrontBack();

    /**
     * {@inheritDoc}
     */
    @Override
    public TankDriveTrajectory mirrorFrontBack() {
        return new TankDriveTrajectory(specs, params, _mirrorFrontBack());
    }

    private native long _retrace();

    /**
     * {@inheritDoc}
     */
    @Override
    public TankDriveTrajectory retrace() {
        return new TankDriveTrajectory(specs, params, _retrace());
    }
}
