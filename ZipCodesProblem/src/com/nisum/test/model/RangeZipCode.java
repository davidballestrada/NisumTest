package com.nisum.test.model;

public class RangeZipCode {

	private Integer startZip;
	private Integer endZip;
	
	public RangeZipCode() {
	}
	
	public RangeZipCode(Integer starZip, Integer endZip) {
		this.startZip = starZip;
		this.endZip = endZip;
	}
	
	public Integer getStartZip() {
		return startZip;
	}

	public void setStartZip(Integer startZip) {
		this.startZip = startZip;
	}

	public Integer getEndZip() {
		return endZip;
	}

	public void setEndZip(Integer endZip) {
		this.endZip = endZip;
	}

	public static enum PeriodCase {
		INDEPENDENT(0),
		SAME(1),
		INSIDE_CONTENT(2),
		PART_CONTENT(3);
		
		private int specificCase;	  
		
		PeriodCase(int caso){ 
			this.specificCase = caso; 
		}
		
		public int getCase(){
			return specificCase; 
		}
	}
	
	@Override
	public String toString() {
		return "[" + startZip.toString() + "," + endZip.toString() + "]";
	}
}
