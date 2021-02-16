package com.solactive.price.pricingengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.solactive.price.pricingengine.dto.HttpResponse;
import com.solactive.price.pricingengine.dto.PriceData;
import com.solactive.price.pricingengine.dto.TickRequestDTO;
import com.solactive.price.pricingengine.service.PricingEngineService;

@RestController
public class PricingEngineController {

	@Autowired
	private PricingEngineService service;

	/**
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/tick", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity addOrUpdateTick(@RequestBody TickRequestDTO request) {

		HttpResponse response = service.addOrUpdateTick(request);
		return new ResponseEntity<>(response.getErrorMsg(), response.getErrorCode());
	}

	/**
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/statistics/{instrument}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PriceData> retrieveStatistics(@PathVariable String instrument) {

		PriceData data = service.retrieveData(instrument);
		if (data != null) {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
		return new ResponseEntity<>(new PriceData(), HttpStatus.OK);
	}

	/**
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity retrieveAggregatedStatistics() {

		PriceData data = service.retrieveAggregatedData();
		if (data != null) {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
		return new ResponseEntity<>(new PriceData(), HttpStatus.OK);
	}

}
