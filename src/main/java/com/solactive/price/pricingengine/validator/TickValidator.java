package com.solactive.price.pricingengine.validator;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.solactive.price.pricingengine.dto.HttpResponse;
import com.solactive.price.pricingengine.dto.TickRequestDTO;
import com.solactive.price.pricingengine.dto.TickResponseDTO;

public class TickValidator {
	
	@SuppressWarnings("deprecation")
	public static TickResponseDTO validateRequest(TickRequestDTO request) {
		TickResponseDTO response = new TickResponseDTO();
		HttpResponse httpResponse = new HttpResponse();
		response.setValid(false);
		response.setResponse(httpResponse);
		if (StringUtils.isEmpty(request.getInstrument())) {
			httpResponse.setErrorCode(HttpStatus.BAD_REQUEST);
			httpResponse.setErrorMsg("Instrument is required!");
		} else if (request.getPrice() < 0.0) {
			httpResponse.setErrorCode(HttpStatus.BAD_REQUEST);
			httpResponse.setErrorMsg("Price is required!");
		} else if (request.getTimestamp() < 0) {
			httpResponse.setErrorCode(HttpStatus.BAD_REQUEST);
			httpResponse.setErrorMsg("Timestamp must not be negative!");
		} else if (request.getTimestamp() < (System.currentTimeMillis() - Long.parseLong("60") * 1000)) {
			httpResponse.setErrorCode(HttpStatus.NO_CONTENT);
		} else {
			httpResponse.setErrorCode(HttpStatus.CREATED);
			response.setValid(true);
		}
		return response;
	}

}
