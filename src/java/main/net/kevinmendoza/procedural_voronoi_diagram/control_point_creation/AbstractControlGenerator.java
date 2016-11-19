package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlDefaultCombined;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointUtility;

abstract class AbstractControlGenerator implements ControlPointCreation {

	private SeedPointGenerator pointGeneration;
	private List<ControlPointInterface> centers;
	private double SPACING = 30;
	private double VARIANCE = 2.0;

	public AbstractControlGenerator(long seed) {
		pointGeneration = new SeedPointGenerator(seed);
		centers = new ArrayList<ControlPointInterface>();
	}
	
	protected long getPointBasedSeed(double[] center){
		int[] newCenter = new int[3];
		for(int i = 0;i<center.length;i++){
			newCenter[i] =(int)(center[i]/SPACING);
		}
		return ControlPointUtility.CreateSeedHash(pointGeneration.getSeed(),newCenter);
 	}
	
	protected int getWeightMult(double[] center){
		return 1;
	}
	
	protected int getPowerMult(double[] center){
		return 1;
	}
	
	protected void setSpacing(double spacing){
		this.SPACING=spacing;
	}
	
	protected double getSpacing(){
		return SPACING;
	}
	
	protected List<int[]> getSpacedPoints(double pointSpacing,int dimensions, double[] center){
		return pointGeneration.getSpacedPoints(pointSpacing, dimensions,center);
	}
	
	protected List<double[]> offsetSpacedPoints(List<int[]> potentialCoords,int dimensions, double pointSpacing){
		return pointGeneration.offsetPoints(potentialCoords,pointSpacing,VARIANCE);
	}
	
	protected List<double[]> getCenters(double pointSpacing, int dimensions, double[] center){
		List<int[]> potentialCoords = getSpacedPoints(pointSpacing, dimensions, center);
		return offsetSpacedPoints(potentialCoords, dimensions, pointSpacing);
	}
	
	protected abstract void changeSpacing(double[] pointVec);

	protected abstract ControlPointInterface buildCenterObject(double[] center);
	
	protected ControlPointInterface buildCombinedObject(HashSet<ControlPointInterface> controlPoints){
		return new ControlDefaultCombined.CombinedControlPointBuilder()
				.addCenters(controlPoints)
				.Build();
	}

	public List<ControlPointInterface> getLocalControlPoints(double[] pointVec,int dimensions){
		changeSpacing(pointVec);
		centers.clear();
		createCenters(pointVec,dimensions);
		return centers;
	}

	private void createCenters(double[] pnt,int dimensions){
		List<double[]> points = getCenters(SPACING,dimensions,pnt);
		for(double[] point: points){
			centers.add(buildCenterObject(point));
		}
	}

	public ControlPointInterface makeCombinedPoint(HashSet<ControlPointInterface> controlPoints){
		return buildCombinedObject(controlPoints);
	}
	
}
