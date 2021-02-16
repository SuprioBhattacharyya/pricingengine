package com.solactive.price.pricingengine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.solactive.price.pricingengine.dto.HttpResponse;
import com.solactive.price.pricingengine.dto.PriceData;
import com.solactive.price.pricingengine.dto.TickData;
import com.solactive.price.pricingengine.dto.TickRequestDTO;
import com.solactive.price.pricingengine.dto.TickResponseDTO;
import com.solactive.price.pricingengine.service.PricingEngineService;
import com.solactive.price.pricingengine.validator.TickValidator;

@Service
public class PricingEngineServiceImpl implements PricingEngineService {
	
	@Autowired
	TickData data;

	@Override
	public HttpResponse addOrUpdateTick(TickRequestDTO request) {
		TickResponseDTO tickResponse = TickValidator.validateRequest(request);
		if (tickResponse.isValid()) {
			data.add(request.getInstrument(), request.getPrice(), request.getTimestamp());
		}

		return tickResponse.getResponse();
	}
	
	@Override
	public PriceData retrieveData(String instrument) {
		return data.get(instrument);
	}

	@Override
	public PriceData retrieveAggregatedData() {
		return data.getAggregate();
	}
	

	/**
	 * The periodic job to remove old transaction records
	 * 
	 */
	@Scheduled(cron = "* * * * * ?")
	private void removeUnwantedTick() {
		data.removeUnwantedTick();
	}

}
