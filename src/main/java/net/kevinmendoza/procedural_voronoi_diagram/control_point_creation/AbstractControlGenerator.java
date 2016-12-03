package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlDefaultCombined;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointUtility;

// TODO: Auto-generated Javadoc
/**
 * The AbstractControlGenerator Class contains most of the methods needed to procedurally create 
 * Voronoi diagram center points. It makes no explicit reference to any one type of Voronoi center; 
 * explicit creation of centers must be carried out by a subclass. 
 */
abstract class AbstractControlGenerator implements ControlPointCreation {

	/** a point generator object. */
	private ProceduralCenterGenerator pointGeneration;
	
	/** A temporary center holder variable. */
	private List<ControlPointInterface> centers;
	
	/** Default spacing for points. This is not made final to present the ability to dynamically
	 * change the voronoi point spacing. */
	private double SPACING = 30;
	
	/** variable for how much generated points are allowed to 'wiggle' within their Moore
	 * neighborhood cells, calculated analogously to wiggle = Spacing/Variance */
	private double VARIANCE = 1.0;

	/**
	 * Default constructor uses a seed of 5.
	 * 
	 */
	public AbstractControlGenerator() {
		pointGeneration = new ProceduralCenterGenerator(5);
		centers = new ArrayList<ControlPointInterface>();
	}
	
	/**
	 * Seed-based constructor.
	 *
	 * @param long seed 
	 * 		a seed value for the generator to use.
	 */
	public AbstractControlGenerator(long seed) {
		pointGeneration = new ProceduralCenterGenerator(seed);
		centers = new ArrayList<ControlPointInterface>();
	}
	
	/**
	 * Gets a seed value based on the point.
	 *
	 * @param double[] center 
	 * 		The Moore neighborhood cell to retrieve the seed value from
	 * 
	 * @return a long seed unique for that cell.
	 */
	protected long getPointBasedSeed(double[] center){
		int[] newCenter = new int[3];
		for(int i = 0;i<center.length;i++){
			newCenter[i] =(int)(center[i]/SPACING);
		}
		return ControlPointUtility.CreateSeedHash(pointGeneration.getSeed(),newCenter);
 	}
	
	/**
	 * Gets a weight multiplier for the given center point. Default method sets the weight to 1.
	 * weight for all cells is calculated by W = m*d^(p), where m is the weight multiplier and W is the weight.
	 *
	 * @param double[] center 
	 *			The Moore neighborhood cell to retrieve the weight value from
	 *
	 * @return the weight multiplier.
	 */
	protected double getWeightMult(double[] center){
		return 1.0;
	}
	
	/**
	 * Gets a power multiplier for the given center point. Default method sets the power to 1.
	 * weight for all cells is calculated by W = m*d^(p), where p is the power multiplier and W is the weight.
	 *
	 * @param double[] center 
	 *		The Moore neighborhood cell to retrieve the power value from
	 * @return the power multiplier.
	 */
	protected int getPowerMult(double[] center){
		return 1;
	}
	
	/**
	 * Allows for dynamical redefinining of spacing. Override at your own risk.
	 *
	 * @param double spacing 
	 * 			the new spacing
	 */
	protected void setSpacing(double spacing){
		this.SPACING=spacing;
	}
	
	/**
	 * Gets the spacing.
	 *
	 * @return the spacing
	 */
	protected double getSpacing(){
		return SPACING;
	}
	
	/**
	 * Gets the spaced points.
	 *
	 * @param pointSpacing the point spacing
	 * @param dimensions the dimensions
	 * @param center the center
	 * @return the spaced points
	 */
	protected List<int[]> getSpacedPoints(double pointSpacing,int dimensions, double[] center){
		return pointGeneration.getSpacedPoints(pointSpacing, dimensions,center);
	}
	
	/**
	 * Offset spaced points.
	 *
	 * @param potentialCoords the potential coords
	 * @param dimensions the dimensions
	 * @param pointSpacing the point spacing
	 * @return the list
	 */
	protected List<double[]> offsetSpacedPoints(List<int[]> potentialCoords,int dimensions, double pointSpacing){
		return pointGeneration.offsetPoints(potentialCoords,pointSpacing,VARIANCE);
	}
	
	/**
	 * Gets the centers.
	 *
	 * @param pointSpacing the point spacing
	 * @param dimensions the dimensions
	 * @param center the center
	 * @return the centers
	 */
	protected List<double[]> getCenters(double pointSpacing, int dimensions, double[] center){
		List<int[]> potentialCoords = getSpacedPoints(pointSpacing, dimensions, center);
		return offsetSpacedPoints(potentialCoords, dimensions, pointSpacing);
	}
	
	/**
	 * Change spacing.
	 *
	 * @param pointVec the point vec
	 */
	protected abstract void changeSpacing(double[] pointVec);

	/**
	 * Builds the center object.
	 *
	 * @param center the center
	 * @return the control point interface
	 */
	protected abstract ControlPointInterface buildCenterObject(double[] center);
	
	/**
	 * Builds the combined object.
	 *
	 * @param controlPoints the control points
	 * @return the control point interface
	 */
	protected ControlPointInterface buildCombinedObject(HashSet<ControlPointInterface> controlPoints){
		return new ControlDefaultCombined.CombinedControlPointBuilder()
				.addCenters(controlPoints)
				.Build();
	}

	/* (non-Javadoc)
	 * @see net.kevinmendoza.procedural_voronoi_diagram.control_point_creation.ControlPointCreation#getLocalControlPoints(double[], int)
	 */
	public List<ControlPointInterface> getLocalControlPoints(double[] pointVec,int dimensions){
		changeSpacing(pointVec);
		centers.clear();
		createCenters(pointVec,dimensions);
		return centers;
	}

	/**
	 * Creates the centers.
	 *
	 * @param pnt the pnt
	 * @param dimensions the dimensions
	 */
	private void createCenters(double[] pnt,int dimensions){
		List<double[]> points = getCenters(SPACING,dimensions,pnt);
		for(double[] point: points){
			centers.add(buildCenterObject(point));
		}
	}

	/* (non-Javadoc)
	 * @see net.kevinmendoza.procedural_voronoi_diagram.control_point_creation.ControlPointCreation#makeCombinedPoint(java.util.HashSet)
	 */
	public ControlPointInterface makeCombinedPoint(HashSet<ControlPointInterface> controlPoints){
		return buildCombinedObject(controlPoints);
	}
	
}
