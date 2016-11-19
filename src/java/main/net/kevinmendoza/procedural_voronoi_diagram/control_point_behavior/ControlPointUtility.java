package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;

import java.util.Arrays;

public class ControlPointUtility {

	
	public static double Distance(ControlDefaultSegment seg1, ControlDefaultSegment seg2) {
		double[] seg1p1 = seg1.getEndPoint(true);
		double[] seg2p1 = seg2.getEndPoint(true);

		double u_0x = seg1p1[0];
		double u_0y = seg1p1[1];
		double u_1x = seg1.getVector()[0];
		double u_1y = seg1.getVector()[1];

		double v_0x = seg2p1[0];
		double v_0y = seg2p1[1];
		double v_1x = seg2.getVector()[0];
		double v_1y = seg2.getVector()[1];

		double d = v_1x*u_1y - u_1x*v_1y;

		double s = (1/d)*  ( (u_0x - v_0x)*u_1y - (u_0y - v_0y)*u_1x);
		double t = (1/d)* -(-(u_0x - v_0x)*v_1y + (u_0y - v_0y)*v_1x);

		// s & t are parametric values between 1 and 0. 
		if( s*s <= 1 && t*t <= 1)
			return 0;

		double[] seg1p2 = seg1.getEndPoint(false);
		double[] seg2p2 = seg2.getEndPoint(false);
		
		double[] vals = new  double[4];
		vals[0] = seg2.getDistance(seg1p1,false);
		vals[1] = seg2.getDistance(seg1p2,false);
		vals[2] = seg1.getDistance(seg2p1,false);
		vals[3] = seg1.getDistance(seg2p2,false);

		Arrays.sort(vals);
		
		return vals[0];
	}
	
	public static double DotProduct(double[] p1, double[] p2){
		return p1[0]*p2[0] + p1[1]*p2[1];
	}
	
	public static double GetSRootDist(double[] one, double[] two){
		double sum = 0;
		double basis;
		for(int i =0;i<Math.min(one.length,two.length);i++){
			basis = one[i] - two[i];
			sum+= basis*basis;
		}
		return Math.sqrt(sum);
	}
	
	public static int CreateHash(double[] point) {
		int dimensions = point.length;
		int val = 1;
		int prime = 7;
		for(int i = 0; i<dimensions;i++){
			val=val*prime;
			val=val ^ ((int)Math.floor(point[i]));
		}
		return val;
		
	}
	
	public static long CreateSeedHash(long seed,int[] point) {
		int dimensions = point.length;
		long val = seed;
		int prime = 7;
		for(int i = 0; i<dimensions;i++){
			val=val*prime;
			val=val ^ (point[i]);
		}
		return val;
		
	}
	
	public static String CreateLabel(double[] point) {
		int dimensions = point.length;
		String label = "";
		for(int i = 0; i<dimensions;i++){
			label = label + ":" + point[i];
		}
		return label;
	}
	
}
