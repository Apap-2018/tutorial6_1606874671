package com.apap.tutorial6.controller;

import java.util.Date;
import java.util.Optional;

import com.apap.tutorial6.model.FlightModel;
import com.apap.tutorial6.repository.FlightDb;
import com.apap.tutorial6.service.FlightService;
import com.apap.tutorial6.service.PilotService;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightController.class)
public class FlightControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FlightService flightService;

	@MockBean
	private PilotService pilotService;

	@Test
	public void givenFlightFlightNumber_whenViewFlight_thenReturnJsonFlight() throws Exception {

		// given
		FlightModel flightModel = new FlightModel();
		flightModel.setFlightNumber("F4 RC");
		flightModel.setOrigin("Depok");
		flightModel.setDestination("Bekasi");
		flightModel.setTime(new Date(new java.util.Date().getTime()));
		Optional<FlightModel> flight = Optional.of(flightModel);
		Mockito.when(flightService.getFlightDetailByFlightNumber(flight.get().getFlightNumber())).thenReturn(flight);
	
		// when
		mvc.perform(MockMvcRequestBuilders.get("/flight/view")
			.param("flightNumber", flight.get().getFlightNumber())
			.contentType(MediaType.APPLICATION_JSON))
			
			// then
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber", Matchers.is(flight.get().getFlightNumber())));
	}

}

