package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.HashSet;
import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

public interface ControlPointCreation {

	/**
	 * Method produces a list of control point objects within range of the provided center point.
	 * 
	 * Specifying two dimensions returns at least 9 objects (Moore neighborhood), 3 dimensions 
	 * returns 27 (3 moore neighborhoods), and so on.
	 * 
	 * @param double[] center
	 * 			the center coordinates to get the Moore neighborhood from.
	 * 
	 * @param int dimensions
	 * 
	 *		the dimensionality of the control points. The minimum value this can be is 2.
	 *	Note that higher dimensional voronoi diagrams suffer from poor performance.
	 * 
	 */
	List<ControlPointInterface> getLocalControlPoints(double[] center, int dimensions);

	ControlPointInterface makeCombinedPoint(
			HashSet<ControlPointInterface> controlPoints);

}
