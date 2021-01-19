package com.amr.currencycalculation.dto;

public class Limits {
	
	public Limits(){
		super();
	}
	public Limits(Integer min,Integer max){
		this.min = min;
		this.max = max;
	}
	
	private Integer min;
	private Integer max;
	
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	
	@Override
	public String toString() {
		return "Limits [min=" + min + ", max=" + max + "]";
	}	

}
