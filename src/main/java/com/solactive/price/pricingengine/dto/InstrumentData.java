package com.solactive.price.pricingengine.dto;

public class InstrumentData {

	
	private String instrument;
	private double amt;
	
	
	public InstrumentData() {
		
	}

	public InstrumentData(String instrument,double amt) {	
		this.amt = amt;
		this.instrument = instrument;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	
}
