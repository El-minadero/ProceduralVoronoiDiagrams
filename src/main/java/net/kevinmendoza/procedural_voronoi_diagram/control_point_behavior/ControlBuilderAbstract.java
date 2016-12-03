package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;

abstract class ControlBuilderAbstract<T extends ControlBuilderAbstract<T>> {

	public double power;
	public double linearMult;
	
	public ControlBuilderAbstract(){
		power		= 1;
		linearMult 	= 1;
	}
	
	public T withWeightPower(double power){
		this.power = power;
		return (T) this;
	}
	
	public T withWeightMult(double mult){
		this.linearMult = mult;
		return (T) this;
	}
	
}
