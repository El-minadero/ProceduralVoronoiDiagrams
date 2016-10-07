package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;
 
public abstract class ControlAbstractPoint implements ControlPointInterface {

	private double linearMult;
	private double power;
	protected String label;
	protected int hashCode;
	private String type;
	
	public ControlAbstractPoint(String type, double linearMult, double power){
		this.type = type;
		this.linearMult = linearMult;
		this.power = power;
	}
	
	public boolean isType(String otherType){
		return type.equalsIgnoreCase(otherType);
	}
	
	protected String getType(){
		return type;
	}
	
	public double getDistance(double[] pointVec,boolean weighted){
		double unweightedDist = getUnweightedDistance(pointVec);
		if(weighted)
			return linearMult*Math.pow(unweightedDist, power);
		else
			return unweightedDist;
	}
	
	protected abstract double getDistanceCutoff();
	
	protected abstract double getUnweightedDistance(double[] pointVec);
	
	public abstract String toString();
	
	public abstract int hashCode();
	
}
