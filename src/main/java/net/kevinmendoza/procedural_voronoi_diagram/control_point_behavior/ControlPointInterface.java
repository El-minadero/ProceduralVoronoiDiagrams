package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;

public interface ControlPointInterface {

	boolean intersects(ControlPointInterface otherControlPoint);
	
	int getRGBIdentifier();

	double[] getClosestPoint(double[] point);
	
	double getDistance(double[] point,boolean weighted);
	
	boolean isType(String otherType);
	
	String toString();

	int hashCode();
	
	static int Compare(ControlPointInterface o1, ControlPointInterface o2, double[] point){
		double dist1 = o1.getDistance(point, true);
		double dist2 = o2.getDistance(point, true);
		if(dist1>dist2)
			return 1;
		else if(dist1==dist2)
			return 0;
		return -1;
	}
	
}
