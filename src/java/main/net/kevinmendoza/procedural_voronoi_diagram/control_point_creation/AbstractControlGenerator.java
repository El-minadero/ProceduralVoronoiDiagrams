package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlDefaultCombined;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

abstract class AbstractControlGenerator implements ControlPointCreation {

	private SeedPointGenerator pointGeneration;
	private List<ControlPointInterface> centers;
	private double[] creationCenter;
	private double spacing;

	public AbstractControlGenerator(long seed) {
		pointGeneration = new SeedPointGenerator(seed);
		spacing = 30;
		centers = new ArrayList<ControlPointInterface>();
	}
	
	protected long getPointBasedSeed(double[] center){
		long i = 5;
		i = (long) (i*center[0]*5 + 13);
		i = (long) (i*center[1]*5 + 13);
		i = (long) (i*center[2]*5 + 13);
		return i;
 	}
	
	protected int getWeightMult(double[] center){
		return 1;
	}
	
	protected int getPowerMult(double[] center){
		return 1;
	}
	
	protected void setSpacing(double spacing){
		this.spacing=spacing;
	}
	
	protected double getSpacing(){
		return spacing;
	}
	
	protected List<double[]> getSpacedPoints(double pointSpacing,double[] center){
		return pointGeneration.getSpacedPoints(pointSpacing, center);
	}
	
	protected List<double[]> offsetSpacedPoints(List<double[]> potentialCoords, int pointSpacing){
		return pointGeneration.offsetPoints(potentialCoords, (int) pointSpacing);
	}
	
	protected List<double[]> getCenters(double pointSpacing, double[] center){
		List<double[]> potentialCoords = getSpacedPoints(pointSpacing, center);
		potentialCoords = offsetSpacedPoints(potentialCoords, (int) pointSpacing);
		return potentialCoords;
	}
	
	protected abstract void changeSpacing(double[] pointVec);

	protected abstract ControlPointInterface buildCenterObject(double[] center);
	
	protected ControlPointInterface buildCombinedObject(HashSet<ControlPointInterface> controlPoints){
		return new ControlDefaultCombined.CombinedControlPointBuilder()
				.addCenters(controlPoints)
				.Build();
	}

	public List<ControlPointInterface> getLocalControlPoints(double[] pointVec){
		changeSpacing(pointVec);
		centers.clear();
		createCenters(pointVec);
		return centers;
	}

	private void createCenters(double[] pnt){
		List<double[]> points = getCenters(spacing,pnt);
		for(double[] point: points){
			centers.add(buildCenterObject(point));
		}
	}

	public ControlPointInterface makeCombinedPoint(HashSet<ControlPointInterface> controlPoints){
		return buildCombinedObject(controlPoints);
	}
	
}
