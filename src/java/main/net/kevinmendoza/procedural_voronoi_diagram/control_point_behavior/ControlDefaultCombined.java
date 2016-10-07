package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;

import java.util.ArrayList;
import java.util.HashSet;

public class ControlDefaultCombined extends ControlAbstractPoint {

	private ArrayList<ControlPointInterface> segs;
	private final String label;
	
	private ControlDefaultCombined(CombinedControlPointBuilder builder){
		super("Combined",builder.linearMult,builder.power);
		segs = new ArrayList<ControlPointInterface>();
		segs.addAll(builder.centers);
		hashCode = segs.get(0).hashCode();
		label = segs.get(0).toString();
	}

	@Override
	protected double getUnweightedDistance(double[] query) {
		double weight=10000, tempweight;
		for(ControlPointInterface seg : segs){
			tempweight = seg.getDistance(query,true);
			if(tempweight<weight)
				weight = tempweight;
		}
		return weight;
	}


	@Override
	protected double getDistanceCutoff() {
		return 3.0;
	}

	@Override
	public double[] getClosestPoint(double[] point) {
		
		double weight=10000, tempweight;
		double[] closestPoint = {0,0};
		
		for(ControlPointInterface seg : segs){
			tempweight = seg.getDistance(point,false);
			if(tempweight<weight){
				weight = tempweight;
				closestPoint = seg.getClosestPoint(point);
			}
		}
		return closestPoint;
	}
	
	@Override
	public boolean intersects(ControlPointInterface otherControlPoint) {
		boolean intersect = false;
		for(ControlPointInterface seg : segs){
			intersect = seg.intersects(otherControlPoint);
		}
		return intersect;
	}
	
	@Override
	public String toString(){
		return label;
	}
	
	@Override
	public int hashCode(){
		return hashCode;
	}
	
	public static class CombinedControlPointBuilder 
	extends ControlBuilderAbstract<CombinedControlPointBuilder> {
		
		private HashSet<ControlPointInterface> centers;
		
		public CombinedControlPointBuilder(){
			super();
			this.centers = new HashSet<ControlPointInterface>();
		}
		
		public CombinedControlPointBuilder addCenters(HashSet<ControlPointInterface> toAdd){
			this.centers.addAll(toAdd);
			return this;
		}
		public ControlDefaultCombined Build(){
			return new ControlDefaultCombined(this);
		}
	}

}
