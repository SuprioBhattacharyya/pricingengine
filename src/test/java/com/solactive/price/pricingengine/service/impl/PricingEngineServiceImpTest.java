/**
 * 
 */
package com.solactive.price.pricingengine.service.impl;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.solactive.price.pricingengine.dto.PriceData;
import com.solactive.price.pricingengine.dto.TickData;
import com.solactive.price.pricingengine.dto.TickRequestDTO;


/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class PricingEngineServiceImpTest {

	@Mock
	private TickData tickData;

	@InjectMocks
	private PricingEngineServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddStatisticsAndAggregate() throws InterruptedException {
		Mockito.doReturn(new PriceData()).when(tickData).getAggregate();
		
		service.addOrUpdateTick(createTickRequestDTO("IBM.N",123, 123456));

		PriceData data = service.retrieveAggregatedData();
		Assert.assertNotNull(data.getCount());
	}

	@Test
	public void testAddStatisticsAndGet() throws InterruptedException {
		Mockito.doReturn(new PriceData()).when(tickData).get(Mockito.any());

		service.addOrUpdateTick(createTickRequestDTO("IBM.N",124, 13456));

		PriceData data = service.retrieveData("IBM.N");
		Assert.assertNotNull(data.getCount());
	}
	
	private TickRequestDTO createTickRequestDTO(String instrument,double amount, long timestamp) {
		TickRequestDTO request = new TickRequestDTO();
		request.setInstrument(instrument);
		request.setPrice(amount);
		request.setTimestamp(timestamp);

		return request;
	}
}
