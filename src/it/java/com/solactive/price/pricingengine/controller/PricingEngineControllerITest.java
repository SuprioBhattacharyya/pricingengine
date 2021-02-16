package com.solactive.price.pricingengine.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solactive.price.pricingengine.dto.TickRequestDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PricingEngineControllerITest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	
	public static final String ADD_TICK = "/tick";
	public static final String STATISTICS = "/statistics";

	/** Test APIs in a flow
	 * @throws Exception
	 */
	@Test
	public void testAddOrUpdateTick() throws Exception {
		
		long delayToBeginningOfNextSecond = 1100 - System.currentTimeMillis() % 1000;
		TimeUnit.MILLISECONDS.sleep(delayToBeginningOfNextSecond);

        long timestamp = System.currentTimeMillis()-59000;

        TickRequestDTO request = createTickRequestDTO("IBM.N",1.2, timestamp);
        // add statistics
        mvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.total").value(1.2))
                .andExpect(jsonPath("$.avg").value(1.2))
                .andExpect(jsonPath("$.min").value(1.2))
                .andExpect(jsonPath("$.max").value(1.2));
        
        request = createTickRequestDTO("IBM.N", 1.3, timestamp + 1000);
        // add statistics
        mvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.total").value(2.5))
                .andExpect(jsonPath("$.avg").value(1.3))
                .andExpect(jsonPath("$.min").value(1.2))
                .andExpect(jsonPath("$.max").value(1.3));
       
        request = createTickRequestDTO("IBM.F",1.4, timestamp + 2000);
        // add statistics
        mvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics - count, sum, avg normal effect - max changed
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.total").value(3.9))
                .andExpect(jsonPath("$.avg").value(1.3))
                .andExpect(jsonPath("$.min").value(1.2))
                .andExpect(jsonPath("$.max").value(1.4));
        
        // 2 transaction in a second
        request = createTickRequestDTO("IBM.F",1.5, timestamp + 3000);
        // add statistics
        mvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics- count, sum, avg normal effect - max changed
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(4))
        .andExpect(jsonPath("$.total").value(5.4))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.2))
        .andExpect(jsonPath("$.max").value(1.5));
        
        
        request = createTickRequestDTO("IBM.F",1.6, timestamp + 3000);
        // add statistics
        mvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics- count, sum, avg normal effect - max changed
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(5))
        .andExpect(jsonPath("$.total").value(7.0))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.2))
        .andExpect(jsonPath("$.max").value(1.6));
        
        
        request = createTickRequestDTO("IBM.A",1.1, timestamp + 4000);
        // add statistics
        mvc.perform(post(ADD_TICK).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics- min value changed
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(6))
                .andExpect(jsonPath("$.total").value(8.1))
                .andExpect(jsonPath("$.avg").value(1.4))
                .andExpect(jsonPath("$.min").value(1.1))
                .andExpect(jsonPath("$.max").value(1.6));

        TimeUnit.SECONDS.sleep(2);
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(5))
        .andExpect(jsonPath("$.total").value(6.9))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.6));
       
        
        TimeUnit.SECONDS.sleep(1);
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(4))
        .andExpect(jsonPath("$.total").value(5.6))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.6));
        
        TimeUnit.SECONDS.sleep(1);
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(3))
        .andExpect(jsonPath("$.total").value(4.2))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.6));
        
        TimeUnit.SECONDS.sleep(3);
        mvc.perform(get(STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(1))
        .andExpect(jsonPath("$.total").value(1.6))
        .andExpect(jsonPath("$.avg").value(1.6))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.6));
        
	}
	
	private TickRequestDTO createTickRequestDTO(String instrument,double amount, long timestamp) {
		TickRequestDTO request = new TickRequestDTO();
		request.setInstrument(instrument);
		request.setPrice(amount);
		request.setTimestamp(timestamp);

		return request;
	}

}
