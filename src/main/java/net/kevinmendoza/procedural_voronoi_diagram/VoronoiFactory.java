package net.kevinmendoza.procedural_voronoi_diagram;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_creation.ControlPointCreation;

public class VoronoiFactory {

	public VoronoiFactory(){
	}
	
	public VoronoiDiagram BuildVoronoi(ControlPointCreation controlInterface) {
		return new VoronoiImplementation
				.VoronoiBuilder()
				.withControlPointCreation(controlInterface)
				.Build();
		
	}

}
