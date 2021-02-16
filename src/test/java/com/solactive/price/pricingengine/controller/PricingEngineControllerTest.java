package com.solactive.price.pricingengine.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solactive.price.pricingengine.dto.HttpResponse;
import com.solactive.price.pricingengine.dto.PriceData;
import com.solactive.price.pricingengine.dto.TickRequestDTO;
import com.solactive.price.pricingengine.service.PricingEngineService;

@RunWith(SpringRunner.class)
public class PricingEngineControllerTest {
	private MockMvc mockMvc;

	@Mock
	private PricingEngineService service;

	@InjectMocks
	private PricingEngineController controller;

	private ObjectMapper mapper = new ObjectMapper();
	
	public static final String ADD_TICK = "/tick";
	public static final String STATISTICS = "/statistics";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testAddTransaction() throws Exception {
		TickRequestDTO tickRequestDTO = createTickRequestDTO("IBM.N",1.0, System.currentTimeMillis());
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setErrorCode(HttpStatus.CREATED);
		httpResponse.setErrorMsg("");
		Mockito.doReturn(httpResponse).when(service).addOrUpdateTick(Mockito.any());

		
		mockMvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(tickRequestDTO)))
		        .andExpect(status().is(HttpServletResponse.SC_CREATED));
	}

	@Test
	public void testAddTransactionOutOfRange() throws Exception {

		TickRequestDTO tickRequestDTO = createTickRequestDTO("IBM.N",1.0, System.currentTimeMillis() + 61000);
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setErrorCode(HttpStatus.CREATED);
		httpResponse.setErrorMsg("");
		Mockito.doReturn(httpResponse).when(service).addOrUpdateTick(Mockito.any());

		mockMvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(tickRequestDTO)))
				.andExpect(status().is(HttpServletResponse.SC_CREATED));
	}

	@Test
	public void testGetStatistics() throws Exception {
		PriceData priceData = new PriceData(5,1662200);
		Mockito.when(service.retrieveAggregatedData()).thenReturn(priceData);

		mockMvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.count").value(priceData.getCount()))
				.andExpect(jsonPath("$.avg").value(priceData.getAvg()))
				.andExpect(jsonPath("$.total").value(priceData.getTotal()))
				.andExpect(jsonPath("$.min").value(priceData.getMin()))
				.andExpect(jsonPath("$.max").value(priceData.getMax()));
	}

	private TickRequestDTO createTickRequestDTO(String instrument,double amount, long timestamp) {
		TickRequestDTO request = new TickRequestDTO();
		request.setInstrument(instrument);
		request.setPrice(amount);
		request.setTimestamp(timestamp);

		return request;
	}


}
