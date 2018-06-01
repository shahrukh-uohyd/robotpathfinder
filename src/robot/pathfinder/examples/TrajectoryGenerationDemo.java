package robot.pathfinder.examples;

import javax.swing.JFrame;

import robot.pathfinder.TankDriveTrajectory;
import robot.pathfinder.Waypoint;
import robot.pathfinder.tools.Grapher;

/**
 * An example program that generates and graphs a path and trajectory.
 * This was the program that generated the graphs in the project README.
 * @author Tyler Tian
 *
 */
public class TrajectoryGenerationDemo {
	
	public static void main(String[] args) {
		//Create array of waypoints
		Waypoint[] waypoints = new Waypoint[] {
				//X pos, Y pos, direction (radians)
				//Direction follows the unit circle, so 0 is the positive X-axis direction
				//Try changing these and watch what happens!
				new Waypoint(0, 0, Math.PI / 2),
				new Waypoint(-10, 50, 0),
		};
		//Used to determine how long the generation took
		long time = System.currentTimeMillis();
		//Generates a trajectory
		//Parameters: Waypoints, max vel, max accel, base width, alpha (smoothness of turns), number of segments
		//Last parameter suppresses exceptions (allows impossible paths)
		//Try changing these and see what happens!
		TankDriveTrajectory trajectory = new TankDriveTrajectory(waypoints, 5, 3.5, 2, 20, 5000, true);
		System.out.println("Trajectory generation took " + (System.currentTimeMillis() - time) + " milliseconds.");
		
		//Now we have the trajectory, let's graph it!
		//Grapher.graphPath and Grapher.graphTrajectory returns a JFrame with the graph inside
		//The dt of 0.005 takes 1.0 / 0.005 = 200 samples
		JFrame pathGraph = Grapher.graphPath(trajectory.getPath(), 0.005);
		//Take samples at 1/100s intervals
		JFrame trajectoryGraph = Grapher.graphTrajectory(trajectory, 0.01);
		//Set the default close operations so when we close one frame, the program exits
		pathGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		trajectoryGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Display the graphs
		pathGraph.setVisible(true);
		trajectoryGraph.setVisible(true);
	}
}