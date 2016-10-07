package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.HashSet;
import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

public interface ControlPointCreation {

	List<ControlPointInterface> getLocalControlPoints(double[] pointVec);

	ControlPointInterface makeCombinedPoint(
			HashSet<ControlPointInterface> controlPoints);

	static boolean isInDistance(double r, double[] one, double[] two){
		double xx = one[0] - two[0];
		double yy = one[1] - two[1];
		return (xx*xx - yy*yy <= r*r);
	}

}
