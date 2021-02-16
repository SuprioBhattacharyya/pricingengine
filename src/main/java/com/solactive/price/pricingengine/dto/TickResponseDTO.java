package com.solactive.price.pricingengine.dto;

public class TickResponseDTO {

	boolean isValid;
	HttpResponse response;
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public HttpResponse getResponse() {
		return response;
	}
	public void setResponse(HttpResponse response) {
		this.response = response;
	}
}
