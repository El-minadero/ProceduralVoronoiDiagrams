package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;

public class ControlDefaultPoint extends ControlAbstractPoint {

	private final double[] point;
	
	private ControlDefaultPoint(ControlPointBuilder builder){
		super("Point",builder.linearMult, builder.power);
		this.point = builder.point;
		this.label = "" + point[0] + ":" + point[1] + ":" + point[2];
		this.hashCode = (int)(((point[0]*7 + 7)*point[1]*7 + 7)*point[2]*7 + 7);
	}
	
	public double[] getPoint(){
		double[] np = {point[0], point[1], point[2]};
		return np;
	}
	
	@Override
	public String toString(){
		return label;
	}
	
	@Override
	protected double getDistanceCutoff() {
		// TODO Auto-generated method stub
		return 2.0;
	}
	
	@Override
	public boolean intersects(ControlPointInterface centerObject) {
		double weight = centerObject.getDistance(this.point,true);
		if(weight < getDistanceCutoff())
			return true;
		else
			return false;
	}


	@Override
	public double getUnweightedDistance(double[] queryPoint) {
		return ControlPointInterface.GetSRootDist(point, queryPoint);
	}
	
	@Override
	public double[] getClosestPoint(double[] p){
		return getPoint();
	}
	
	@Override
	public int hashCode(){
		return hashCode;
	}

	public static class ControlPointBuilder
	extends ControlBuilderAbstract<ControlPointBuilder> {
		
		private double[] point;
		
		public ControlPointBuilder(){
			super();
		}
		
		public ControlPointBuilder withPoint(double[] point){
			this.point = point;
			return this;
		}
		
		public ControlDefaultPoint Build(){
			return new ControlDefaultPoint(this);
		}
	}
}
