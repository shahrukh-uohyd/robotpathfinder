package com.arctos6135.robotpathfinder.motionprofile;

public interface MotionProfile {
    public double totalTime();

    public double distance(double t);

    public double velocity(double t);

    public double acceleration(double t);
}