package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlDefaultPoint;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

public class ControlPointGenerator extends AbstractControlGenerator {

	public ControlPointGenerator(long seed,double spacing){
		super(seed);
		setSpacing(spacing);
	}


	@Override
	protected ControlPointInterface buildCenterObject(double[] center) {
		return new ControlDefaultPoint
				.ControlPointBuilder()
				.withPoint(center)
				.withWeightMult(getWeightMult(center))
				.withWeightPower(getPowerMult(center))
				.Build();
	}


	@Override
	protected void changeSpacing(double[] pointVec) {
	}

}
