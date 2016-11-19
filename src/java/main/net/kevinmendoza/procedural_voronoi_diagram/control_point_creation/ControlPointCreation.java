package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.HashSet;
import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

public interface ControlPointCreation {

	List<ControlPointInterface> getLocalControlPoints(double[] pointVec, int dimensions);

	ControlPointInterface makeCombinedPoint(
			HashSet<ControlPointInterface> controlPoints);

	static boolean isInDistance(double r, double[] one, double[] two){
		double sum=0;
		double coord;
		for(int i = 0;i<one.length;i++){
			coord = one[i] - two[i];
			sum+=coord;
		}
		return (sum <= r*r);
	}

}
