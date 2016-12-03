package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.Random;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.*;

public class SegmentGenerator extends AbstractControlGenerator {

	/**
	 *  
	 * This subclass of AbstractControlGenerator handles the creation of voronoi
	 * centers by placing line segments. To produce custom lines, implement a 
	 * custom child of this class. 
	 * 
	 *@param long seed
	 *			a long seed value to use in the generator
	 *
	 *@param double spacing
	 *			average spacing between segment centers
	 */
	public SegmentGenerator(long seed,double spacing){
		super(seed);
		setSpacing(spacing);
	}
	
	/**
	 * allows for a dynamic change in the average spacing of a voronoi diagram.
	 * 
	 * @param double[] pointVec
	 * 		an x,y,z 3D double array  which the superclass uses to query new spacing.
	 * 		Leave this method empty if you wish to have smooth spacing.
	 * 
	 */
	@Override
	protected void changeSpacing(double[] pointVec) {
	}
	
	/**
	 * creates an object implementing the ControlPointInterface. By default this method creates
	 * a line segment.
	 * 
	 * @param double[] center
	 * 		an x,y,z 3D double array which the superclass uses to initialize a control point at 
	 * 		the given spatial coordinates.
	 * 
	 */
	@Override
	protected ControlPointInterface buildCenterObject(double[] center) {
		return new ControlDefaultSegment
				.ControlSegmentBuilder()
				.withCenter(center)
				.withMagnitude(getMagnitude(center))
				.withUnitVector(getUnitVector(center))
				.withWeightMult(getWeightMult(center))
				.withWeightPower(getPowerMult(center))
				.Build();
	}
	
	/**
	 * returns the line length of a segment to be created at a point.
	 * 
	 * @param double[] point
	 * 		an x,y,z 3D double vector which the superclass uses to initialize a control point at 
	 * 		the given spatial coordinates.
	 * 
	 */
	protected double getMagnitude(double[] point){
		Random rand = getPointBasedRandom(point);
		double magnitude = (rand.nextDouble() + 2)*getSpacing()/2;
		return magnitude;
	}
	
	/**
	 * returns a unit vector representing a segment to be created at a point.
	 * 
	 * @param double[] point
	 * 		an x,y,z 3D double vector which the superclass uses to initialize a control point at 
	 * 		the given spatial coordinates.
	 * 
	 */
	protected double[] getUnitVector(double[] point){
		Random rand = getPointBasedRandom(point);
		double[] unitVector = {rand.nextDouble() -0.25,
							   rand.nextDouble() -0.25};
		double mag = unitVector[0]*unitVector[0] + 
					 unitVector[1]*unitVector[1];
		mag = Math.sqrt(mag);
		unitVector[0]/=mag;
		unitVector[1]/=mag;
		return unitVector;
	}

	/**
	 * returns a Random object based on the coordinates of a given point.
	 * 
	 * @param double[] point
	 * 		an x,y,z 3D double vector which the superclass uses to initialize a control point at 
	 * 		the given spatial coordinates.
	 */
	protected Random getPointBasedRandom(double[] point){
		return new Random(getPointBasedSeed(point));
	}
	

}
