package net.kevinmendoza.procedural_voronoi_diagram;

import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

public interface VoronoiDiagram {

	public List<ControlPointInterface> getLowestCostObjectsAtPoint(double[] pointVec);
	
}
