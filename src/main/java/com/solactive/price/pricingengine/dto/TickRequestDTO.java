package com.solactive.price.pricingengine.dto;

public class TickRequestDTO {
	 private String instrument;
	 private double price;
	 private long timestamp;
	 
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "[instrument= " + instrument + ", price= " + price + ", timestamp= " + timestamp + "]";
	}

}
