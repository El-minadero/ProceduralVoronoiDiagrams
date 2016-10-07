package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.Random;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.*;

public class SegmentGenerator extends AbstractControlGenerator {

	public SegmentGenerator(long seed,double spacing){
		super(seed);
		setSpacing(spacing);
	}
	
	@Override
	protected void changeSpacing(double[] pointVec) {
	}

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
	
	protected double getMagnitude(double[] point){
		Random rand = getPointBasedRandom(point);
		double magnitude = rand.nextDouble()*getSpacing()/2 + getSpacing()/2;
		return magnitude;
	}
	
	protected double[] getUnitVector(double[] point){
		Random rand = getPointBasedRandom(point);
		double[] unitVector = {rand.nextDouble() + 2,
							   rand.nextDouble() + 2,
							   rand.nextDouble() + 2};
		double mag = unitVector[0]*unitVector[0] + 
					 unitVector[1]*unitVector[1] + 
					 unitVector[2]*unitVector[2];
		mag = Math.sqrt(mag);
		unitVector[0]/=mag;
		unitVector[1]/=mag;
		unitVector[2]/=mag;
		return unitVector;
	}

	protected Random getPointBasedRandom(double[] point){
		return new Random(getPointBasedSeed(point));
	}
	

}
