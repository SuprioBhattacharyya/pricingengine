package com.solactive.price.pricingengine.dto;

import org.springframework.http.HttpStatus;

public class HttpResponse {
	
	HttpStatus errorCode;
	String errorMsg;
	public HttpStatus getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
