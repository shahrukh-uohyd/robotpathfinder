package com.arctos6135.robotpathfinder.motionprofile;

import com.arctos6135.robotpathfinder.core.RobotSpecs;

public class TrapezoidalMotionProfile implements MotionProfile {

    protected double initVel;
    protected double distance;
    protected double maxAcl, maxVel;
    protected double cruiseVel;

    protected double tAccel, tCruise, tTotal;
    protected double accelDist, cruiseDist;

    protected boolean reverse = false;

    public TrapezoidalMotionProfile(RobotSpecs specs, double dist) {
        construct(specs, dist, 0);
    }

    public TrapezoidalMotionProfile(RobotSpecs specs, double dist, double initVel) {
        construct(specs, dist, initVel);
    }

    private void construct(RobotSpecs specs, double dist, double initVel) {
        if (dist < 0) {
            reverse = true;
            dist = -dist;
            initVel = -initVel;
        }
        distance = dist;
        maxAcl = specs.getMaxAcceleration();
        maxVel = specs.getMaxVelocity();
        this.initVel = initVel;

        if (Math.abs(initVel) > maxVel) {
            throw new IllegalArgumentException("Initial velocity too high!");
        }
        // Calculate the distance covered when accelerating and decelerating
        // Formula can be derived from the fourth kinematic formula
        double dAccel = dist / 2 - initVel * initVel / (4 * maxAcl);
        double dDecel;
        // If the acceleration distance is less than 0, the distance is not enough to
        // decelerate back to 0
        // Change the distance so that we can
        if (dAccel < 0) {
            dDecel = initVel * initVel / (2 * maxAcl);
            dist = dDecel;
        } else {
            dDecel = dist - dAccel;
        }
        // Calculate cruise velocity
        // Formula derived from the fourth kinematic formula
        double vc = Math.sqrt(2 * maxAcl * dDecel);
        cruiseVel = Math.min(vc, maxVel);

        // Calculate acceleration time
        tAccel = (cruiseVel - initVel) / maxAcl;
        // Re-calculate the acceleration distance
        // This is needed because the first result does not take into account the actual
        // max velocity
        // First kinematic formula
        accelDist = tAccel * tAccel * maxAcl * 0.5 + initVel * tAccel;
        // Calculate the deceleration time
        double tDecel = cruiseVel / maxAcl;
        // Re-calculate the deceleration distance
        double decelDist = tDecel * tDecel * maxAcl * 0.5;

        // Calculate the cruise distance
        cruiseDist = dist - accelDist - decelDist;
        // Calculate the cruise time
        tCruise = cruiseDist / cruiseVel;

        tTotal = tAccel + tCruise + tDecel;
    }

    @Override
    public double totalTime() {
        return tTotal;
    }

    @Override
    public boolean isReversed() {
        return reverse;
    }

    @Override
    public double position(double time) {
        double result = 0;
        // When accelerating
        if (time < tAccel) {
            result = time * time * maxAcl * 1 / 2 + initVel * time;
        }
        // When cruising
        else if (time < tAccel + tCruise) {
            // The distance is the distance covered during acceleration and rest of the time
            // multiplied by the cruise velocity
            result = accelDist + (time - tAccel) * cruiseVel;
        }
        // When decelerating
        else if (time <= tTotal) {
            // The distance is the distance covered during acceleration and cruising, plus
            // the distance covered during deceleration,
            // which can be solved using the third kinematic formula
            double t = time - tAccel - tCruise;
            result = accelDist + cruiseDist + t * cruiseVel - t * t * maxAcl * 0.5;
        } else {
            throw new IllegalArgumentException("Time out of range: " + time);
        }
        return reverse ? -result : result;
    }

    @Override
    public double velocity(double time) {
        double result = 0;
        // When accelerating
        if (time < tAccel) {
            // The velocity is just the time multiplied by the acceleration
            result = time * maxAcl + initVel;
        }
        // When cruising
        else if (time < tAccel + tCruise) {
            // The velocity is the cruise velocity
            result = cruiseVel;
        }
        // When decelerating
        else if (time <= tTotal) {
            // The velocity is the cruise velocity minus the acceleration times the time
            // decelerating
            result = cruiseVel - (time - tAccel - tCruise) * maxAcl;
        } else {
            throw new IllegalArgumentException("Time out of range: " + time);
        }
        return reverse ? -result : result;
    }

    @Override
    public double acceleration(double time) {
        double result = 0;
        // When accelerating
        if (time < tAccel) {
            result = maxAcl;
        }
        // When cruising
        else if (time < tAccel + tCruise) {
            result = 0;
        }
        // When decelerating
        else if (time <= tTotal) {
            result = -maxAcl;
        } else {
            throw new IllegalArgumentException("Time out of range: " + time);
        }
        return reverse ? -result : result;
    }
}
