package com.solactive.price.pricingengine.service;

import com.solactive.price.pricingengine.dto.HttpResponse;
import com.solactive.price.pricingengine.dto.PriceData;
import com.solactive.price.pricingengine.dto.TickRequestDTO;

public interface PricingEngineService {
	
	public HttpResponse addOrUpdateTick(TickRequestDTO request);
	
	public PriceData retrieveData(String instrument);
	
	public PriceData retrieveAggregatedData();
//	
//	public String convertCurrency(CurrencyConversionDTO currencyConversionDTO) throws Exception;
//	
//	public URL retrieveLink() throws MalformedURLException;	

}
