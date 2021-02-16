package com.solactive.price.pricingengine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceData {

	private double min;
	private double max;
	private double avg;
	private int count;
	private double total;
	private long time;
	
	public PriceData() {
		
	}

	public PriceData(double amt,long time) {	
		this.min = amt;
		this.max = amt;
		this.avg = amt;
		this.total = amt;
		this.count = 1;	
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public void update(double amt, long time) {
		count++;
		this.total = BigDecimal.valueOf(total).add(BigDecimal.valueOf(amt)).doubleValue();
		this.time = time;
		this.min = Math.min(this.min, amt);
		this.max = Math.max(this.max, amt);
		this.avg = BigDecimal.valueOf(total).divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP).doubleValue();
	}
	
	public void removeUnwantedTick(double amt) {
		if (count > 1) {
			count--;
			this.total = BigDecimal.valueOf(total).subtract(BigDecimal.valueOf(amt)).doubleValue();
			this.avg = BigDecimal.valueOf(total).divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP).doubleValue();

		} else if (count==1){
			count = 0 ;
			total=0.0;
			avg= 0.0;
			
		}
	}

	@Override
	public String toString() {
		return "[" + min + " " + max + " "+ avg + " | " + count + " " + total + "]";
	}

}
