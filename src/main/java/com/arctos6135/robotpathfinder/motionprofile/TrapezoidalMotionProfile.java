package com.arctos6135.robotpathfinder.motionprofile;

import com.arctos6135.robotpathfinder.core.RobotSpecs;

public class TrapezoidalMotionProfile implements MotionProfile {

    protected double distance;
    protected double maxAcl, maxVel;
    protected double cruiseVel;

    protected double tAccel, tCruise, tTotal;
    protected double accelDist, cruiseDist;

    protected boolean reverse = false;

    public TrapezoidalMotionProfile(RobotSpecs specs, double dist) {
        this(dist, specs.getMaxVelocity(), specs.getMaxAcceleration());
    }

    public TrapezoidalMotionProfile(double dist, double maxVelocity, double maxAcceleration) {
        if (dist < 0) {
            reverse = true;
            dist = -dist;
        }
        distance = dist;
        maxAcl = maxAcceleration;
        maxVel = maxVelocity;

        // Calculate the max reachable velocity
        // Formula can be derived from 4th kinematic equation
        double vc = Math.sqrt(2 * maxAcl * dist / 2);
        cruiseVel = Math.min(vc, maxVel);

        tAccel = cruiseVel / maxAcl;

        accelDist = Math.pow(tAccel, 2) * maxAcl * 1 / 2;
        // Acceleration and deceleration should cover the same distance
        cruiseDist = dist - accelDist * 2;
        tCruise = cruiseDist / cruiseVel;

        tTotal = tAccel + tCruise + tAccel;
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
        if (time <= tAccel) {
            result = Math.pow(time, 2) * maxAcl * 1 / 2;
        }
        // When cruising
        else if (time <= tAccel + tCruise) {
            // The distance is the distance covered during acceleration and rest of the time
            // multiplied by the cruise velocity
            result = accelDist + (time - tAccel) * cruiseVel;
        }
        // When decelerating
        else if (time <= totalTime()) {
            // The distance is the distance covered during acceleration and cruising, plus
            // the distance covered during deceleration,
            // which can be solved using the third kinematic formula
            result = accelDist + cruiseDist
                    + ((time - tAccel - tCruise) * cruiseVel - Math.pow(time - tAccel - tCruise, 2) * maxAcl * 1 / 2);
        } else {
            throw new IllegalArgumentException("Time out of range: " + time);
        }
        return reverse ? -result : result;
    }

    @Override
    public double velocity(double time) {
        double result = 0;
        // When accelerating
        if (time <= tAccel) {
            // The velocity is just the time multiplied by the acceleration
            result = time * maxAcl;
        }
        // When cruising
        else if (time <= tAccel + tCruise) {
            // The velocity is the cruise velocity
            result = cruiseVel;
        }
        // When decelerating
        else if (time <= totalTime()) {
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
        if (time <= tAccel) {
            result = maxAcl;
        }
        // When cruising
        else if (time <= tAccel + tCruise) {
            result = 0;
        }
        // When decelerating
        else if (time <= totalTime()) {
            result = -maxAcl;
        } else {
            throw new IllegalArgumentException("Time out of range: " + time);
        }
        return reverse ? -result : result;
    }
}
