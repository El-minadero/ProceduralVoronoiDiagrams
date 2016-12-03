package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;

public class ControlDefaultSegment extends ControlAbstractPoint  {
	
	private double[] end1;
	private double[] end2;
	private double[] vector;
	private double max;
	private double squaredMax;
	private String label;
	private final double cutoff = 5;
	
	public ControlDefaultSegment(ControlSegmentBuilder builder){
		super("Segment",1,1);
		initLine(builder);
	}
	
	@Override
	public double getDistanceCutoff(){
		return cutoff;
	}
	
	@Override
	public boolean intersects(ControlPointInterface centerObject) {
		double dist= 0;
		if(centerObject.isType(getType())){
			dist = ControlPointUtility.Distance(this, (ControlDefaultSegment)centerObject);
		}
		else if(centerObject.isType("Point")){
			ControlDefaultPoint pnt = (ControlDefaultPoint) centerObject;
			double[] p = getClosestPoint(pnt.getPoint());
			dist = getDistance(p, true);
		}
		else {
			//TODO idk what to do here
		}
		return (dist < getDistanceCutoff());
	}

	@Override
	public double getUnweightedDistance(double[] pointVec) {
		double[] point = getClosestPoint(pointVec);
		return ControlPointUtility.GetSRootDist(point, pointVec);
	}
	
	@Override
	public double[] getClosestPoint(double[] p) {
		double[] ap = { p[0] - end1[0], p[1] - end1[1] };
		double t = ControlPointUtility.DotProduct(ap,vector)/squaredMax;
		if(t>1.0 || t < 0) {
			double dist1 = ControlPointUtility.GetSRootDist(p, end1);
			double dist2 = ControlPointUtility.GetSRootDist(p, end2);
			if(dist1<dist2)
				return end1;
			else
				return end2;
		}
		return getParametricPoint(t);
	}
	
	public double[] getVector(){
		return vector;
	}
	
	public double[] getEndPoint(boolean b){
		if(b)
			return end1;
		return end2;
	}
	
	private double[] getParametricPoint(double t){
		double[] paramPoint = {vector[0]*t + end1[0],vector[1]*t + end1[1]};
		return paramPoint;
	}
	
	private void createString() {
		this.label = "p1{" + ControlPointUtility.CreateLabel(end1) + "}" + 
				     "#p2{" + ControlPointUtility.CreateLabel(end2) + "}";
	}
	
	private void initLine(ControlSegmentBuilder builder){
		this.max = builder.length;
		double[] p1 = {  builder.unitVector[0]*max/2 + builder.center[0], 
				builder.unitVector[1]*max/2 + builder.center[1]};
		double[] p2 = { -builder.unitVector[0]*max/2 + builder.center[0],
				-builder.unitVector[1]*max/2 + builder.center[1]};
		double[] v = {p2[0] - p1[0],p2[1] - p1[1]};
		vector = v; end1 = p1;end2 = p2;
		squaredMax = max*max;
		createString();
		hashCode = ControlPointUtility.CreateHash(end1) 
				 ^ ControlPointUtility.CreateHash(end2);
	}
	
	public int getRGBIdentifier(){
		return 500;
	}

	@Override
	public String toString() {
		return getType() + " " + label;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}

	public static class ControlSegmentBuilder 
	extends ControlBuilderAbstract<ControlSegmentBuilder> {
		
		private double[] center;
		private double length;
		private double[] unitVector;
		
		public ControlSegmentBuilder(){
			super();
		}
		
		public ControlSegmentBuilder withCenter(double[] center){
			this.center = center;
			return this;
		}
		
		public ControlSegmentBuilder withMagnitude(double length){
			this.length = length;
			return this;
		}
		
		public ControlSegmentBuilder withUnitVector(double[] vec){
			this.unitVector = vec;
			return this;
		}
		
		public ControlDefaultSegment Build(){
			return new ControlDefaultSegment(this);
		}
	}
}
